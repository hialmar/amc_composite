package fr.miage.amc_composite.metier;


import fr.miage.amc_composite.clients.ClientClients;
import fr.miage.amc_composite.clients.ClientComptes;
import fr.miage.amc_composite.transientObj.Client;
import fr.miage.amc_composite.transientObj.ClientWithCompte;
import fr.miage.amc_composite.transientObj.Compte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

// Composant métier. On aurait pu mettre @Service
@Component
public class ClientsCompteRepositoryImpl implements ClientsCompteRepository {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ClientComptes clientcomptes;

    @Autowired
    ClientClients clientclients;

    // On va interroger successivement les 2 micro-services Client et Comptes
    @Override
    public ClientWithCompte getClientWithComptes(Long idclient) {
        logger.info("On a 1 demande");
        logger.info("On envoie la demande au service client");

        try {
            // On récupère 1 objet client
            Client c = this.clientclients.getClient(idclient);
            logger.info("On a recue la réponse client : {}", c);

            // On récupère la liste des comptes pour 1 client donné
            logger.info("On envoie la demande au service compte");
            List<Compte> cpts = this.clientcomptes.getComptes(c.getId());
            logger.info("On a recue la réponse compte : {}", c);

            // On forge la réponse
            ClientWithCompte cwc = new ClientWithCompte();
            cwc.setId(c.getId());
            cwc.setNom(c.getNom());
            cwc.setPrenom(c.getPrenom());
            cwc.setComptes(cpts);
            return cwc;
        } catch(feign.FeignException feignException) {
            return new ClientWithCompte();
        }



    }

}
