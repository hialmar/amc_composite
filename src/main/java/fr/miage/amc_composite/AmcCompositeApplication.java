package fr.miage.amc_composite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// Activation enregistrement Annuaire
@EnableDiscoveryClient
// Activation et auto-confoguiration de clients Feign
@EnableFeignClients
// Activation LoadBalancer
public class AmcCompositeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmcCompositeApplication.class, args);
    }

}
