package com.web.study.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Authority {
	
	//여기까지는 단순히 user_mst를 들고온것밖에 안됨, 우리는 권한을 주는 작업이 필요
	private int authority_id;
	private int  user_id;
	private int  role_id;
	
	private Role role;


}
