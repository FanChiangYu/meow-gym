package web.course.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassResponse {
	Course course;
	List<ClassSessions> classSessions;
	String coachName;
}
