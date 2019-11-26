package com.etoc.vo;

import com.etoc.constant.OrderEnum;

/**
 * 
 * 订购操作状态实体，作用范围是全局
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年3月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OrderInstance
{
    String id;
    
    OrderEnum order;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public OrderEnum getOrder()
    {
        return order;
    }
    
    public void setOrder(OrderEnum order)
    {
        this.order = order;
    }
    
}
