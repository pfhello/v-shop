package cn.itcast.shop.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable{
	/* `uid` varchar(32) NOT NULL,
	  `username` varchar(20) DEFAULT NULL,
	  `password` varchar(20) DEFAULT NULL,
	  `name` varchar(20) DEFAULT NULL,
	  `email` varchar(30) DEFAULT NULL,
	  `telephone` varchar(20) DEFAULT NULL,
	  `birthday` date DEFAULT NULL,
	  `sex` varchar(10) DEFAULT NULL,
	  `state` int(11) DEFAULT NULL,
	  `code` varchar(64) DEFAULT NULL,*/
	private String uid;
	private String username;
	private String password;
	private String name;
	private String email;
	private String telephone;
	private Date birthday;
	private String sex;
	private Integer state;//是否激活
	private String code;//激活码
}
