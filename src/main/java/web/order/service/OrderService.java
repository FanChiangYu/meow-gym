package web.order.service;

import core.service.CoreService;
import web.order.pojo.Orderitems;
import web.order.pojo.Orders;

public interface OrderService extends CoreService{
	
//	Orderitems addcart(Orderitems orderitems, Integer userId);
	
	Orders payment(Orders orders);

}
