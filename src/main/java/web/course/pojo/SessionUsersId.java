package web.course.pojo;

import java.io.Serializable;
import java.util.Objects;

public class SessionUsersId implements Serializable{
	public Integer sessionId;
	public Integer userId;
	@Override
	public boolean equals(Object o) {
		if (this == o) {
		return true;
		}
		if (o == null || getClass() != o.getClass()) {
		return false;
		}
		SessionUsersId id = (SessionUsersId) o;
		return Objects.equals(sessionId, id.sessionId)
		&& Objects.equals(userId, id.userId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(sessionId, userId);
	}
}
