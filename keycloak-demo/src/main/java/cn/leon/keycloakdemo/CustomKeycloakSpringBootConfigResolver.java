package cn.leon.keycloakdemo;

import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName CustomKeycloakSpringBootConfigResolver
 * @Description
 * @Author Jevon
 * @Date2020/1/16 14:45
 **/
@Configuration
public class CustomKeycloakSpringBootConfigResolver extends KeycloakSpringBootConfigResolver {

    private final KeycloakDeployment deployment;
    public CustomKeycloakSpringBootConfigResolver(KeycloakSpringBootProperties properties) {
        this.deployment = KeycloakDeploymentBuilder.build(properties);
    }

    @Override
    public KeycloakDeployment resolve(HttpFacade.Request request) {
        return deployment;
    }
}
