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

		Date dateStart = new Date(coursePromo.getDateStart().getTime());
		Date dateEnd = new Date(coursePromo.getDateEnd().getTime());
		Date dateNow = new Date();

		if (dateStart.before(dateNow)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			coursePromo.setMessage("開始日期請選擇" + sdf.format(dateNow) + "之後");
			coursePromo.setSuccessful(false);
			return coursePromo;
		}

		long dateDiff = (dateEnd.getTime() - dateStart.getTime()) / (1000 * 60 * 60 * 24);

		if (dateDiff < 30) {
			coursePromo.setMessage("結束日期需大於開始日期30天");
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
		coursePromo.setImgUrl(filename);

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
