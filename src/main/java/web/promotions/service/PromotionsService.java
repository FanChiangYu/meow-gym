package web.promotions.service;

import java.io.IOException;
import java.util.List;

import core.service.CoreService;
import web.course.pojo.Course;
import web.promotions.pojo.CoursePromo;

public interface PromotionsService extends CoreService {

	List<CoursePromo> selectAll();

	CoursePromo apply(CoursePromo coursePromo) throws IOException;

	List<Course> findCourseAndPromotionAll();

}
