package com.changgou.comment.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:shenkunlin
 * @Description:ProductComment构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_product_comment")
public class ProductComment implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//评论ID

    @Column(name = "sku_id")
	private Long skuId;//商品ID

    @Column(name = "order_id")
	private Long orderId;//订单ID

    @Column(name = "username")
	private String username;//登陆用户名

    @Column(name = "content")
	private String content;//评论内容

    @Column(name = "audit_time")
	private Date auditTime;//评论时间



	//get方法
	public Integer getId() {
		return id;
	}

	//set方法
	public void setId(Integer id) {
		this.id = id;
	}
	//get方法
	public Long getSkuId() {
		return skuId;
	}

	//set方法
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	//get方法
	public Long getOrderId() {
		return orderId;
	}

	//set方法
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	//get方法
	public String getUsername() {
		return username;
	}

	//set方法
	public void setUsername(String username) {
		this.username = username;
	}
	//get方法
	public String getContent() {
		return content;
	}

	//set方法
	public void setContent(String content) {
		this.content = content;
	}
	//get方法
	public Date getAuditTime() {
		return auditTime;
	}

	//set方法
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}


}
