package kr.or.ddit.yguniv.security.conf;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import kr.or.ddit.yguniv.login.controller.CustomAuthFailureHandler;
import kr.or.ddit.yguniv.login.controller.CustomAuthSuccessHandler;
import kr.or.ddit.yguniv.person.service.PersonService;
import kr.or.ddit.yguniv.security.authorize.ReloadableAuthorizationManager;
import kr.or.ddit.yguniv.security.conf.resource.dao.SecuredResourceMapper;
import kr.or.ddit.yguniv.security.conf.resource.service.CusUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "kr.or.ddit.yguniv.security.conf.resource.service")
@Slf4j
public class SecurityContextJavaConfig {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final PersonService customUserDetailsService;

	private final CustomAuthFailureHandler customFailureHandler;
	private final CustomAuthSuccessHandler successHandler;

//	private final AuthenticationFailureHandler  customFailureHandler;
//	private final AuthenticationSuccessHandler successHandler;

	@PostConstruct
	public void init() {
		authenticationManagerBuilder.eraseCredentials(false);
	}

	@Bean // 비밀번호 암호화
	public PasswordEncoder passwordEncoder() {
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();  // 나중에 비밀번호 암호화해서 저장하면 쓸것
		return new PasswordEncoder() { // 그냥 단순 문자열로만 암호화

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return encodedPassword.equals(rawPassword);
			}

			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}
		};
	}

//	@Bean // 정적 권한 설정
//	public UserDetailsService userDetailService(PasswordEncoder encoder) {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("a001").password(encoder.encode("java")).roles("USER").build());
//		manager.createUser(User.withUsername("c001").password(encoder.encode("java")).roles("ADMIN").build());
//		
//		return manager;
//	}

	/**
	 * AuthenticationManager 설정
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	/**
	 * 정적 리소스 보안 설정
	 */

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/resources/**");
	}
	@Inject
	 private SecuredResourceMapper resMapper;
	 @Bean
	 public AuthorizationManager<HttpServletRequest> customAuthrozationManager(){
		 return new ReloadableAuthorizationManager(resMapper);
	 }
//	 customAuthrozationManager()
	 private final UserDetailsService PersonDAO;

	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	     http
	         .userDetailsService(PersonDAO) // UserDetailsService 추가하여 UserName 가져오기 
	         .anonymous().authorities("ROLE_ANONYMOUS").and()
	         .addFilterAt(new AuthorizationFilter(customAuthrozationManager()), AuthorizationFilter.class)
	         .authorizeHttpRequests(authorize -> authorize
	             .antMatchers("/loginForm", "/2fa").permitAll() //인증 없이 접근 가능
	             .antMatchers("/**").authenticated() // 모든 요청 인증 필요
	             .anyRequest().permitAll()
	         )
	         .formLogin()
	             .loginPage("/loginForm") // 인증되지 않은 사용자가 접근할 때 이동할 로그인 페이지 
	             .loginProcessingUrl("/login") // 로그인 요청 처리 URL
	             .defaultSuccessUrl("/index.do", true) // 로그인 성공 시
	             .failureUrl("/loginForm?error=true") // 로그인 실패 시
	             .successHandler(successHandler) // 성공 핸들러
	             .failureHandler(customFailureHandler) // 실패 핸들러
	             .usernameParameter("id") // 로그인 ID 필드 이름 customFailureHandler
	             .passwordParameter("pswd") // 로그인 비밀번호 필드 이름 
	         .and()
	         .logout()
	             .logoutUrl("/logout") // 로그아웃 요청 URL
	             .logoutSuccessUrl("/loginForm?logout=true") // 로그아웃 성공 후 이동 페이지
	             .invalidateHttpSession(true) // 세션 무효화
	             .deleteCookies("JSESSIONID") // 쿠키 삭제 
	         .and()
	         .csrf().disable();

	     return http.build();
	 }


	 
//	 @Bean //WEB07 예제 코드
//	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	 http.anonymous().authorities("ROLE_ANONYMOUS").and()
//					 .addFilterAt(new AuthorizationFilter(customAuthrozationManager()), AuthorizationFilter.class)
//					 .formLogin()
//					 .and()
//					 .csrf().disable();
//	 return http.build();
//	 }
	
	 
//	@Bean // 접근 설정 //YGUNIV 원본코드 
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.anonymous().authorities("ROLE_ANONYMOUS").and().authorizeHttpRequests(authorize -> authorize
//									.antMatchers("/loginForm","/2fa").permitAll()
////									.antMatchers("/member/*List*").hasRole("ADMIN")
////									.antMatchers("/member/*Insert*").hasRole("ANONYMOUS")
////									.antMatchers("/prod/*Insert*").hasRole("ADMIN")
////									.antMatchers("/prod/*Update*").hasRole("ADMIN")
////									.antMatchers("/buyer/**").hasRole("ADMIN")
////									.antMatchers("/mypage").authenticated()
//				.antMatchers("/**").authenticated().anyRequest().permitAll()).formLogin().loginPage("/loginForm") // 사용자
//				.loginProcessingUrl("/login") // 로그인 처리 URL
//				.defaultSuccessUrl("/index.do", true) // 로그인 성공 시 이동할 URL
//				.failureUrl("/loginForm?error=true") // 로그인 실패 시 이동할 URL
//				.successHandler(successHandler) // 성공 핸들러
//				.failureHandler(customFailureHandler) // 실패 핸들러 등록
//				.usernameParameter("id") // 사용자 ID 필드명
//				.passwordParameter("pswd") // 비밀번호 필드명
//				.and()
//				.logout()
//				.logoutUrl("/logout") // 로그아웃 요청 URL 설정
//				.logoutSuccessUrl("/loginForm?logout=true") // 로그아웃 성공 후 이동할 URL
//				.invalidateHttpSession(true) // 세션 무효화
//				.deleteCookies("JSESSIONID") // 쿠키 삭제 
//				.and().csrf().disable();
//
//		return http.build();
//	}
	 @Bean
		public HandlerMappingIntrospector handlerIntrospector() {
			return new HandlerMappingIntrospector();
		}
}
