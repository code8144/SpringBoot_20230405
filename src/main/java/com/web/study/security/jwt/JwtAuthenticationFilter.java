package com.web.study.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	// 생성될때 jwtTokenProvider에 들어옴
	private final JwtTokenProvider jwtTokenProvider;

	// chain 이 객체를 거치고 다음 필터로 나가게하는것 c->f->f->f ->controller
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Bearer잘라낸 토큰 
		String token = getToken(request);

		boolean validationFlag = jwtTokenProvider.validateToken(token);

		if ( validationFlag) {
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(request, response);

	}

	private String getToken(ServletRequest request) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String type = "Bearer";
		String token = httpServletRequest.getHeader("Authorization");

		// hasText 문자열이 Null, 공백이 아닌지 확인, Bearer로 시작하는 지 확인 
		// 앞에있는 Bearer을 잘라냄 
		if (StringUtils.hasText(token) && token.startsWith(type)) {
			return token.substring(type.length() + 1);
		}
		return null;

	}

}
