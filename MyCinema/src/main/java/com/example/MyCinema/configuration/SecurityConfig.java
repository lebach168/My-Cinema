package com.example.MyCinema.configuration;

import jakarta.servlet.DispatcherType;
import org.springframework.aot.generate.ValueCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    private final String[] PUBLIC_ENDPOINTS = {
            "/**", "/auth/**"
    };

    @Value("${jwt.signerKey}")
    String SECRET_KEY;

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity, DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        ValueCodeGenerator Customizer;
        httpSecurity
            .authorizeHttpRequests(request -> request.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    //user permission
                    .requestMatchers("api/v1/users/**").hasRole("USER")
                    .requestMatchers("/api/v1/tickets/**").hasRole("USER")
                    .requestMatchers("/api/v1/reservation/**").hasRole("USER")
                    //staff permission
                    .requestMatchers(HttpMethod.GET,"/api/v1/staffs/**").hasRole("STAFF")
                    .requestMatchers("/api/v1/staffs/**").hasRole("ADMIN") // only admin can modify staff account
                    .requestMatchers(HttpMethod.DELETE).hasRole("STAFF")
                    .requestMatchers(HttpMethod.POST).hasRole("STAFF")
                    .requestMatchers(HttpMethod.PUT).hasRole("STAFF")
                    .requestMatchers(HttpMethod.PATCH).hasRole("STAFF")

                    //public api,author owner info -> method security
                    .requestMatchers(HttpMethod.GET,"/**").permitAll()

                    //admin permission
                    .anyRequest().hasRole("ADMIN"))
                    .exceptionHandling((exception)-> exception.authenticationEntryPoint(delegatedAuthenticationEntryPoint));
        ;

        httpSecurity.oauth2ResourceServer(oauth2-> oauth2.jwt(jwtConfigurer ->
                jwtConfigurer
                        .decoder(jwtDecoder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())));
        //httpSecurity.authorizeHttpRequests(request-> request.anyRequest().permitAll());


        return httpSecurity.build();
    }


    @Bean
    public RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.withRolePrefix("ROLE_")
                .role("ADMIN").implies("STAFF")
                .role("STAFF").implies("USER")
                .build();
    }

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder
                .withSecretKey(new SecretKeySpec(SECRET_KEY.getBytes(),"HS256"))
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

}