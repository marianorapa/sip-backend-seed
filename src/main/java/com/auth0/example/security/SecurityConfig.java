package com.auth0.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  private final AuthenticationErrorHandler authenticationErrorHandler;

  public SecurityConfig(AuthenticationErrorHandler authenticationErrorHandler) {
    this.authenticationErrorHandler = authenticationErrorHandler;
  }

  @Bean
  public SecurityFilterChain httpSecurity(final HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/api/private", "/api/private-scoped").authenticated()
            .anyRequest().permitAll())
        .cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(makePermissionsConverter()))
            .authenticationEntryPoint(authenticationErrorHandler))
        .build();
  }

  private JwtAuthenticationConverter makePermissionsConverter() {
    final var jwtAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtAuthoritiesConverter.setAuthoritiesClaimName("permissions");
    jwtAuthoritiesConverter.setAuthorityPrefix("");

    final var jwtAuthConverter = new JwtAuthenticationConverter();
    jwtAuthConverter.setJwtGrantedAuthoritiesConverter(jwtAuthoritiesConverter);

    return jwtAuthConverter;
  }
}