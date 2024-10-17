package com.xiuxian.xiuxianserver.config;

import com.xiuxian.xiuxianserver.filter.JwtRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationEntryPoint customAuthenticationEntryPoint; // 自定义处理器

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(AuthenticationEntryPoint customAuthenticationEntryPoint, JwtRequestFilter jwtRequestFilter) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable())  // 关闭 CSRF 防护
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/user/**").authenticated()  // 保护用户 API
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()  // 允许对 Swagger 资源的访问
                        .anyRequest().authenticated()  // 其他请求需要认证
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)  // 使用自定义的 EntryPoint
                )

                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // 增加日志记录每个请求路径
        logger.info("Security filter chain initialized for securing requests");

        return http.build();
    }

    // 用于忽略静态资源的安全过滤
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**", "/public/**");
    }

    // 配置 PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 提供 AuthenticationManager Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.info("AuthenticationManager initialized");
        return authenticationConfiguration.getAuthenticationManager();
    }

}
