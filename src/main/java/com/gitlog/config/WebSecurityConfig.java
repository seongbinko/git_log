package com.gitlog.config;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함 Spring Security Filter Chain 을 사용한다는 것
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }


    /*    AuthenticationManager 를 이용하여, 원하는 시점에 로그인이 될 수 있도록 바꿔보자.
        먼저, AuthenticationManager 를 외부에서 사용 하기 위해, AuthenticationManagerBean 을 이용하여 Sprint Securtiy 밖으로 AuthenticationManager 빼 내야 한다.*/
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(ImmutableList.of("*")); // 스프링부트 2.4부터 변경?
        configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "TOKEN_ID", "X-Requested-With", "Content-Type", "Content-Length", "Cache-Control"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()); // http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http
                .httpBasic().disable()
                .headers().frameOptions().disable().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/books/**").authenticated()
//                .antMatchers(HttpMethod.PUT, "/api/books/**").authenticated()
//                .antMatchers(HttpMethod.DELETE, "/api/books/**").authenticated()
                .anyRequest().permitAll();
//                .and()
//                .addFilterBefore(new JwtAutenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//                .antMatchers("/api/books").hasRole("USER")
    }


}
