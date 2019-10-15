package cn.bptop.metering.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by yangyibo on 17/1/18.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
//        http.authorizeRequests().antMatchers("/user/login").permitAll()
//                .and().authorizeRequests().anyRequest().authenticated()
//                .and().formLogin().loginPage("/login.html").permitAll()
//                .and().logout().permitAll();
        http.authorizeRequests().anyRequest().permitAll()
                .and().logout().permitAll();
        http.csrf().disable();
    }
}

