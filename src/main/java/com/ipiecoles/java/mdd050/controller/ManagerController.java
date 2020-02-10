package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    // Import du repository de manager

    @Autowired // Un repository existe et j'ai besoin de l'utiliser
    private ManagerRepository managerRepository;

    //Import du repository du Technicien
    @Autowired
    private TechnicienRepository technicienRepository;

    @Autowired
    private EmployeRepository employeRepository;


    //10/02/2020 : Méthode pour "supprimer" enlever un technicien l'équipe d'un manager
    @RequestMapping(method = RequestMethod.GET, value = "/{idManager}/equipe/{idTechnicien}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)//Renvoyer le code correspondant au status HTTP et pas le code 200, pas de contenu renvoyé et cela est normal
    public void deleteEmploye (
            @PathVariable("idManager") Long idManager,
            @PathVariable("idTechnicien") Long idTechnicien) {

        //Recupérer le technicien à partir de son id
        Technicien technicien = technicienRepository.findById(idTechnicien).get();

        //Supprimer le lien avec le manager => attribut manager dans technicien
        technicien.setManager(null); //le fait de mettre l'id du manager à null permet d'enlever le lien entre le technicien et le manager et donc de supprimer le techincien de l'équipe

        //On sauvegarde le technicien
        technicienRepository.save(technicien);
    }

    //10/02/2020 : Méthode pour ajouter un technicien l'équipe d'un manager
    @RequestMapping(method = RequestMethod.GET, value = "/{idManager}/equipe/{matriculeTech}/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)//Renvoyer le code correspondant au status HTTP et pas le code 200, pas de contenu renvoyé et cela est normal
    public Manager addTechncienToEquipe (
            @PathVariable("idManager") Long idManager,
            @PathVariable("matriculeTech") String matriculeTech) {

        //Récupérer l'id du manager et l'envoyer à technicien
        Manager manager = managerRepository.findById(idManager).get();

        //Recupérer le technicien à partir de son id de la class employe
        Technicien technicien = technicienRepository.findByMatricule(matriculeTech);

        //Affectation d'un manager à un technicien
        technicien.setManager(manager);

        //On sauvegarde le technicien
        technicienRepository.save(technicien);
        return manager;
    }
}
