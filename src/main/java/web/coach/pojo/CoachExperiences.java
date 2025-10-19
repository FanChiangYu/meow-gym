package web.coach.pojo;

import java.util.Date;

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
@Table(name = "COACH_EXPERIENCES")
public class CoachExperiences {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EXP_ID")
	private Integer expId;
	@Column(name = "COACH_ID")
	private Integer coachId; 
	@Column(name = "COMPANY")
	private String company; 
	@Column(name = "TITLE")
	private String title; 
	@Column(name = "START_DATE")
	private Date startDate; 
	@Column(name = "END_DATE")
	private Date endDate; 
}
