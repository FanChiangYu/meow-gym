package web.course.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "COURSE_RECURRING_RULES")
public class CourseRecurringRules {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RULE_ID")
	private Integer ruleId;
	@Column(name = "COURSE_ID")
	private Integer courseId;
	@Column(name = "WEEKDAY")
	private Integer weekday;
	@Column(name = "TIME_SLOT")
	private Integer timeSlot;
}
