package br.com.lanproject.security;

import br.com.lanproject.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/usuarios").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }

//    @Autowired
//    private AutenticacaoService autenticacaoService;
//
//    @Autowired
//    private TokenService tokenService;
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(this.autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((authz) -> {
//            try {
//                authz
//                    .antMatchers(HttpMethod.POST, "/auth").permitAll()
//                    .antMatchers(HttpMethod.POST, "/usuarios").permitAll()
//                    .anyRequest()
//                    .authenticated().and().csrf().disable()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository),
//                                UsernamePasswordAuthenticationFilter.class);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }).httpBasic(withDefaults());
//        return http.build();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/**.html",
//                "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
//    }
}
