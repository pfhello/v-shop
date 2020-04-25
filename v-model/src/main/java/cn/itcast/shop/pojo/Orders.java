package cn.itcast.shop.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Orders implements Serializable{
	/*`oid` varchar(32) NOT NULL,
	  `ordertime` datetime DEFAULT NULL,
	  `total` double DEFAULT NULL,
	  `state` int(11) DEFAULT NULL,
	  `address` varchar(30) DEFAULT NULL,
	  `name` varchar(20) DEFAULT NULL,
	  `telephone` varchar(20) DEFAULT NULL,
	  `uid` varchar(32) DEFAULT NULL,*/

	private String oid;
	private String ordertime;
	private Integer total;
	private Integer state;//是否付款
	private String address;
	private String name;
	private String telephone;
	private String uid;
}
