package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.awt.print.Pageable;
import java.util.Optional;

@RestController
@RequestMapping("/employes")
public class EmployeController {

    @Autowired // Un repository existe et j'ai besoin de l'utiliser
    private EmployeRepository employeRepository;


    @RequestMapping(value = "/count", method = RequestMethod.GET) //concaténation de l'URL d'en haut
    public long countEmployes() {
        //Récupérer le nombre d'employés et l'envoyer au client
        return employeRepository.count();
    }

    /*
        @RequestMapping(value="/6", method = RequestMethod.GET)
        public Employe retreiveEmployes() {
            return employeRepository.findById(6L).get();
        }

     */
//Rendre dynamique l'affichage des employés
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employe retrieveEmployes(@PathVariable("id") Long id) {

        //10/02/2020 : gestion des exceptions

        Optional <Employe> e = employeRepository.findById(id);
        if (e.isPresent()){
            return e.get();
        }
        throw new EntityNotFoundException("L'employé d'identifiant" + id + "n'éxiste pas !");
    }

    //Méthode GET /employés et renvoi l'employé avec le matricule C00019 + Exceptions

    public static final String REGEX_MATRICULE = "^[MCT][0-9]{5}"; // pas d'espace (sensible à la casse)

    @RequestMapping(method = RequestMethod.GET, value = "", params = "matricule")
    public Employe EmployeByMatricule(@RequestParam("matricule") String matricule) {

        //10/02/2020 : gestion des exceptions

        if(!matricule.matches(REGEX_MATRICULE)){
            throw new IllegalArgumentException("Le matricule fourni est incorrect !");
        }
        Employe employe = employeRepository.findByMatricule(matricule);
        if (employe == null){
            throw new EntityNotFoundException("L'employé d'identifiant" + matricule + "n'éxiste pas !");
        }
        return employe;
    }

    //Listes des employés
    public interface PagingAndSortingRepository extends CrudRepository<Employe, ID> {
        Iterable<Employe> findAll(Sort sort);

        Page<Employe> findAll(Pageable pageable);
    }

    /*10/02/2020 : Ajout du requestMapping pour faire le lien avec l'index.html*/
    /*Méthode permettant d'afficher la listes des employés*/
    @RequestMapping(method = RequestMethod.GET)
    public Page<Employe> GetListEmploye(@RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size,
                                        @RequestParam("sortDirection") String Direction,
                                        @RequestParam("sortProperty") String SortProperty) {

        //10/02/2020 : gestion des exceptions pour le nombre d'éléments par page

        Page<Employe> employes =  employeRepository.findAll(PageRequest.of(page, size, Sort.Direction.fromString(Direction), SortProperty));

        if(size < 1 || size > 50){ //permet que si un attaquant veux faire tomber notre site en augmentant la taille du nombre de page il ne pourra pas
            throw new IllegalArgumentException("Le nombres d'élements est trop important ! Loupé ");
        }
        return employes;
    }

    //10/02/2020 : Création du mapping pour pouvoir créer un nouvel employé en une fois pour les différent postes (Manager, commercial, technicien)
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, //Format de données reçu (JSON)
            produces = MediaType.APPLICATION_JSON_VALUE) // Format de données envoyé (JSON)
    public Employe createEmploye(
            @RequestBody Employe employe
    ) {
        return employeRepository.save(employe);
        //return employeRepository.findAll(employe); //return employe repository for save New Employe
    }
    //10/02/2020 : Création méthode pour modifié les paramètres d'un employé
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Employe modifyEmploye(
            @PathVariable("id") Long idEmploye,  //Permettera de gérer l'exception de l'id afin d'éviter l'incohérence de données
            @RequestBody Employe employe) {
        return employeRepository.save(employe);
    }
    //10/02/2020 : Méthode pour supprimer un employé
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//Renvoyer le code correspondant au status HTTP et pas le code 200, pas de contenu renvoyé et cela est normal
    public void deleteEmploye (
            @PathVariable("id") Long idEmploye) {
        employeRepository.deleteById(idEmploye);
        //return ("l'uilitsateur à été supprimer");
    }

}
