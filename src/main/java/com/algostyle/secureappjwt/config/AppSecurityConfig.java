package com.algostyle.secureappjwt.config;


import com.algostyle.secureappjwt.filter.AppFilter;
import com.algostyle.secureappjwt.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Autowired
    private AppFilter filter;

    @Autowired
    private MyUserDetailsService userDetailsService;





    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }





    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                // Désactiver la protection CSRF (Cross-Site Request Forgery)
                .csrf().disable()

                // Configuration des autorisations des requêtes HTTP
                .authorizeHttpRequests()

                // Autoriser l'accès sans authentification aux routes suivantes
                .requestMatchers("/studentapi/login","/studentapi/register").permitAll()

                .and()

                // toutes les autres routes commençant par /studentapi/ nécessitent une authentification
                .authorizeHttpRequests().requestMatchers("/studentapi/**")
                .authenticated()

                .and()

                // Gestion de la session : le mode STATELESS signifie qu'aucune session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)


                .and()


                // Définition du fournisseur d'authentification personnalisé
                .authenticationProvider(authenticationProvider())

                // Ajout d'un filtre personnalisé avant le filtre standard d'authentification
                // basé sur le nom d'utilisateur et le mot de passe
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)

                // Construction de la chaîne de filtres de sécurité
                .build();
    }
}
