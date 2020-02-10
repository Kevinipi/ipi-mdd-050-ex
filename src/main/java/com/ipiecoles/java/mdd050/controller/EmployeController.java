package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.print.Pageable;

@RestController
@RequestMapping("/employes")
public class EmployeController {

    @Autowired // Un repository existe et j'ai besoin de l'utiliser
    private EmployeRepository employeRepository;
    //private Object employeService;

    @RequestMapping(value = "/count", method = RequestMethod.GET) //concaténation de l'URL d'en haut
    public long countEmployes(){
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
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Employe retrieveEmployes(@PathVariable ("id") Long id) {
        return employeRepository.findById(id).get();
    }

    //Méthode GET /employés et renvoi l'employé avec le matricule C00019
    @RequestMapping(method = RequestMethod.GET, value = "", params = "matricule")
    public Employe EmployeByMatricule (@RequestParam("matricule") String matricule){
        return employeRepository.findByMatricule(matricule);
    }

    //Listes des employés
    public interface PagingAndSortingRepository extends CrudRepository<Employe, ID> {
        Iterable<Employe> findAll(Sort sort);
        Page<Employe> findAll(Pageable pageable);
    }
    @RequestMapping(method = RequestMethod.GET) //10/02/2020 : Ajout du requestMapping pour faire le lien avec l'index.html
    public Page<Employe> GetListEmploye (@RequestParam("page")Integer page,
                                   @RequestParam("size") Integer size,
                                   @RequestParam("sortDirection") String Direction,
                                   @RequestParam("sortProperty") String SortProperty) {
        //PageRequest pageRequest = new PageRequest(page, size, Direction,matricule);
        return (Page<Employe>) employeRepository.findAll(PageRequest.of(page, size, Sort.Direction.fromString(Direction), SortProperty));
    }

    //10/02/2020 : Création du mapping pour pouvoir créer un nouvel employé en une fois pour les différent postes (Manager, commercial, technicien)
    @RequestMapping(method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE, //Format de données reçu (JSON)
                    produces = MediaType.APPLICATION_JSON_VALUE) // Format de données envoyé (JSON)
    public Employe createEmploye(
            @RequestBody Employe employe
    ){
        return employeRepository.save(employe);
        //return employeRepository.findAll(employe); //return employe repository for save New Employe
    }
}
