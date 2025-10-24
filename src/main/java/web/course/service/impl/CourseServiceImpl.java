package web.course.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import core.util.FileUtil;

import javax.naming.NamingException;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import web.coach.pojo.CoachProfiles;
import web.course.dao.CourseDao;
import web.course.dao.impl.CourseDaoImpl;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.service.CourseService;

import web.user.pojo.User;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
	@Autowired
	private CourseDao dao;

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
		List<Course> courses = dao.selectAll();
		for (Course course : courses) {
			String userName = findName(course);
			course.setCoachName(userName);
		}
		return courses;
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
	
	@Override
	public String addTimestampToFileName(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		String extension = fileName.substring(dotIndex);
		String baseName = fileName.substring(0, dotIndex);
		String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
                			.format(new java.util.Date());

		return baseName + "_" + timestamp + extension;
	}

	@Override
	public boolean writeToImgPath(Part part) {
		try {
			String filename = FileUtil.getFileName(part);
			filename = addTimestampToFileName(filename);
			Path path = Paths.get(FileUtil.IMG_ROOT_PATH, filename);
			byte[] bytes = part.getInputStream().readAllBytes();
			Files.write(path, bytes);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String findName(Course cousre) {
		cousre = dao.selectById(cousre.getCourseId());
		CoachProfiles coachProfiles = dao.selectByCoachId(cousre.getCoachId());
		User user = dao.selectByUserId(coachProfiles.getUserId());
		return user.getName();
	}

	@Override
	public List<CourseRecurringRules> findRules(Course cousre) {
		return dao.selectByCourseId(cousre.getCourseId());
	}

	

}
