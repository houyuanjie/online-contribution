package edu.xsyu.onlinesubmit.security;

import edu.xsyu.onlinesubmit.enumeration.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests(requests -> requests
                        /*
                         * 页面访问授权规则, 请将越具体的规则放在前面
                         * 这里设计了一种非常简单的模式:
                         *     USER 角色的用户可以访问 CONTENT_PATH/user/ 下的页面
                         *     EDITOR 角色则是 CONTENT_PATH/editor/
                         * 以此类推, 请在 controller 包和 jsp 包中保持这样的目录结构以方便识别
                         */
                        .antMatchers("/user/**").hasAnyRole(Role.USER.name(), Role.EDITOR.name(), Role.ADMIN.name())
                        .antMatchers("/editor/**").hasAnyRole(Role.EDITOR.name(), Role.ADMIN.name())
                        .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
                        /*
                         * 请将 jsp 中要使用到的静态资源放置在 /public/** 或者 /static/**
                         * 其它端口配置为 permitAll
                         */
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                )
                .rememberMe(Customizer.withDefaults())
                .logout(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

}
