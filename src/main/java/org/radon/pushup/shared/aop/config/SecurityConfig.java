package org.radon.pushup.shared.aop.config;
import jakarta.transaction.Transactional;
import org.radon.pushup.features.user.application.port.in.CreateUserUseCase;
import org.radon.pushup.features.user.application.port.out.UserRepository;
import org.radon.pushup.features.user.domain.User;
import org.radon.pushup.features.user.infrastructure.repository.AuthorityJpaRepository;
import org.radon.pushup.features.user.infrastructure.repository.RoleJpaRepository;
import org.radon.pushup.features.user.infrastructure.repository.UserJpaRepository;
import org.radon.pushup.features.user.infrastructure.repository.entities.AuthorityEntity;
import org.radon.pushup.features.user.infrastructure.repository.entities.RoleEntity;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;
import org.radon.pushup.features.user.infrastructure.repository.mapper.UserMappers;
import org.radon.pushup.shared.aop.errorHandling.RestAccessDeniedHandler;
import org.radon.pushup.shared.aop.errorHandling.RestAuthenticationEntryPoint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JwtAuthFilter jwtAuthFilter;
    private final PasswordEncoder passwordEncoder;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    public SecurityConfig(UserRepository userRepository, JwtAuthFilter jwtAuthFilter, PasswordEncoder passwordEncoder, RestAuthenticationEntryPoint restAuthenticationEntryPoint, RestAccessDeniedHandler restAccessDeniedHandler) {
        this.userRepository = userRepository;
        this.jwtAuthFilter = jwtAuthFilter;
        this.passwordEncoder = passwordEncoder;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain publicChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/public/**","/public","/auth/**","/auth")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        var auth = new DaoAuthenticationProvider(userRepository);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }


    @Transactional
    @Bean
    public CommandLineRunner commandLineRunner(AuthorityJpaRepository authorityJpaRepository, RoleJpaRepository roleJpaRepository, UserJpaRepository userJpaRepository) {
        return args -> {

            try{
                if(roleJpaRepository.findByName("ADMIN") == null || roleJpaRepository.findByName("ADMIN").isEmpty()){

                    var create = authorityJpaRepository.save(new AuthorityEntity("CREATE"));
                    var add = authorityJpaRepository.save(new AuthorityEntity("ADD"));
                    var read = authorityJpaRepository.save(new AuthorityEntity("READ"));
                    var update = authorityJpaRepository.save(new AuthorityEntity("UPDATE"));
                    var remove = authorityJpaRepository.save(new AuthorityEntity("REMOVE"));

                    roleJpaRepository.save(new RoleEntity("ADMIN",Set.of(create,add,read,update,remove)));
                    var owner = roleJpaRepository.save(new RoleEntity("OWNER",Set.of(create,add,read,update)));
                    roleJpaRepository.save(new RoleEntity("DEVELOPER",Set.of(add,read)));
                    roleJpaRepository.save(new RoleEntity("ANALYST",Set.of(add,read)));
                    roleJpaRepository.save(new RoleEntity("VIEWER",Set.of(read)));

                    userJpaRepository.save(new UserEntity(
                            "alireza.kh",
                            passwordEncoder.encode("123456"),
                            "ali@gmail.com",
                            "09369101332",
                            owner
                    ));

                }

            }catch (Exception ex){

            }

        };
    }


}
