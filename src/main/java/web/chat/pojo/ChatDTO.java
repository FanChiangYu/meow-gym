package web.chat.pojo;

import java.sql.Timestamp;
import java.util.Date;

//為了方便找出session的name而設置的，跟資料庫table無關
//ChatDTO 不需要做hibernate Annotation映射，因為這個購物袋單純用來查閱用的，並沒有跟資料庫有關聯

public class ChatDTO {
	private Integer chatId;
	private Integer courseId;
	private Integer userId;
	private String name; // 或 nickname
	private String content;
	private Timestamp createdAt;

	// add 20251114
	private String avatarUrl;
	private Integer role;
	// add 20251114 end

	public ChatDTO() {
		super();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	// add 20251114
	public void setAvatarUrl(String AvatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getRole() {
		return role;
	}
	// add end 20251114

//	public ChatDTO(Integer chatId, Integer courseId, Integer userId, String name, String content, Timestamp createdAt) {
//		super();
//		this.chatId = chatId;
//		this.courseId = courseId;
//		this.userId = userId;
//		this.name = name;
//		this.content = content;
//		this.createdAt = createdAt;
//	}

	public ChatDTO(Integer chatId, Integer courseId, Integer userId, String name, String content, Timestamp createdAt, String avatarUrl, Integer role) {
		super();
		this.chatId = chatId;
		this.courseId = courseId;
		this.userId = userId;
		this.name = name;
		this.content = content;
		this.createdAt = createdAt;
		this.avatarUrl = avatarUrl;
		this.role = role;
	}

//	public ChatDTO(int chatId, int courseId, int userId, String name, String content, Date createdAt) {
//		this.chatId = chatId;
//		this.courseId = courseId;
//		this.userId = userId;
//		this.name = name;
//		this.content = content;
//		this.createdAt = (createdAt == null) ? null : new Timestamp(createdAt.getTime());
//	}

	public ChatDTO(int chatId, int courseId, int userId, String name, String content, Date createdAt, String avatarUrl, Integer role) {
		this.chatId = chatId;
		this.courseId = courseId;
		this.userId = userId;
		this.name = name;
		this.content = content;
		this.createdAt = (createdAt == null) ? null : new Timestamp(createdAt.getTime());
		this.avatarUrl = avatarUrl;
		this.role = role;
	}

}
