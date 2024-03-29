package fr.miage.amc_composite.metier;


import fr.miage.amc_composite.transientObj.ClientWithCompte;

/**
 * Repository de manipulation des ClientsCompte
 */
public interface ClientsCompteRepository {

    ClientWithCompte getClientWithComptes(Long idclient, String bearerToken);

}
