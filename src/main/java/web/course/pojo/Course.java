package web.course.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import core.pojo.Core;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.promotions.pojo.CoursePromo;

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
	@JsonFormat(pattern = "yyyy/MM/dd") 
	private Date dateStart; 
	@Column(name = "DATE_END")
	@JsonFormat(pattern = "yyyy/MM/dd") 
	private Date dateEnd; 
	@Column(name = "COURSE_PRICE")
	private Integer coursePrice; 
	@Column(name = "APPROVAL_STATUS")
	private String approvalStatus; 
	@Column(name = "IMG_URL")
	private String imgUrl; 
	
	@Transient
	private String coachName; // 回應前端用
	@Transient
	private Integer quotaUsed; // 回應前端用, 統計使用者已使用堂數
	@Transient
	private String payStatus; // 回應前端用, 判斷此課程的付款狀態
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false)
	private List<CoursePromo> coursePromos;
}
