package web.user.pojo;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name = "USER")
public class User extends Core {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", insertable = false)
	private Integer userId;

	@Column(name = "cnt_code")
	private Integer cntCode;

	@Column(name = "dist_code")
	private Integer distCode;

	@Column(name = "role")
	private Integer role;

	@Column(name = "detail_address")
	private String detailAddress;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;

	@Column(name = "reset_code")
	private String resetCode;

	@Column(name = "phone")
	private String phone;

	@Column(name = "avatar_url")
	private String avatarUrl;

	@Column(name = "is_banned")
	private boolean isBanned;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "gender")
	private String gender;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at")
	private Timestamp createdAt;

	@Transient
	private String imgBase64;

	@Transient
	private String filename;
	
	@Transient
	private MultipartFile avatarFile;

}
