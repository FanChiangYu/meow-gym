package web.order.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private Integer cardNumber; // 信用卡卡號
	@Column(name = "EXP_YEAR")
	private Integer expYear; // 信用卡到期年
	@Column(name = "EXP_MONTH")
	private Integer expMonth; // 信用卡到期月
	@Column(name = "CVC")//可以不寫
	private Integer cvc; // CVC驗證碼
	@Column(name = "CREATED_AT")
	private Timestamp createdAt; // 付款時間
	
//fields
//	private static final long serialVersionUID = 1L;
//	private Integer orderId; // 訂單ID
//	private Integer userId; // 使用者ID
//	private Integer payAmount; // 總付款金額
//	private String status; // 訂單狀態
//	private String paymentMethod; // 付款方法
//	private String cardHolder; // 持卡人姓名
//	private Integer cardNumber; // 信用卡卡號
//	private Integer expYear; // 信用卡到期年
//	private Integer expMonth; // 信用卡到期月
//	private Integer cvc; // CVC驗證碼
//	private Timestamp createdAt; // 付款時間
	
//constructors
//	public Orders() {
//	}
//	
//	public Orders(Integer orderId, Integer userId, Integer payAmount, String status, String paymentMethod,
//			String cardHolder, Integer cardNumber, Integer expYear, Integer expMonth, Integer cvc, Timestamp createdAt) {
//		super();
//		this.orderId = orderId;
//		this.userId = userId;
//		this.payAmount = payAmount;
//		this.status = status;
//		this.paymentMethod = paymentMethod;
//		this.cardHolder = cardHolder;
//		this.cardNumber = cardNumber;
//		this.expYear = expYear;
//		this.expMonth = expMonth;
//		this.cvc = cvc;
//		this.createdAt = createdAt;
//	}

//setters/getters
//	public Integer getOrderId() {
//		return orderId;
//	}
//
//	public void setOrderId(Integer orderId) {
//		this.orderId = orderId;
//	}
//
//	public Integer getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Integer userId) {
//		this.userId = userId;
//	}
//
//	public Integer getPayAmount() {
//		return payAmount;
//	}
//
//	public void setPayAmount(Integer payAmount) {
//		this.payAmount = payAmount;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public String getPaymentMethod() {
//		return paymentMethod;
//	}
//
//	public void setPaymentMethod(String paymentMethod) {
//		this.paymentMethod = paymentMethod;
//	}
//
//	public String getCardHolder() {
//		return cardHolder;
//	}
//
//	public void setCardHolder(String cardHolder) {
//		this.cardHolder = cardHolder;
//	}
//
//	public Integer getCardNumber() {
//		return cardNumber;
//	}
//
//	public void setCardNumber(Integer cardNumber) {
//		this.cardNumber = cardNumber;
//	}
//
//	public Integer getExpYear() {
//		return expYear;
//	}
//
//	public void setExpYear(Integer expYear) {
//		this.expYear = expYear;
//	}
//
//	public Integer getExpMonth() {
//		return expMonth;
//	}
//
//	public void setExpMonth(Integer expMonth) {
//		this.expMonth = expMonth;
//	}
//
//	public Integer getCvc() {
//		return cvc;
//	}
//
//	public void setCvc(Integer cvc) {
//		this.cvc = cvc;
//	}
//
//	public Timestamp getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(Timestamp createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
}
