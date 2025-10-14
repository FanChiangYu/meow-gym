package web.course.pojo;

import java.util.List;

public class NewCourseRequest {
	private Course course;
	private List<CourseRecurringRules> rules;
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public List<CourseRecurringRules> getRules() {
		return rules;
	}
	public void setRules(List<CourseRecurringRules> rules) {
		this.rules = rules;
	}
	
	
}
