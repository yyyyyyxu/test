package com.yanxu.book.config;

import com.yanxu.book.auth.DaoAuthenticationProvider;
import com.yanxu.book.auth.HttpStatusLoginFailureHandler;
import com.yanxu.book.auth.JsonLoginConfigurer;
import com.yanxu.book.auth.JsonLoginSuccessHandler;
import com.yanxu.book.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private DataSource dataSource;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userLoginService).passwordEncoder(password());
//    }

    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/user/login")
                .failureHandler(new HttpStatusLoginFailureHandler())
                .defaultSuccessUrl("/book/bookDetails").successHandler(new JsonLoginSuccessHandler()).permitAll()
                .and().logout()
                .logoutUrl("/book/logout")
                .logoutSuccessUrl("/user/login").permitAll()
                .and().authorizeRequests()
                .antMatchers("/", "/user/login","user/loginFailure").permitAll()
                .anyRequest().authenticated()
                .and().rememberMe().tokenRepository(presistentTokenRepository())
                .tokenValiditySeconds(60 * 60)
                .and()
                .apply(new JsonLoginConfigurer<>()).loginSuccessHandler(new JsonLoginSuccessHandler())
                .and().csrf().disable();
    }
//

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean("daoAuthenticationProvider")
    protected AuthenticationProvider daoAuthenticationProvider() throws Exception {
        //这里会默认使用BCryptPasswordEncoder比对加密后的密码，注意要跟createUser时保持一致
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userLoginService);
        return daoProvider;
    }

    @Bean
    public PersistentTokenRepository presistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}

