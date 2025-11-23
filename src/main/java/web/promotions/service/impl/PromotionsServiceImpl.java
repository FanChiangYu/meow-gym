package web.promotions.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.util.FileUtil;
import web.course.dao.CourseDao;
import web.course.pojo.Course;
import web.promotions.dao.PromotionsDao;
import web.promotions.pojo.CoursePromo;
import web.promotions.service.PromotionsService;

@Transactional
@Service
public class PromotionsServiceImpl implements PromotionsService {
	@Autowired
	private PromotionsDao dao;
	@Autowired
	private CourseDao courseDao;

	@Override
	public List<CoursePromo> selectAll() {
		return dao.selectPromo();
	}

	@Override
	public CoursePromo apply(CoursePromo coursePromo) throws IOException {
		if (coursePromo.getPromoPrice() == null) {
			coursePromo.setMessage("未填寫課程訂價");
			coursePromo.setSuccessful(false);
			return coursePromo;
		}

		if (coursePromo.getDateStart() == null) {
			coursePromo.setMessage("未選擇開始日期");
			coursePromo.setSuccessful(false);
			return coursePromo;
		}

		if (coursePromo.getDateEnd() == null) {
			coursePromo.setMessage("未選擇結束日期");
			coursePromo.setSuccessful(false);
			return coursePromo;
		}

		Date promoDateStart = coursePromo.getDateStart();
		Date promoDateEnd = coursePromo.getDateEnd();

		Course course = courseDao.selectById(coursePromo.getCourseId());
		Date courseDateStart = course.getDateStart();
		Date courseDateEnd = course.getDateEnd();
		
		if (!promoDateStart.after(courseDateStart)) {
			coursePromo.setMessage("促銷活動日期必須在課程開始日期之後");
			coursePromo.setSuccessful(false);
			return coursePromo;
		}
		
		if (!promoDateEnd.before(courseDateEnd)) {
			coursePromo.setMessage("促銷活動日期必須在課程結束日期之前");
			coursePromo.setSuccessful(false);
			return coursePromo;
		}

	

		final String imgBase64 = coursePromo.getImgBase64();
		if (imgBase64 == null || imgBase64.isEmpty()) {
			coursePromo.setMessage("未選擇圖片");
			coursePromo.setSuccessful(false);
			return coursePromo;
		}

		String filename = coursePromo.getFilename();
		if (filename == null || filename.isEmpty()) {
			coursePromo.setMessage("缺少圖片檔名");
			coursePromo.setSuccessful(false);
			return coursePromo;
		}

		filename = addTimestampToFileName(filename);
		String fullPath = FileUtil.IMG_ROOT_PATH + filename;
		byte[] img = Base64.getDecoder().decode(imgBase64);
		Path path = Paths.get(fullPath);
		Files.write(path, img);
		coursePromo.setImgUrl("/meow-gym/course/img/" + filename);
		
		
		
		

		int count = dao.insert(coursePromo);
		if (count == 1) {
			coursePromo.setMessage("送出成功");
			coursePromo.setSuccessful(true);
		} else {
			coursePromo.setMessage("送出失敗");
			coursePromo.setSuccessful(false);
		}

		return coursePromo;
	}

	public String addTimestampToFileName(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		String extension = fileName.substring(dotIndex);
		String baseName = fileName.substring(0, dotIndex);
		String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
		return baseName + "_" + timestamp + extension;
	}

	@Override
	public List<Course> findCourseAndPromotionAll() {
		return courseDao.selectAll();
	}

	@Override
	public int delete(CoursePromo coursePromo) {
		return dao.deleteById(coursePromo);
	}
	

}
