package web.course.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import core.pojo.Core;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COURSES")
public class Course extends Core {
	private static final long serialVersionUID = -5775833812254098286L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COURSE_ID")
	private Integer courseId;
	@Column(name = "COACH_ID")
	private Integer coachId; 
	@Column(name = "ROOM_ID")
	private Integer roomId; 
	@Column(name = "TITLE")
	private String title; 
	@Column(name = "CATEGORY")
	private String category; 
	@Column(name = "SESSION_QUOTA")
	private Integer sessionQuota; 
	@Column(name = "DESCRIPTION")
	private String description; 
	@Column(name = "CAPACITY_MAX")
	private Integer capacityMax; 
	@Column(name = "DATE_START")
	private Date dateStart; 
	@Column(name = "DATE_END")
	private Date dateEnd; 
	@Column(name = "COURSE_PRICE")
	private Integer coursePrice; 
	@Column(name = "APPROVAL_STATUS")
	private String approvalStatus; 
	@Column(name = "IMG_URL")
	private String imgUrl; 
}

//public class Course extends Core {
//	private static final long serialVersionUID = 1L;
//	private Integer courseId; // 課程ID
//	private Integer coachId; // 教練ID
//	private Integer roomId; // 教室ID
//	private String title; // 課程名稱
//	private String category; // 課程類別
//	private Integer sessionQuota; // 課程堂數
//	private String description; // 課程介紹
//	private Integer capacityMax; // 最大上課人數
//	private Date dateStart; // 起始日期
//	private Date dateEnd; // 結束日期
//	private Integer coursePrice; // 課程訂價
//	private String approvalStatus; // 課程審核狀態
//	private String imgUrl; // 課程圖片
//
//	public Course() {
//	}
//
//	public Course(Integer courseId, Integer coachId, Integer roomId, String title, String category,
//			Integer sessionQuota, String description, Integer capacityMax, Date dateStart, Date dateEnd,
//			Integer coursePrice, String approvalStatus, String imgUrl) {
//		this.courseId = courseId;
//		this.coachId = coachId;
//		this.roomId = roomId;
//		this.title = title;
//		this.category = category;
//		this.sessionQuota = sessionQuota;
//		this.description = description;
//		this.capacityMax = capacityMax;
//		this.dateStart = dateStart;
//		this.dateEnd = dateEnd;
//		this.coursePrice = coursePrice;
//		this.approvalStatus = approvalStatus;
//		this.imgUrl = imgUrl;
//	}
//
//	// Getter / Setter
//	public Integer getCourseId() {
//		return courseId;
//	}
//
//	public void setCourseId(Integer courseId) {
//		this.courseId = courseId;
//	}
//
//	public Integer getCoachId() {
//		return coachId;
//	}
//
//	public void setCoachId(Integer coachId) {
//		this.coachId = coachId;
//	}
//
//	public Integer getRoomId() {
//		return roomId;
//	}
//
//	public void setRoomId(Integer roomId) {
//		this.roomId = roomId;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public String getCategory() {
//		return category;
//	}
//
//	public void setCategory(String category) {
//		this.category = category;
//	}
//
//	public Integer getSessionQuota() {
//		return sessionQuota;
//	}
//
//	public void setSessionQuota(Integer sessionQuota) {
//		this.sessionQuota = sessionQuota;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public Integer getCapacityMax() {
//		return capacityMax;
//	}
//
//	public void setCapacityMax(Integer capacityMax) {
//		this.capacityMax = capacityMax;
//	}
//
//	public Date getDateStart() {
//		return dateStart;
//	}
//
//	public void setDateStart(Date dateStart) {
//		this.dateStart = dateStart;
//	}
//
//	public Date getDateEnd() {
//		return dateEnd;
//	}
//
//	public void setDateEnd(Date dateEnd) {
//		this.dateEnd = dateEnd;
//	}
//
//	public Integer getCoursePrice() {
//		return coursePrice;
//	}
//
//	public void setCoursePrice(Integer coursePrice) {
//		this.coursePrice = coursePrice;
//	}
//
//	public String getApprovalStatus() {
//		return approvalStatus;
//	}
//
//	public void setApprovalStatus(String approvalStatus) {
//		this.approvalStatus = approvalStatus;
//	}
//
//	public String getImgUrl() {
//		return imgUrl;
//	}
//
//	public void setImgUrl(String imgUrl) {
//		this.imgUrl = imgUrl;
//	}
//}
