package web.chat.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import core.pojo.Core;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseDTO extends Core{
	private static final long serialVersionUID = -5775833812254098286L;
	
    private Integer userId;
    private Integer courseId;

}
