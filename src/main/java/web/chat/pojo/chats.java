package web.chat.pojo;

import java.sql.Timestamp;

import core.pojo.Core;

public class Chats extends Core {
	private static final long serialVersionUID = 1L;

	private Integer chatId; // chat_id
	private Integer courseId; // course_id
	private Integer userId; // user_id
	private Integer coachId; // coach_id
	private String content; // content
	private Timestamp createdAt; // created_at

	public Chats() {

	}

	public Integer getChatId() {
		return chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCoachId() {
		return coachId;
	}

	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
//	        return "Chats{" +
//	                "chatId=" + chatId +
//	                ", courseId=" + courseId +
//	                ", userId=" + userId +
//	                ", coachId=" + coachId +
//	                ", content='" + content + '\'' +
//	                ", createdAt=" + createdAt +
//	                '}';
		return "chatId=" + chatId + ", courseId=" + courseId + ", userId=" + userId + ", coachId=" + coachId
				+ ", content='" + content + '\'' + ", createdAt=" + createdAt + '}';
	}
}
