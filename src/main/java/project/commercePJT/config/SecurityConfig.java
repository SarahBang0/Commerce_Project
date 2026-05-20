package project.commercePJT.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF 보안 비활성화 (테스트할 때 이게 켜져 있으면 POST 요청이 안 돼요!)
                .csrf(csrf -> csrf.disable())

                // 2. 모든 요청에 대해 인증 없이 허용 (로그인 창 안 뜨게 함)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // 3. 기본 로그인 폼 비활성화
                .formLogin(form -> form.disable())

                // 4. H2 콘솔 사용을 위해 프레임 옵션 비활성화
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}