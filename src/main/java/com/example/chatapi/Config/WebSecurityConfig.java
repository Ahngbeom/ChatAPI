package com.example.chatapi.Config;

import com.example.chatapi.Security.CustomOAuth2UserService;
import com.example.chatapi.Security.CustomUserDetailService;
import com.example.chatapi.Security.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // @Secured, @PreAuthorize, @PostAuthorize 활성화
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final CustomUserDetailService customUserDetailService;

	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
		return new LoginSuccessHandler();
	}

	/*
        PasswordEncoder는 Spring Security의 Interface 객체이다.
        따라서 PasswordEncoder의 구현체를 대입해주고, Bean으로 등록해줄 필요가 있다.
        PasswordEncoder의 구현체로 BCryptPasswordEncoder 객체를 지정한다.
        BCryptPasswordEncoder 객체는 Hash 함수를 이용하여 패스워드를 암호화하는 구현체이다.
        PasswordEncoder는 UserServiceImpl 클래스에서 유저를 DB에 등록하기 전, 패스워드를 암호화하는 과정에 사용된다.
        https://youngjinmo.github.io/2021/05/passwordencoder/
         */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.userDetailsService(customUserDetailService)
				// CSRF 비활성화
				.csrf().disable()
				// 특정 경로 접근 권한 설정. "/signup", "/sign-up", "/webjars/**" 패턴의 요청 주소는 모두에게 접근 허용
				.authorizeRequests()
				.antMatchers("/webjars/**", "/js/**", "/template/**", "/test/**").permitAll()
				.antMatchers("/api/user/signup", "/**/*-validation").permitAll()
				.anyRequest().authenticated()
				.and()

                // Login
				.formLogin()
				.loginPage("/login")
//				.loginProcessingUrl("/login")
				.permitAll()
				.successHandler(myAuthenticationSuccessHandler())
				.and()

				// Remember Login Status
				.rememberMe()
				.key("MBTI Chatting Application Login Remember Me")
				.rememberMeParameter("remember-me")
                .tokenValiditySeconds(86400 * 30) // 1 Month
				.userDetailsService(customUserDetailService)
				.authenticationSuccessHandler(myAuthenticationSuccessHandler())
				.and()

				// Logout
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				// 로그아웃 시 Session 무효화, JSESSIONID 쿠키 삭제
				.invalidateHttpSession(true).deleteCookies("JSESSIONID", "remember-me")
				.permitAll()
				.and()

				// OAuth2
//				.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
				.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint().userService(customOAuth2UserService);

		return http.build();
	}

}
