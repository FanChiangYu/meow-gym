package web.user.pojo;

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
@Table(name = "DISTRICT")
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DIST_CODE")
	private Integer distCode;
	@Column(name = "CNT_CODE")
	private Integer cntCode;
	@Column(name = "DIST_NAME")
	private String distName;
}
