package fr.miage.amc_composite.clients;

import fr.miage.amc_composite.transientObj.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// Création d'un client REST pour le service "clients-service" enregistré dans l'annuaire
@FeignClient("amcclients")
public interface ClientClients {

    // Déclaration d'usage d'une méthode
    /*
        UPDATE API GATEWAY
        Ancien : @RequestMapping(method = RequestMethod.GET, value = "/api/clients/{id}", produces = "application/json")
        Modif URL pour enlever le /api/clients
     */
    @RequestMapping(method = RequestMethod.GET, value = "{id}", produces = "application/json")
    Client getClient(@PathVariable Long id);
}
