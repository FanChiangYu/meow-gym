package web.coach.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoachApplyUpdateRequest {
	private CoachProfiles profile;
	private CoachEducations education;
	private CoachExperiences experience;
	private CoachCertificates certificate;
	private String imgBase64;
	private String fileName;
}
