package web.course.pojo;

import java.util.Date;

import core.pojo.Core;

public class Course extends Core {
	private static final long serialVersionUID = 1L;
	private Integer courseId; // 課程ID
	private Integer coachId; // 教練ID
	private Integer roomId; // 教室ID
	private String title; // 課程名稱
	private String category; // 課程類別
	private Integer sessionQuota; // 課程堂數
	private String description; // 課程介紹
	private Integer capacityMax; // 最大上課人數
	private Date dateStart; // 起始日期
	private Date dateEnd; // 結束日期
	private Integer coursePrice; // 課程訂價
	private String approvalStatus; // 課程審核狀態
	private String imgUrl; // 課程圖片

	public Course() {
	}

	public Course(Integer courseId, Integer coachId, Integer roomId, String title, String category,
			Integer sessionQuota, String description, Integer capacityMax, Date dateStart, Date dateEnd,
			Integer coursePrice, String approvalStatus, String imgUrl) {
		this.courseId = courseId;
		this.coachId = coachId;
		this.roomId = roomId;
		this.title = title;
		this.category = category;
		this.sessionQuota = sessionQuota;
		this.description = description;
		this.capacityMax = capacityMax;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.coursePrice = coursePrice;
		this.approvalStatus = approvalStatus;
		this.imgUrl = imgUrl;
	}

	// Getter / Setter
	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getCoachId() {
		return coachId;
	}

	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getSessionQuota() {
		return sessionQuota;
	}

	public void setSessionQuota(Integer sessionQuota) {
		this.sessionQuota = sessionQuota;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCapacityMax() {
		return capacityMax;
	}

	public void setCapacityMax(Integer capacityMax) {
		this.capacityMax = capacityMax;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Integer getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(Integer coursePrice) {
		this.coursePrice = coursePrice;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
