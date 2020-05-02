package com.tianya.springboot.excel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "excel_user")
@AllArgsConstructor
@NoArgsConstructor
public class ExcelUser implements Serializable{
	
	private static final long serialVersionUID = 7535853689840546770L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id ;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "addr")
	private String addr;
	
	@Column(name = "height")
	private int height;
	
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
