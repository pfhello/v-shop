package cn.itcast.shop.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Product implements Serializable{
	/*`pid` varchar(32) NOT NULL,
	  `pname` varchar(50) DEFAULT NULL,
	  `market_price` double DEFAULT NULL,
	  `shop_price` double DEFAULT NULL,
	  `pimage` varchar(200) DEFAULT NULL,
	  `pdate` date DEFAULT NULL,
	  `is_hot` int(11) DEFAULT NULL,
	  `pdesc` varchar(255) DEFAULT NULL,
	  `pflag` int(11) DEFAULT NULL,
	  `cid` varchar(32) DEFAULT NULL,*/
	private String pid;
	private String pname;
	private Integer marketPrice;
	private Integer shopPrice;
	private String pimage;
	private Date pdate;
	private Integer isHot;
	private String pdesc;
	private Integer pflag;
	private String cid;
}
