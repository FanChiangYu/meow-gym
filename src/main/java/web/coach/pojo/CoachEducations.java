package web.coach.pojo;


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
@Table(name = "COACH_EDUCATIONS")
public class CoachEducations {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EDU_ID")
	private Integer eduId;
	@Column(name = "COACH_ID")
	private Integer coachId; 
	@Column(name = "SCHOOL")
	private String school; 
	@Column(name = "DEGREE")
	private String degree; 
}
