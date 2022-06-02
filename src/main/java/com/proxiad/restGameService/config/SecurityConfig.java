package com.proxiad.restGameService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//TODO refactor for proper REST, remove CSRF
@Configuration
public class SecurityConfig {
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain games(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .mvcMatchers("/", "/games/*", "/games/hangman/**", "/login", "/logout", "/register", "/rest/hangman/**").permitAll()
                        .mvcMatchers(HttpMethod.POST, "rest/hangman/start-game/*").permitAll()
                        .anyRequest().authenticated()
                )
//                .httpBasic(withDefaults())
                .authenticationProvider(authenticationProvider())
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .successForwardUrl("/")
                                .permitAll())
                .logout(logout ->
                        logout
                                .deleteCookies("JSESSIONID", "remember-me")
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/")
                                .permitAll()
                )
//                .csrf().disable()
        ;
        return http.build();
    }

    @Bean
    @Primary
    public DaoAuthenticationProvider authenticationProvider() {
        System.out.println("auth provider here");
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public RoleHierarchyImpl roleHierarchy() {
        System.out.println("custom role hierarchy here");
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }
//
//    @Bean
//    public RoleHierarchyVoter roleHierarchyVoter(RoleHierarchy roleHierarchy) {
//        System.out.println("arrive public RoleHierarchyVoter roleHierarchyVoter");
//        return new RoleHierarchyVoter(roleHierarchy);
//    }
//
//    @Bean
//    public AffirmativeBased defaultAccessDecisionManager(RoleHierarchy roleHierarchy) {
//        System.out.println("arrive public AffirmativeBased defaultAccessDecisionManager()");
//        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
//
//        // webExpressionVoter
//        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
//        DefaultWebSecurityExpressionHandler
//                expressionHandler = new DefaultWebSecurityExpressionHandler();
//        expressionHandler.setRoleHierarchy(roleHierarchy);
//        webExpressionVoter.setExpressionHandler(expressionHandler);
//
//        decisionVoters.add(webExpressionVoter);
//        decisionVoters.add(roleHierarchyVoter(roleHierarchy));
//        // return new AffirmativeBased(Arrays.asList((AccessDecisionVoter) webExpressionVoter));
//        return new AffirmativeBased(decisionVoters);
//    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("pass")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
