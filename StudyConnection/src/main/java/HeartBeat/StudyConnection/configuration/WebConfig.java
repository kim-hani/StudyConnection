package HeartBeat.StudyConnection.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://13.125.49.195:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH")
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(3000);// 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
               // .allowedHeaders("*");
    }
}
