package web.promotions.pojo;

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
@Table(name = "COURSE_PROMOTIONS")
public class CoursePromo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COURSE_ID")
	private Integer courseId;
	@Column(name = "PROMO_ID")
	private Integer promoId;
	@Column(name = "PROMO_PRICE")
	private Integer promoPrice;
	@Column(name = "DATE_START")
	private Date dateStart; 
	@Column(name = "DATE_END")
	private Date dateEnd; 
	@Column(name = "IMG_URL")
	private String imgUrl;
	public String getImgBase64Str() {
		return null;
	} 
}
