package web.order.pojo;

import core.pojo.Core;

public class Orderitems extends Core {

//fields
	private static final long serialVersionUID = 1L;
	private Integer orderItemId; // 訂單明細ID
	private Integer orderId; // 訂單ID
	private Integer courseId; // 課程ID
	private Integer purchasedPrice; // 購買單價

	
//constructors
	public Orderitems() {
	}

	public Orderitems(Integer orderItemId, Integer orderId, Integer courseId, Integer purchasedPrice) {
	super();
	this.orderItemId = orderItemId;
	this.orderId = orderId;
	this.courseId = courseId;
	this.purchasedPrice = purchasedPrice;
	}

//setters/getters
	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getPurchasedPrice() {
		return purchasedPrice;
	}

	public void setPurchasedPrice(Integer purchasedPrice) {
		this.purchasedPrice = purchasedPrice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
