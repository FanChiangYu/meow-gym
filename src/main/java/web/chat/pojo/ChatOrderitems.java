//package web.chat.pojo;
//
//import java.sql.Timestamp;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import core.pojo.Core;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "ORDER_ITEMS")
//public class ChatOrderitems extends Core {
////Hibernate
//	private static final long serialVersionUID = -5775833812254098286L;
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "ORDER_ITEM_ID")
//	private Integer orderItemId; // 訂單明細ID
//
////	@Column(name = "ORDER_ID")
////	private Integer orderId; // 訂單ID
//
//	@JoinColumn(name = "ORDER_ID")  // 外鍵欄位只在這裡映射一次 name 應該填入"參考端">>子表的ORDER_ID
//	private Integer orderId; // 訂單ID
//
//	@Column(name = "COURSE_ID")
//	private Integer courseId; // 課程ID
//
//	@Column(name = "PURCHASED_PRICE")
//	private Integer purchasedPrice; // 購買單價
//
//	@ManyToOne
//	@JoinColumn(name = "ORDER_ID", insertable = false, updatable = false)
//	private ChatOrders order; // 代表抓到"一個" ChatOrders
//}
