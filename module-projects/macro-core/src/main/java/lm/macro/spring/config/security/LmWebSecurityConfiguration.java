package lm.macro.spring.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class LmWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//    private LmLog logger = new LmLog(getClass());
//
//    @Bean
//    public LmAuthenticationManager lmAuthenticationManager() {
//        return new LmAuthenticationManager();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        RemoteAuthenticationProvider provider = new RemoteAuthenticationProvider();
//        System.out.println(lmAuthenticationManager() + "<---");
//
//        provider.setRemoteAuthenticationManager(lmAuthenticationManager());
//
//        return provider;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
//
//        registry.antMatchers("/assets/**,/statics/**")
//                .permitAll().anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/loginProcess")
//                .failureUrl("/login?error=1").permitAll()
//                .usernameParameter("id")
//                .passwordParameter("pw")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .and()
//                .rememberMe()
//                .key("remember-me")
//                .tokenValiditySeconds(2419200)
//                .and()
//                .csrf()
//                .disable();
//    }
}