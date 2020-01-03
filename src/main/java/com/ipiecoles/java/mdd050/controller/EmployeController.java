package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employes")
public class EmployeController {

    @Autowired // Un repository existe et j'ai besoin de l'utiliser
    private EmployeRepository employeRepository;

    @RequestMapping("/count") //concaténation de l'URL d'en haut
    public long countEmployes(){
        //Récupérer le nombre d'employés et l'envoyer au client
        return employeRepository.count();
    }
}
