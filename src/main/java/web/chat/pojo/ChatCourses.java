//package web.chat.pojo;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import core.pojo.Core;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//
//@Entity
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "COURSES")
//public class ChatCourses extends Core {
//	private static final long serialVersionUID = -5775833812254098286L;
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "COURSE_ID")
//	private Integer courseId;
//	@Column(name = "COACH_ID")
//	private Integer coachId; 
//	@Column(name = "ROOM_ID")
//	private Integer roomId; 
//	@Column(name = "TITLE")
//	private String title; 
//	@Column(name = "CATEGORY")
//	private String category; 
//	@Column(name = "SESSION_QUOTA")
//	private Integer sessionQuota; 
//	@Column(name = "DESCRIPTION")
//	private String description; 
//	@Column(name = "CAPACITY_MAX")
//	private Integer capacityMax; 
//	@Column(name = "DATE_START")
//	private Date dateStart; 
//	@Column(name = "DATE_END")
//	private Date dateEnd; 
//	@Column(name = "COURSE_PRICE")
//	private Integer coursePrice; 
//	@Column(name = "APPROVAL_STATUS")
//	private String approvalStatus; 
//	@Column(name = "IMG_URL")
//	private String imgUrl; 
//}
