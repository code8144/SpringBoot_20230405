package com.web.study.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.web.study.domain.entity.Authority;
import com.web.study.domain.entity.User;
import com.web.study.dto.request.auth.LoginReqDto;
import com.web.study.dto.request.auth.RegisteUserReqDto;
import com.web.study.dto.response.auth.JwtTokenRespDto;
import com.web.study.exception.CustomException;
import com.web.study.repository.UserRepository;
import com.web.study.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void registeUser(RegisteUserReqDto registeUserReqDto) {
		User userEntity = registeUserReqDto.toEntity();

		userRepository.saveUser(userEntity); // insert user_mst

		List<Authority> authorities = new ArrayList<>();

		authorities.add(Authority.builder().user_id(userEntity.getUser_id()).role_id(1).build());
		userRepository.addAuthorities(authorities);
	}

	// 중복검사를 하여 중복되는 아이디인지 확인 
	@Override
	public void duplicatedUsername(RegisteUserReqDto registeUserReqDto) {
		User userEntity = userRepository.findUserByUsername(registeUserReqDto.getUsername());

		if (userEntity != null) {
			Map<String, String> errorMap = new HashMap<>();
			errorMap.put("username", "이미 사용중인 사용자 이름입니다");

			throw new CustomException("중복 검사 오류", errorMap);
		}

	}

	@Override
	public JwtTokenRespDto login(LoginReqDto loginReqDto) {
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(loginReqDto.getUsername(), loginReqDto.getPassword());
		
		
		/*
		 * authenticate 이것은 authentication manager에서 태어남 , 인증을 할수 있는 것이 생성 
		 authentication manager이것은 userdetailservice를 가지고 있음 
		 * authentication는  실제 security안에서 로그인 정보를 관리
		 * 메니저가 알아볼수 있게끔 만들어줌 동시에 loadbyusername이 일어남 -> userentity정보를 다 들고옴 
		 */
		//UserDetailService의 loadUserByUsername() 호출이 된다
		// authentication 이게 실행되면 정상 로그인 
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		return jwtTokenProvider.createToken(authentication);
	}

}
