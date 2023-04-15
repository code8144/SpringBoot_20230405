package com.web.study.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter


/*
 * 데이터 베이스들의 정보를 다시 한번 PrincipalUserDetails 으로 변환 
PrincipalUserDetails이것을 get하면 interface라서 그냥 못씀, 다운캐스팅 해야함 -> 다운캐스팅하면 이안에 들어있는 정보들 사용가능
 jwt안에는 builder가 들어있다, subject안에는 getname이 들어가 있음 
 *  get name = PrincipalUser의 username
 */
public class PrincipalUserDetails  implements UserDetails{
	

	
	
	private static final long serialVersionUID = 1L;
	
	//필수적으로 들어가야하는 정보들 
	private int userId;
	private String username;
	private String password;
	private List<String> roles;
	
/*
 * GrantedAuthorit 는 인터페이스 형식이고 이것에는 getthority가 들어있음 이것은 string 형식
 * 이것이 return 될때 role로 return 
 * 문자열을 들고와야하기 때문에 권한을 문자열로 주려고 반복을 계속 돌림 
 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role ->{
			authorities.add(new SimpleGrantedAuthority(role));
		});
		
		return authorities;
	}

	// getpassword, getusername은 검증하는 작업, 패쓰워드와 암호화된 패스워드를 비교
	@Override
	public String getPassword() {
		
		return password;
	}

	
	@Override
	public String getUsername() {
	
		return username;
	}

	//사용기간 만료, 이중에서 하나라도  false면 로그인이  안됨 
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	//계정을 잠궈버림 
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	//비밀번호가 5회 틀렸을때
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정 비활성 상태(이메일 인증을 완료해야하거나, 전화번호 인증을 하지 않았을 때)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	

}
