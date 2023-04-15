package com.web.study.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.study.domain.entity.User;
import com.web.study.exception.CustomException;
import com.web.study.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	//여기로 username이 넘어옴 
	// 실질적으로 로그인 정보를 받는공간 ->  UserDetails, 정보를 담는 공간 authentication
	//업캐스팅이 되어 return 가능
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			User userEntity = userRepository.findUserByUsername(username);
			
			if(userEntity == null) {
				//예외처리
				throw new CustomException("사용자 정보를 다시 확인해주세요");
			}
		
			//빌더가 이 정보(user가 로그인할때 들어온 정보, loadbyuser에서 넘어온 로그인 정보)를 받고 일치하는지 검사, 락이 걸려져있는게 있는지 검사
		return userEntity.toPrincipal();
	}
	
	
	
	

}
