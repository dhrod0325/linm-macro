package lm.macro.spring.config.security;

import lm.macro.security.LmAuthenticationProvider;
import lm.macro.security.LmUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class LmWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Resource
    private DataSource dataSource;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new LmAuthenticationProvider();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();

        registry.antMatchers("/assets/**", "/statics/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProcess")
                .failureUrl("/login?error=1").permitAll()
                .usernameParameter("id")
                .passwordParameter("pw")
                .and()
                .logout()
                .logoutUrl("/logout")
                .and()
                .rememberMe()
                .key("remember-me")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(2419200)
                .userDetailsService(userDetailsService())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new LmUserDetailService();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);

        return db;
    }
}