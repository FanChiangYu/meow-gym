package web.order.pojo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "ORDERS")
public class Orders extends Core{
//Hibernate
	private static final long serialVersionUID = -5775833812254098286L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Integer orderId; // 訂單ID
	@Column(name = "USER_ID")
	private Integer userId; // 使用者ID
	@Column(name = "PAY_AMOUNT")
	private Integer payAmount; // 總付款金額
	@Column(name = "STATUS") //可以不寫
	private String status; // 訂單狀態
	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod; // 付款方法
	@Column(name = "CARD_HOLDER")
	private String cardHolder; // 持卡人姓名
	@Column(name = "CARD_NUMBER")
	private String cardNumber; // 信用卡卡號
	@Column(name = "EXP_DATE")
	private String expDate; // 信用卡到期年月
	@Column(name = "CVC")//可以不寫
	private String cvc; // CVC驗證碼
	@Column(name = "CREATED_AT")
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp createdAt; // 訂單成立時間
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
	private List<Orderitems> Orderitems;
	
	// 回應前端用
	@Transient
	private String name;  //回傳前端會員姓名
	@Transient
	private String email;  //回傳前端會員Email
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "user_id", insertable = false, updatable = false)
//	private User user;
}
