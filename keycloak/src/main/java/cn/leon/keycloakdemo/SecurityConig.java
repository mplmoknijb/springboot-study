package cn.leon.keycloakdemo;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import java.net.URI;

/**
 * @ClassName SecurityConig
 * @Description
 * @Author Jevon
 * @Date2020/1/16 13:57
 **/
@KeycloakConfiguration
public class SecurityConig extends KeycloakWebSecurityConfigurerAdapter {
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    @Primary
    public KeycloakConfigResolver keycloakConfigResolver(KeycloakSpringBootProperties properties) {
        return new CustomKeycloakSpringBootConfigResolver(properties);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/**/*")
//                .authenticated();
        http.authorizeRequests().anyRequest().authenticated()
                .and().formLogin().disable().httpBasic().disable()
                .authorizeRequests()
                .and()
                .oauth2Login().and()
//                .logout(o -> {
//            OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
//            successHandler.setPostLogoutRedirectUri(URI.create("http://localhost:8082"));
//            o.logoutSuccessHandler(successHandler);
//        })
                .logout()
                .logoutUrl("/signout")
                .and().csrf().csrfTokenRepository(new HttpSessionCsrfTokenRepository());
    }
}
