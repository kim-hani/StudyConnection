package HeartBeat.StudyConnection.configuration;


import HeartBeat.StudyConnection.userInfo.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig{

    private final UserDetailService userService;

    // 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return ((web) -> web.ignoring()
                .requestMatchers(toH2Console()));
    }

    // 필터 구현
    // vue.js와 어떤식으로 상호작용하는지 몰라 formLogin 등 설정 미완
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/login", "/api/signup").permitAll()
                        .anyRequest().authenticated()
                        );
        return http.build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager
    (AuthenticationManagerBuilder authBuilder, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService)
    throws Exception{
       authBuilder
               .getSharedObject(AuthenticationManagerBuilder.class)
               .userDetailsService(userService)
               .passwordEncoder(bCryptPasswordEncoder);

       return authBuilder.build();
    }

    // password encoder
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
