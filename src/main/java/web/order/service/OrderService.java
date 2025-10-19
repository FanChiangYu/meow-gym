package web.order.service;

import core.service.CoreService;
import web.order.pojo.Orders;

public interface OrderService extends CoreService{
	
	Orders payment(Orders orders);

}
