package com.example.scvet.config;


import com.example.scvet.security.JwtAuthFilter;
import com.example.scvet.security.JwtService;
import com.example.scvet.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/agendamentos/**")
                .hasAnyRole("USER")
                .antMatchers("/api/v1/agendamentos/**")
                .hasAnyRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/v1/animais/**")
                .hasAnyRole("USER")
                .antMatchers("/api/v1/animais/**")
                .hasAnyRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/v1/consultas/**")
                .hasAnyRole("USER")
                .antMatchers("/api/v1/consultas/**")
                .hasAnyRole("ADMIN")

                .antMatchers("/api/v1/especialidades/**")
                .hasAnyRole("ADMIN")

                .antMatchers("/api/v1/funcoes/**")
                .hasAnyRole("ADMIN")

                .antMatchers("/api/v1/funcionarios/**")
                .hasAnyRole("ADMIN")

                .antMatchers("/api/v1/especies/**")
                .hasAnyRole("ADMIN")

                .antMatchers("/api/v1/clientes/**")
                .hasAnyRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/api/v1/usuarios/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }
}