package HeartBeat.StudyConnection.configuration;


import HeartBeat.StudyConnection.configuration.jwt.TokenAuthenticationFilter;
import HeartBeat.StudyConnection.service.userInfoService.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    private final UserDetailService userService;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    // 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return ((web) -> web.ignoring()
                //.requestMatchers(toH2Console())
                .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-test","/v3/api-docs/**"));
    }

    // 필터 구현
    // vue.js와 어떤식으로 상호작용하는지 몰라 formLogin 등 설정 미완
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .cors(c -> c.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(c -> c.disable())
                .httpBasic((httpBasic) -> httpBasic.disable())
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable())); // X-Frame-Options 비활성화;

        http
                .authorizeHttpRequests((auth) ->
                        auth
                                .requestMatchers("/api/login", "/api/signup", "/api/**").permitAll()
                                .requestMatchers("/chatting/**", "/ws/**").permitAll()
                                .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-test","/v3/api-docs/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가;

        return http.build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService)
            throws Exception{
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and().build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080", "http://13.125.49.195:8080"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // password encoder
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
