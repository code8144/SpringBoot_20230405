package com.web.study.domain.entity;

import java.util.ArrayList;
import java.util.List;

import com.web.study.security.PrincipalUserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data


/*1) 회원가입
 * 
 * 1. 회원가입
 * 2. 해당 정보로 회원가입 요청
 * 3. 회원가입 정보를 db에 보냄 , 패스워드는 암호화하여 보냄 , 암호화는 toEntity에서 이루어짐 
 * 
 * 2) 로그인
 * 
 * 1. 로그인할 정보를 입력(username, password)
 * 2.해당 정보로 로그인 요청
 * 3. AuthenticationManager에게 usrename, password를 전달
 * 4. AuthenticationManager가 인증을 시작 
 * 5. UserDetailsService의 loadUserByUsername(String username)이 호출
 * UserDetails를 리턴 받아서 Authentication객체를 생성하기 위함 , 이때 해당 username으로 db에서 조회된  UserEntity가 없으면 등록되지 않은 회원임 
 * 6. Authentication객체를 Jwt로 변환하는 작업을 해야함 , Authentication객체가 생성되었다는 것 자체가 로그인성공을 의미함 
 * 7. 변환된 jwt를 클라이언트에게 응답
 * 8. 클라이언트는 jwt토큰을 로컬스토리지나 쿠키에 저장
 * 
 * 3)요청시 토큰 인증 
 * 
 *1. 요청Header에 Bearer방식으로 Jwt 토큰을 전달 
 *2. spring security에서 인증이 필요한 요청들은 jwtAuthenticationFilter을 통해 인증절차를 진행한다
 *이때 인증의 최종 목표는 SecurityContextHolder객체의 Context에 Authentication을 등록하는 것이다
 *Authentication 객체가 등록이 되어있으면 인증이 된 것이다, 로그인이랑은 다름 토큰 검증이 되지 않은 것 
 *
 * 3. JwtAuthenticationFilter에서 요청 Header에 들어있는 Authorization의 jwt토큰을 추출
 * 4. jwt 토큰을 검증 , 이때 검증에 실패하여 EXception이 생성되면 AuthenticationEntryPoint가 실행되면서 401 응답을 함
 * 5. jwt토큰 검증이 완료되면 jwt토큰에서 claims를 추출한다
 * 6. claims에서 username과 Authorities를 추출하여  Authentication객체를 생성한다
 * 7. 생성된  Authentication객체를 Securitycontext에 등록한다
 * 8. 등록이 완료되면 다음 doChain에 호출된다, 이때 doChain은 controller가 될 수도 있고, filter가 될 수도 있다 
 *
 */
public class User {
	
	private int user_id;
	private String username;
	private String password;
	private String name;
	private String email;
	
	private List<Authority> authorities;
	
	//role들의 이름이 리스트안에 들어가야함 
	public PrincipalUserDetails toPrincipal() {
		List<String> roles = new ArrayList<>();
		
		authorities.forEach(authority ->{
			roles.add(authority.getRole().getRole_name());
		});
		return PrincipalUserDetails.builder()
				.userId(user_id)
				.username(username)
				.password(password)
				.roles(roles)
				.build();
	}

}
