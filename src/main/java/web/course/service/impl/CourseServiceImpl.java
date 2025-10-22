package web.course.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import com.google.gson.JsonObject;

import web.course.dao.CourseDao;
import web.course.dao.impl.CourseDaoImpl;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.service.CourseService;
import web.member.pojo.Member;

public class CourseServiceImpl implements CourseService {
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
		
		if(dateDiff < 30) {
			course.setMessage("結束日期需大於開始日期30天");
			course.setSuccessful(false);
			return course;
		}
		
		course.setCoachId(1); // 暫定
		course.setApprovalStatus("待審核");
		int count = dao.insert(course);
		if(count == 1) {
			course.setMessage("送出成功");
			course.setSuccessful(true);
		} else {
			course.setMessage("送出失敗");
			course.setSuccessful(false);
		}
		
		return course;
	}

	@Override
	public JsonObject apply(List<CourseRecurringRules> rules, Integer id) {
		JsonObject result = new JsonObject();
		for (CourseRecurringRules rule : rules) {
			rule.setCourseId(id);
			if(rule.getWeekday() == null) {
				result.addProperty("successful", false);
				result.addProperty("message", "未選擇星期");
				return result;
			}
			
			if(rule.getTimeSlot() == null) {
				result.addProperty("successful", false);
				result.addProperty("message", "未選擇時段");
				return result;
			}
			System.out.println(id);
			int count = dao.insert(rule);
			if(count != 1) {
				result.addProperty("successful", false);
				result.addProperty("message", "送出失敗");
				return result;
			}
		}
		result.addProperty("successful", true);
		result.addProperty("Message", "送出成功");
		return result;
	}

	@Override
	public JsonObject removeById(Integer id) {
		return null;
	}

	@Override
	public List<Course> findAll() {
		return dao.selectAll();
	}

	@Override
	public Course find(Course cousre) {
		System.out.println(cousre.getCourseId());
		if(cousre.getCourseId() == null) {
			cousre.setSuccessful(false);
			return cousre;
		}
		cousre = dao.selectById(cousre.getCourseId());
		cousre.setSuccessful(true);
		return cousre;
	}

	@Override
	public String modify(Course cousre) {
		int count = dao.update(cousre);
		return count > 0 ? "更新成功" : "更新失敗";
	}

	

}
