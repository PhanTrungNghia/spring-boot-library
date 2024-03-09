package com.luv2code.springbootlibrary.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable Cross-Site Request forgery - CORS
        // http.csrf().disable();
        http.csrf(csrf->csrf.disable());

        // Protect endpoint at /api/<type>/secure => Vd: /api/books/secure
        http.authorizeRequests(
                configurer -> configurer
                        .requestMatchers(
                                "/api/books/secure/**",
                                "/api/reviews/secure/**",
                                "/api/messages/secure/**",
                                "/api/admin/secure/**"
                        )
                        .authenticated()
        );

        // OAuth2 Okta Config
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));

        // Add CORS Filter
        http.cors(withDefaults());

        // Add content negotiation strategy for response data
        http.setSharedObject(
                    ContentNegotiationStrategy.class,
                    new HeaderContentNegotiationStrategy()
                );

        // Force a non-empty response body for 401's to make response friendly
        // Ép buộc một thân phản hồi không trống cho các mã 401 để làm cho phản hồi thân thiện hơn
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }
}
