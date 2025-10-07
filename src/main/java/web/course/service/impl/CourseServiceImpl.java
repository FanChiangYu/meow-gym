package web.course.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;

import web.course.dao.CourseDao;
import web.course.dao.impl.CourseDaoImpl;
import web.course.pojo.Course;
import web.course.service.CourseService;

public class CourseServiceImpl  implements CourseService {
	private CourseDao dao;
	
	public CourseServiceImpl() throws NamingException{
		dao = new CourseDaoImpl();
	}

	@Override
	public Course apply(Course course) {
		
		if(course.getTitle() == null) {
			course.setMessage("課程名稱未輸入");
			course.setSuccessful(false);
			return course;
		}
		
		if(course.getCategory() == null) {
			course.setMessage("未選擇類別");
			course.setSuccessful(false);
			return course;
		}
		
		if(course.getRoomId() == null) {
			course.setMessage("未選擇教室");
			course.setSuccessful(false);
			return course;
		}
		
		if(course.getSessionQuota() == null) {
			course.setMessage("未選擇課程堂數");
			course.setSuccessful(false);
			return course;
		}
		
		if(course.getCapacityMax() == null) {
			course.setMessage("未選擇最大上課人數");
			course.setSuccessful(false);
			return course;
		}
		
		if(course.getDateStart() == null) {
			course.setMessage("未選擇開始日期");
			course.setSuccessful(false);
			return course;
		}
		
		if(course.getDateEnd() == null) {
			course.setMessage("未選擇結束日期");
			course.setSuccessful(false);
			return course;
		}
		
		if(course.getCoursePrice() == null) {
			course.setMessage("未填寫課程訂價");
			course.setSuccessful(false);
			return course;
		}
		
		if(course.getDescription() == null) {
			course.setMessage("課程介紹未填寫");
			course.setSuccessful(false);
			return course;
		}
		
		Date dateStart = new Date(course.getDateStart().getTime());
		Date dateEnd = new Date(course.getDateEnd().getTime());
		Date dateNow = new Date();
		
		if(dateStart.before(dateNow)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			course.setMessage("開始日期請選擇" + sdf.format(dateNow) + "之後");
			course.setSuccessful(false);
			return course;
		}
		
		long dateDiff = (dateEnd.getTime() - dateStart.getTime()) / (1000 * 60 * 60 * 24);
		
		System.out.println(dateStart);
		System.out.println(dateEnd);
		System.out.println(dateDiff);
		if(dateDiff < 30) {
			course.setMessage("結束日期需大於開始日期30天");
			course.setSuccessful(false);
			return course;
		}
		
		course.setCoachId(1); // 暫定
		course.setApprovalStatus("PENDING");
		beginTx();
		int count = dao.insert(course);
		if(count == 1) {
			course.setMessage("送出成功");
			course.setSuccessful(true);
			commit();
		} else {
			course.setMessage("送出失敗");
			course.setSuccessful(false);
			rollback();
		}
		
		return course;
	}

}
