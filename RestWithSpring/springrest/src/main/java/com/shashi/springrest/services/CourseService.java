package com.shashi.springrest.services;

import java.util.List;
import com.shashi.springrest.entities.Course;

public interface CourseService {
	public List<Course> getCourses();
	public Course getCourse(long courseId);
	public Course addCourse(Course course);

}
