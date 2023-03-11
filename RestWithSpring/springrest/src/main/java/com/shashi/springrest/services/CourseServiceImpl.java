package com.shashi.springrest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shashi.springrest.entities.Course;

@Service
public class CourseServiceImpl implements CourseService{
	List<Course> list;
	
	

	public CourseServiceImpl() {
		list= new ArrayList<Course>();
		list.add(new Course(1,"Java","Come and get me"));
		list.add(new Course(2,"Python","Overrated"));

	}



	@Override
	public List<Course> getCourses() {
		return list;
	}
	
	@Override
	public Course getCourse(long courseId) {
		Course course=null;
		for(Course c:list) {
			if(c.getId()==courseId) {
				course=c;
				break;
			}
		}
		return course;
	}



	@Override
	public Course addCourse(Course course) {
		list.add(course);
		return course;
	}
	
}
