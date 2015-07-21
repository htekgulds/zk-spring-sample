package tr.gov.tuik.zk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.inject.Inject;

/**
 * Created by Hasan TEKGÜL
 * on 7/13/2015
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public ActiveDirectoryLdapAuthenticationProvider adAuthenticationProvider() {
//        ActiveDirectoryLdapAuthenticationProvider provider =
//                new ActiveDirectoryLdapAuthenticationProvider(
//                        "tuikmerkez",   // domain
//                        "ldap://tuikmerkez.tuik.gov.tr:389",    // url
//                        "ou=TUIK BASKANLIGI,DC=tuikmerkez,DC=tuik,DC=gov,DC=tr" // root
//                );
//        provider.setConvertSubErrorCodesToExceptions(true);
//        provider.setSearchFilter("(&(objectClass=user)(sn=*)(bysUserName=*)(|(bysUserName={0})(cn={0})))");
//        return provider;
//    }

    @Inject
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
//                .authenticationProvider(adAuthenticationProvider())   // Ldap
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/zkau/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/login.zul").anonymous()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login.zul")
                .loginProcessingUrl("/j_spring_security_check")
                .defaultSuccessUrl("/index.zul")
                .failureUrl("/login.zul?login_error=1")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
            .logout()
                .invalidateHttpSession(true)
                .permitAll();
    }
}
