package web.course.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import web.course.pojo.ClassResponse;
import web.course.pojo.ClassSessions;
import web.course.pojo.Course;
import web.course.pojo.CourseRecurringRules;
import web.course.pojo.SessionUsers;
import web.course.service.CourseService;
import web.order.pojo.Orders;
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
	public JsonObject apply(List<CourseRecurringRules> rules, Course course) {
		JsonObject result = new JsonObject();
		List<Date> dates = new ArrayList<>();
		
		for (CourseRecurringRules rule : rules) {
			rule.setCourseId(course.getCourseId());
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

			int count = dao.insert(rule);
			if(count != 1) {
				result.addProperty("successful", false);
				result.addProperty("message", "送出失敗");
				return result;
			}
			
			// insert class_session表格處理
			dates = findDateOfWeekday(course, rule);
			if(dates == null) {
				result.addProperty("successful", false);
				result.addProperty("message", "送出失敗");
				return result;
			}
			
			for (Date date : dates) {
				ClassSessions classSessions = new ClassSessions();
				classSessions.setCourseId(course.getCourseId());
				classSessions.setSessionDate(date);
				classSessions.setTimeSlot(rule.getTimeSlot());
				int cCount = dao.insert(classSessions);
				if(cCount != 1) {
					result.addProperty("successful", false);
					result.addProperty("message", "送出失敗");
					return result;
				}
			}
			
		}
		result.addProperty("successful", true);
		result.addProperty("message", "送出成功");
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
		Course findCourse = dao.selectById(cousre.getCourseId());
		if (findCourse == null) {
			cousre.setSuccessful(false);
			return cousre;
		}
		
		findCourse.setSuccessful(true);
		return findCourse;
	}

	@Override
	public String modify(Course cousre) {
		System.out.println("======================" + cousre.getApprovalStatus());
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

	@Override
	public List<Date> findDateOfWeekday(Course course, CourseRecurringRules rule) {
		List<Date> result = new ArrayList<>();
		int diff;
		
		Calendar calendar = Calendar.getInstance();  // 實例化
		calendar.setTime(course.getDateStart()); // 設定起始日期
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);  // 起始日期在星期幾
		
		switch (rule.getWeekday()) {
			case 1:
				diff = Calendar.MONDAY - dayOfWeek;
				break;
			case 2:
				diff = Calendar.TUESDAY - dayOfWeek;
				break;
			case 3:
				diff = Calendar.WEDNESDAY - dayOfWeek;
				break;
			case 4:
				diff = Calendar.THURSDAY - dayOfWeek;
				break;
			case 5:
				diff = Calendar.FRIDAY - dayOfWeek;
				break;
			case 6:
				diff = Calendar.SATURDAY - dayOfWeek;
				break;
			case 7:
				diff = Calendar.SUNDAY - dayOfWeek;
				break;
			default:
				return null;
		}
		
		if (diff < 0) {
			diff += 7; // 若起始日期已超過這週，跳到下週
		}
		calendar.add(Calendar.DAY_OF_MONTH, diff);
		
		// 逐週增加直到超過結束日
		while (!calendar.getTime().after(course.getDateEnd())) {
			result.add(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 7); // 加一週
		}
		
		return result;
	}

	@Override
	public List<ClassResponse> findClass(Integer userId) {
		List<Orders> orders = new ArrayList<>();
		List<ClassResponse> classResponses = new ArrayList<>();
		// 尋找訂單是否有已購買課程
		orders = dao.selectOrderByUserId(userId);
		for (Orders order : orders) {
			List<Integer> courseIds = dao.selectCourseIdByOrderId(order.getOrderId()); // 從Orderitems找CourseId
			for (Integer couseId : courseIds) {
				ClassResponse classResponse = new ClassResponse(); 
				Course course = dao.selectById(couseId); // 找課程
				Integer quotaCnt = Math.toIntExact(findQuotaUsed(course, userId)); // 計算已使用堂數
				course.setQuotaUsed(quotaCnt);
				List<ClassSessions> classSessionsList = dao.selectClassSessionBycourseID(couseId); // 找班次
				// 判斷每個ClassSessions的預約狀態
				for (ClassSessions classSessions : classSessionsList) {
					SessionUsers sessionUsers = dao.selectBySessionIdUserID(classSessions.getSessionId(), userId);
					Long countUser = dao.selectCntBySessionId(classSessions.getSessionId()); // 找班次人數
					classSessions.setUserCnt(Math.toIntExact(countUser));
					if (sessionUsers == null) {
						if (countUser >= course.getCapacityMax() && countUser != null) {
							classSessions.setBookStatus("無法預約");
						} else { 
							classSessions.setBookStatus("未預約");
						}
					} else {
						classSessions.setBookStatus("已預約");
					}
				}
				// 全部放到ClassResponse物件
				classResponse.setCourse(course);
				classResponse.setCoachName(findName(course)); // 找教練名
				classResponse.setClassSessions(classSessionsList);
				classResponses.add(classResponse); // 放到List
			}
		}
		
		return classResponses;
	}
	
	@Override
	public Long findQuotaUsed(Course course, Integer userId) {
		Long selectCntTotal = 0L;
		List<ClassSessions> csList = dao.selectClassSessionBycourseID(course.getCourseId());
		for (ClassSessions cs : csList) {
			Long selectCnt = dao.selectCntFromSessionUserById(cs.getSessionId(), userId);
			if (selectCnt != null && selectCnt > 0) {
				selectCntTotal += selectCnt;
			}
		}
		return selectCntTotal;
	}

	@Override
	public Boolean reserveUpdate(ClassSessions classSessions, Integer useId) {
		SessionUsers su = new SessionUsers();
		su.setSessionId(classSessions.getSessionId());
		su.setUserId(useId);
		if ("未預約".equals(classSessions.getBookStatus())) {
			int count = dao.insert(su);
			return count == 1;		
		} else if ("已預約".equals(classSessions.getBookStatus())) {
			int count = dao.deleteById(su);
			return count == 1;
		}
		return false;
	}
	

}
