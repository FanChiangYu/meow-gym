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
@Table(name = "ORDER_ITEMS")
public class Orderitems extends Core {
//Hibernate
	private static final long serialVersionUID = -5775833812254098286L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ITEM_ID")
	private Integer orderItemId; // 訂單明細ID
	@Column(name = "ORDER_ID")
	private Integer orderId; // 訂單ID
	@Column(name = "COURSE_ID")
	private Integer courseId; // 課程ID
	@Column(name = "PURCHASED_PRICE")
	private Integer purchasedPrice; // 購買單價

//fields
//	private static final long serialVersionUID = 1L;
//	private Integer orderItemId; // 訂單明細ID
//	private Integer orderId; // 訂單ID
//	private Integer courseId; // 課程ID
//	private Integer purchasedPrice; // 購買單價

	
//constructors
//	public Orderitems() {
//	}
//
//	public Orderitems(Integer orderItemId, Integer orderId, Integer courseId, Integer purchasedPrice) {
//	super();
//	this.orderItemId = orderItemId;
//	this.orderId = orderId;
//	this.courseId = courseId;
//	this.purchasedPrice = purchasedPrice;
//	}

//setters/getters
//	public Integer getOrderItemId() {
//		return orderItemId;
//	}
//
//	public void setOrderItemId(Integer orderItemId) {
//		this.orderItemId = orderItemId;
//	}
//
//	public Integer getOrderId() {
//		return orderId;
//	}
//
//	public void setOrderId(Integer orderId) {
//		this.orderId = orderId;
//	}
//
//	public Integer getCourseId() {
//		return courseId;
//	}
//
//	public void setCourseId(Integer courseId) {
//		this.courseId = courseId;
//	}
//
//	public Integer getPurchasedPrice() {
//		return purchasedPrice;
//	}
//
//	public void setPurchasedPrice(Integer purchasedPrice) {
//		this.purchasedPrice = purchasedPrice;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
	
}
