package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/techniciens")


public class TechnicienController {

    @Autowired
    private TechnicienRepository technicienRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmployeRepository employeRepository;

    //10/02/2020 : Ajouter ou supprimer un manager à un technicien
    @RequestMapping(method = RequestMethod.GET, value = "{idTechnicien}/manager/{matriculeManager}/add")
    public Technicien addManagerToTechnicien (
            @PathVariable("idTechnicien") Long idTechnicien,
            @PathVariable("matriculeManager") String matriculeManager
    ){
        //Recupère le technicien à partir de son id
        Technicien technicien = technicienRepository.findById(idTechnicien).get();

        //Récupere le manager à partir de son matricule
        Manager manager = (Manager) employeRepository.findByMatricule(matriculeManager);

        // Lire le manager et le technicien
        technicien.setManager(manager);

        //Sauvegarde du technicien
        return technicienRepository.save(technicien);
    }
}
