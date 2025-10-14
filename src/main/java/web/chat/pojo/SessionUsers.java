package web.chat.pojo;

import java.util.Objects;

import core.pojo.Core;

public class SessionUsers extends Core {

	private static final long serialVersionUID = 1L;

	/** 課程ID (course_id) */
	private Integer courseId;

	/** 班次ID (session_id) —— 與 userId 組成複合主鍵 */
	private Integer sessionId;

	/** 使用者ID (user_id) —— 與 sessionId 組成複合主鍵 */
	private Integer userId;

	public SessionUsers() {
	}

	public SessionUsers(Integer courseId, Integer sessionId, Integer userId) {
		this.courseId = courseId;
		this.sessionId = sessionId;
		this.userId = userId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/** 複合鍵建議以 sessionId + userId 判等（依表的 PRIMARY KEY 定義） */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SessionUsers))
			return false;
		SessionUsers that = (SessionUsers) o;
		return Objects.equals(sessionId, that.sessionId) && Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sessionId, userId);
	}

	@Override
	public String toString() {
		return "SessionUsers{" + "courseId=" + courseId + ", sessionId=" + sessionId + ", userId=" + userId + '}';
	}
	
	//為什麼要增加equals和hashCode??

}
