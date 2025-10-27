package web.chat.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "`ORDERS`")
public class ChatOrders extends Core{
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
	private Integer cardNumber; // 信用卡卡號
	
	@Column(name = "EXP_YEAR")
	private Integer expYear; // 信用卡到期年
	
	@Column(name = "EXP_MONTH")
	private Integer expMonth; // 信用卡到期月
	
	@Column(name = "CVC")//可以不寫
	private Integer cvc; // CVC驗證碼
	
	@Column(name = "CREATED_AT")
	private Timestamp createdAt; // 付款時間
	
	
	// 建立實體關聯
	// List 代表對方是"多"
	@OneToMany(mappedBy = "order")
	private List<ChatOrderitems> items;
	
}
