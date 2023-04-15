package com.web.study.security.jwt;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.web.study.dto.response.auth.JwtTokenRespDto;
import com.web.study.exception.CustomException;
import com.web.study.security.PrincipalUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
//jwttoken은 json이라 문자열임 
public class JwtTokenProvider {

	private final Key key;

	// 가장 중요한 키 , 다른 곳에 유출되면 안됨
	public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);

	}

	public JwtTokenRespDto createToken(Authentication authentication) {
		StringBuilder authoritiesBuilder = new StringBuilder();

		authentication.getAuthorities().forEach(grantedAuthority -> {
			authoritiesBuilder.append(grantedAuthority.getAuthority());
			authoritiesBuilder.append(",");
		});

		authoritiesBuilder.delete(authoritiesBuilder.length() - 1, authoritiesBuilder.length());

		// 문자열 변환
		String authorities = authoritiesBuilder.toString();

		// 현재시간 가져오기
		long now = (new Date()).getTime();

		// 1000 == 1초, 탈취당할 위험이 있어서 길게 시간을 주지 않음
		Date tokenExpriesDate = new Date(now + (1000 * 60 * 30)); // 토큰 만료 시간, 현재로부터 30분 뒤 

		// 매개변수로 받은 곳 안에 userdetails가 들어있고 이것을 다운캐스팅, 다운캐스팅해야 안에 들어있는 객체들을 사용가능
		PrincipalUserDetails userDetails = (PrincipalUserDetails) authentication.getPrincipal();

		//jwt 토큰 생성 
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())
				.claim("userId", userDetails.getUserId())
				.claim("auth", authorities).setExpiration(tokenExpriesDate)
				.signWith(key, SignatureAlgorithm.HS256) // 암호화
				.compact();

		// 이것을 return 해주면 controller가 받음 
		return JwtTokenRespDto.builder().grantType("Bearer").accessToken(accessToken).build();

	}

	public boolean validateToken(String token) {
		try {
			// 자바에서 쓸수 있게끔
			Jwts.parserBuilder().setSigningKey(key) // 라이브러리
					.build().parseClaimsJws(token); // 이 토큰이 문제가 생기면 예외를 처리를 해주어야함
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			// Security라이브러리에 오류가 있거나, Json의 포맷이 잘못된 Jwt가 들어왔을 경우의 예외
			log.info("Invalid JWT Token", e);

		} catch (ExpiredJwtException e) {
			// 토큰의 유효기간이 만료될 경우의 예외
			log.info("Expired JWT Token", e);

		} catch (UnsupportedJwtException e) {
			// jwt의 형식을 지키지 않은 경우 (Header.Payload.Signature)
			// SignatureException이 포함되어 있을 경우
			log.info("Unsupported JWT Token", e);

		} catch (IllegalArgumentException e) {
			// Argument가 아예들어오지 않은 경우 , Jwt토큰이 없을 때
			log.info("IllegalArgument JWT Token", e);
		} catch (Exception e) {
			log.info("JWT Token Error", e);

		}
		return false; // 인증되지 않음

	}

	public Authentication getAuthentication(String accessToken) {
		// claims를 통째로 가지고 옴 
		Claims claims = parseClaims(accessToken);
		// 인증을 하기 위해서 필요한 정보들만 가지고 옴 
		Object roles = claims.get("auth");
		
		if(roles == null) {
			//예외를 던져버림, 권한은 무조건 있어야함 
			throw new CustomException("권한 정보가 없는 토큰입니다");
		}
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		String[] rolesArray  = roles.toString().split(",");
		Arrays.asList(rolesArray).forEach(role -> {
			//문자열을 잘라다가 simplegrantedAuthority에 적용
			authorities.add(new SimpleGrantedAuthority(role));
		});
		
		UserDetails userDetails = new User(claims.getSubject(), "", authorities);
		return  new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
			

	}
	private Claims parseClaims(String accessToken) {
		try {
			//바디를 리턴 해줌 ,header, body, ? 중 바디를 담당 
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(accessToken)
					.getBody(); 
		} catch(ExpiredJwtException e) {
			return e.getClaims();
			
		}
		
	}

}
