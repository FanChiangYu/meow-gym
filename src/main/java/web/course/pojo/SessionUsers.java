package web.course.pojo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SESSIONS_USERS")
public class SessionUsers {
	@Column(name = "SESSION_ID")
	private Integer sessionId;
	@Column(name = "USER_ID")
	private Integer userId;
}
