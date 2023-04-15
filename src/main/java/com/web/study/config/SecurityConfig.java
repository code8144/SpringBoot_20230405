package com.web.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.web.study.security.jwt.JwtAuthenticationEntryPoint;
import com.web.study.security.jwt.JwtAuthenticationFilter;
import com.web.study.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/*
	 * 요청이 들어오면 1개의 요청당 1개의 스레드 생성, 요청이 들어오는데 로그인이나 회원가입이면  Authentication 객체가 없어도 상관없음 
	 * 나머지는 Authentication가 필수적으로 필요
	 * 헤즈롤이 걸린 것들은 authentication  + getAuthorities(정보..?
	 * 요청이 들어오면 토큰으로 인증 , 필터를 들어와서 req.getHeader에서 get(Authorization을 get)을 들고오기 
	 *하드코딩(직접값을 넣는 코딩) 보다는 변수를 설정해서 넣는 것이 더 좋다, 하드코딩은 수정을 할 일이 많기 때문 / ex)7보다는 문자열의 길이를 들고옴 
	 * Bearer 한칸뒤에 있는 것이 실제 토큰, 이것을 subString으로 변환 
	 * 문자열의 길이를 자른다른 토큰이 유효한 길이의 토큰인지 검사
	 * 토큰을 풀어야하는데 그때 parsebuilder를 사용(빌더안에는  sign이 들어있음, 키값으로 정상적인 키값인지 확인 , 검증확인후 빌드 사용
	 * claim을 가지고 검사, 동작을 하는 동안에 예외가 발생할 가능성이 있어서 예외처리를 해줌 (try~ catch), 여기서 예외가 뜨면 프로그램 중단 , 스레드가 죽어버림 
	 * 그래서 예외가 뜨는 순간 403에러 발생 , exceptionHandler는 예외가 발생하면 무조건 이 과정을 거쳐라는 뜻, 여기에서 403 -> 401로 바꿔서 줌 
	 * securitycontext안에 Authentication 객체가 들어있으면 인증 성공 -> 로그인 성공 , 없으면 무조건 403오류
	 * 하지만 어드민이라는 권한이 없으면 403이 뜬다
	 * 
	 * password는 autheritity에 들어가면 전부 보여지기 때문에 넣으면 안된다
	 * 
	 * 
	 */
	
	
	// 여기서 configure의 역할은 security filter
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.httpBasic().disable(); //웹에서 기본 인증 방식 
		http.formLogin().disable(); // 폼태그를 통한 로그인 
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//세션 비활성(무상태성), 누구인지 기억하지 않음 
		
		http.authorizeRequests()
		.antMatchers("/auth/register/**", "/auth/login/**")
		.permitAll()
		.antMatchers("/course")
		.hasRole("ADMIN")// role을 자동으로 떼고 가져옴 
		.anyRequest()
		.authenticated()
		.and()
		.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
		//UsernamePasswordAuthenticationFilter.class 이것은 고정 )
		.exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint); //401은 인증되지 않았음, 403은 권한이 없음 
		}
}
