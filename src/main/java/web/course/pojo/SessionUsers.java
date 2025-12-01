package web.course.pojo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@Table(name = "SESSION_USERS")
@IdClass(SessionUsersId.class)
public class SessionUsers {
	@Id
	@Column(name = "SESSION_ID")
	private Integer sessionId;
	@Id
	@Column(name = "USER_ID")
	private Integer userId;
}
