package web.chat.pojo;

import java.sql.Timestamp;
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
@Table(name = "CHATS")
public class Chats extends Core {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chat_id")
	private Integer chatId;

	@Column(name = "course_id")
	private Integer courseId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "coach_id")
	private Integer coachId;

	@Column(name = "content")
	private String content;

	// 直接跳過，否則ChatEndPoint.java就要填，但通常都是直接自動填寫現在時間，所以直接叫hibernate填好了! 講義12-2
	// 讓 DB 自動填 DEFAULT CURRENT_TIMESTAMP
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

	// add this to use the system print function in ChatDaoImpl.java, or it will
	// print null.
	@Override
	public String toString() {
		return "Chats{" + "chatId=" + chatId + ", courseId=" + courseId + ", userId=" + userId + ", coachId=" + coachId
				+ ", content='" + content + '\'' + ", createdAt=" + createdAt + '}';
	}

//	public Chats() {
//
//	}
//
//	public Integer getChatId() {
//		return chatId;
//	}
//
//	public void setChatId(Integer chatId) {
//		this.chatId = chatId;
//	}
//
//	public Integer getCourseId() {
//		return courseId;
//	}
//
//	public void setCourseId(Integer courseId) {
//		this.courseId = courseId;
//	}
//
//	public Integer getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Integer userId) {
//		this.userId = userId;
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
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//
//	public Timestamp getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(Timestamp createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	@Override
//	public String toString() {
////	        return "Chats{" +
////	                "chatId=" + chatId +
////	                ", courseId=" + courseId +
////	                ", userId=" + userId +
////	                ", coachId=" + coachId +
////	                ", content='" + content + '\'' +
////	                ", createdAt=" + createdAt +
////	                '}';
//		return "chatId=" + chatId + ", courseId=" + courseId + ", userId=" + userId + ", coachId=" + coachId
//				+ ", content='" + content + '\'' + ", createdAt=" + createdAt + '}';
//	}
}
