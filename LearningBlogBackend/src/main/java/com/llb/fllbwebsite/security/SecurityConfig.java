package com.llb.fllbwebsite.security;

import com.llb.fllbwebsite.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static com.llb.fllbwebsite.security.SecurityConstants.*;

import static com.llb.fllbwebsite.security.SecurityConstants.H2_URL;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {return new JwtAuthenticationFilter();}

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, CustomUserDetailsService customUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.customUserDetailsService = customUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE, DELETE_USER_URL).hasRole(SUPER_ADMIN_ROLE)
                .antMatchers(USERS_LIST_URL).hasRole(SUPER_ADMIN_ROLE)
                .antMatchers(COMMENTS_AND_LIKES_LIST_URLS).hasRole(SUPER_ADMIN_ROLE)
                .antMatchers(CATEGORY_LIST_URL).hasRole(SUPER_ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, CREATE_CATEGORY_URL).hasRole(SUPER_ADMIN_ROLE)
                .antMatchers(HttpMethod.DELETE, DELETE_CATEGORY_URL).hasRole(SUPER_ADMIN_ROLE)
                .antMatchers(ROLE_URLS).hasRole(SUPER_ADMIN_ROLE)
                .antMatchers(USER_POSTS_LIST_URL).hasAnyRole(SUPER_ADMIN_ROLE, SUB_ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, CREATE_POSTS_URL).hasAnyRole(SUPER_ADMIN_ROLE, SUB_ADMIN_ROLE)
                .antMatchers(HttpMethod.DELETE, DELETE_POSTS_URL).hasAnyRole(SUPER_ADMIN_ROLE, SUB_ADMIN_ROLE)
                .antMatchers(USER_UPDATE_URL).authenticated()
                .antMatchers(COMMENT_AND_LIKE_URLS).authenticated()
                .antMatchers(
                        "/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
//                .antMatchers(SIGN_UP_URLS).permitAll()
                .antMatchers(SIGN_UP_URL, SIGN_IN_URL, GET_USER_URL).permitAll()
                .antMatchers(POST_URLS).permitAll()
                .antMatchers(CATEGORY_URLS).permitAll()
                .antMatchers(H2_URL).permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
    }
}
