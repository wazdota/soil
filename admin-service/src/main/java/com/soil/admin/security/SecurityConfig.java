package com.soil.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled =  true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private GoAuthenticationSuccessHandler goAuthenticationSuccessHandler;
    private GoAccessDeniedHandler goAccessDeniedHandler;
    private GoAuthenticationEntryPoint goAuthenticationEntryPoint;
    private GoAuthenticationFailureHandler goAuthenticationFailureHandler;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsServiceImpl,
                          JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
                          GoAuthenticationSuccessHandler goAuthenticationSuccessHandler,
                          GoAccessDeniedHandler goAccessDeniedHandler,
                          GoAuthenticationEntryPoint goAuthenticationEntryPoint,
                          GoAuthenticationFailureHandler goAuthenticationFailureHandler) {
        this.userDetailsService = userDetailsServiceImpl;
        this.goAccessDeniedHandler = goAccessDeniedHandler;
        this.goAuthenticationEntryPoint = goAuthenticationEntryPoint;
        this.goAuthenticationFailureHandler = goAuthenticationFailureHandler;
        this.goAuthenticationSuccessHandler = goAuthenticationSuccessHandler;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().authenticationEntryPoint(goAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/v1/admin").hasRole("SUPER")
                .antMatchers(HttpMethod.DELETE,"/v1/admin").hasRole("SUPER")
                .antMatchers("/v1/admin").hasRole("ADMIN")
                .antMatchers("/v1/admin_s/**").hasRole("SUPER")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .successHandler(goAuthenticationSuccessHandler)
                .failureHandler(goAuthenticationFailureHandler)
                .permitAll();
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(goAccessDeniedHandler);
    }


}
