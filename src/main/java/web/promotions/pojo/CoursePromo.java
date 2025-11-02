package web.promotions.pojo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "course_promotions")
public class CoursePromo extends Core {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "promo_id")
	private Integer promoId;
	@Column(name = "course_id")
	private Integer courseId;
	@Column(name = "promo_price")
	private Integer promoPrice;
	@Column(name = "date_start")
	private Date dateStart;
	@Column(name = "date_end")
	private Date dateEnd;
	@Column(name = "img_url")
	private String imgUrl;
	@Transient
	private String imgBase64;
	@Transient
	private String filename;
}
