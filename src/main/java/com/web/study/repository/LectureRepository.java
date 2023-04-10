package com.web.study.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.study.domain.entity.Lecture;

@Mapper
public interface LectureRepository {
	public int regist(Lecture lecture);
	public List<Lecture> getLectureAll();
	public Lecture findLectureById(int id);
}
