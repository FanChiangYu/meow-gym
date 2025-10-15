package web.order.dao;

import core.dao.CoreDao;
import web.order.pojo.Orders;

public interface OrderDao extends CoreDao<Orders, Integer>{
	
	int insert(Orders orders);

}
