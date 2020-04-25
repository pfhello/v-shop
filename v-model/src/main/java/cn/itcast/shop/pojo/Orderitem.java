package cn.itcast.shop.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Orderitem implements Serializable{
	 /*`itemid` varchar(32) NOT NULL,
	  `count` int(11) DEFAULT NULL,
	  `subtotal` double DEFAULT NULL,
	  `pid` varchar(32) DEFAULT NULL,
	  `oid` varchar(32) DEFAULT NULL,
	  PRIMARY KEY (`itemid`),
	  KEY `fk_0001` (`pid`),
	  KEY `fk_0002` (`oid`),*/
	
	private String itemid;
	private Integer count;
	private Integer subtotal;
	private String pid;
	private String oid;

}
