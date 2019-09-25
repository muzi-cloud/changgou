package com.changgou.user.pojo;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author:shenkunlin
 * @Description:Footprint构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="tb_footprint")
public class Footprint implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//浏览足迹ID

    @Column(name = "sku_id")
	private Long skuId;//商品ID

    @Column(name = "username")
	private String username;//登陆用户名

    @Column(name = "Browse_time")
	private Date BrowseTime;//浏览时间



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
	public String getUsername() {
		return username;
	}

	//set方法
	public void setUsername(String username) {
		this.username = username;
	}
	//get方法
	public Date getBrowseTime() {
		return BrowseTime;
	}

	//set方法
	public void setBrowseTime(Date BrowseTime) {
		this.BrowseTime = BrowseTime;
	}


}
