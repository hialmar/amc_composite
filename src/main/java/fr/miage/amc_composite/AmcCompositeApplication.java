package fr.miage.amc_composite;

import com.okta.spring.boot.oauth.Okta;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.logging.Logger;

@SpringBootApplication
// Activation enregistrement Annuaire
@EnableDiscoveryClient
// Activation et auto-confoguiration de clients Feign
@EnableFeignClients
// Activation LoadBalancer
@EnableMethodSecurity(prePostEnabled = true)
public class AmcCompositeApplication {

    Logger logger = Logger.getLogger(AmcCompositeApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(AmcCompositeApplication.class, args);
    }

    @Bean
    @ConditionalOnMissingBean
    ObservationRegistry observationRegistry() {
        PathMatcher pathMatcher = new AntPathMatcher("/");
        ObservationRegistry observationRegistry = ObservationRegistry.create();
        observationRegistry.observationConfig().observationPredicate((name, context) -> {
            if(context instanceof ServerRequestObservationContext) {
                return !pathMatcher.match("/actuator/**", ((ServerRequestObservationContext) context).getCarrier().getRequestURI());
            } else {
                return true;
            }
        });
        return observationRegistry;
    }


    // ET LA
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        // process CORS annotations
        http.cors(Customizer.withDefaults());

        // force a non-empty response body for 401's to make the response more browser friendly
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }


}
