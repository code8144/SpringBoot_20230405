package com.web.study.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.study.domain.entity.Instructor;

@Mapper
public interface InstructorRepository {
	public int saveInstructor(Instructor instructor);
	public List<Instructor> getInstructorAll();
	//하나만이라 리스트가 아님 
	public Instructor findInstructorById(int id);
}
