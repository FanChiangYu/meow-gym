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

}
