package web.course.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseResponse {
	Course course;
	String userName;
	List<CourseRecurringRules> rules;
}
