package fr.miage.amc_composite.expo;


import fr.miage.amc_composite.metier.ClientsCompteRepository;
import fr.miage.amc_composite.transientObj.ClientWithCompte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Service d'exposition REST des comptes-clients.
 * URL / exposée.
 */
@RestController()
/*
    AVANT : @RequestMapping("/api/clientscompte")
    Modif : plus besoin de /api/clientscompte via
    l'usage de l'API Gateway qui va masquer le chemin de l'URL
 */
@RequestMapping("/")
public class ClientCompteController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    // Injection DAO clients-compte
    @Autowired
    ClientsCompteRepository clientsCompteRepository;

    /**
     * GET 1 client AVEC la liste de ses comptes
     * @param id id du client
     * @return  converti en JSON
     */
    @GetMapping("{id}")
    public ClientWithCompte getClient(@PathVariable("id") Long id, @RequestHeader("Authorization") String bearerToken) {
        logger.info("ClientComptes : demande récup comptes d'un client avec id:{}", id);
        ClientWithCompte c = clientsCompteRepository.getClientWithComptes(id, bearerToken);
        logger.info("ClientComptes : demande récup comptes client:{}", c);
        return c;
    }

}
