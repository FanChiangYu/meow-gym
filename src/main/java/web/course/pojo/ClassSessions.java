package web.course.pojo;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLASS_SESSIONS")
public class ClassSessions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SESSION_ID")
	private Integer sessionId;
	@Column(name = "COURSE_ID")
	private Integer courseId;
	@Column(name = "SESSION_DATE")
	private Date sessionDate; 
	@Column(name = "TIME_SLOT")
	private Integer timeSlot;
	@Column(name = "CHECKIN_AT")
	private Timestamp checkinAt;
	@Column(name = "CHECKIN_OUT")
	private Timestamp checkinOut;
	
	@Transient
	private String bookStatus; // 用來回應前端，判斷使用者的預約狀態
							   // "已預約"、"可預約"、"無法預約"(預設）
	@Transient
	private Integer userCnt; // 此班次總預約人數，回應前端用
}
