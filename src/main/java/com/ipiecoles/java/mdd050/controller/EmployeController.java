package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employes")
public class EmployeController {

    @Autowired // Un repository existe et j'ai besoin de l'utiliser
    private EmployeRepository employeRepository;

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

}