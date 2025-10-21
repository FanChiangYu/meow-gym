package core.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

public class FileUtil {
	public static final String IMG_ROOT_PATH = System.getProperty("catalina.home") + "/img/";

	/**
	 * 從 圖片目錄 讀入 檔案
	 * @param filename 欲讀入的檔案之檔名
	 * @return 讀入的檔案
	 */
	public static byte[] readFromImgPath(String filename) {
		try {
			Path path = Paths.get(IMG_ROOT_PATH, filename);
			return Files.readAllBytes(path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 將 檔案 寫出 至 圖片目錄
	 * @param part 欲寫出的檔案
	 * @return 是否成功
	 */
	public static boolean writeToImgPath(Part part) {
		try {
			String filename = getFileName(part);
			Path path = Paths.get(IMG_ROOT_PATH, filename);
			byte[] bytes = part.getInputStream().readAllBytes();
			Files.write(path, bytes);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getFileName(Part part) {
		String fileDesc = part.getHeader("Content-Disposition");
		int index = fileDesc.indexOf("filename=\"");
		String fileName = fileDesc.substring(index + 10, fileDesc.length() - 1);
		return FilenameUtils.getName(fileName);
	}
}
