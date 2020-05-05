package com.tianya.springboot.common.entity.crawler;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "novel_info")
public class NovelInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id ;
	
	@Column
	private String name ;
	
	@Column
	private String author ;
	
	@Column
	private String type ;
	
	@Column
	private String status ;
	
	@Column
	private String updateDate ;
	
	@Column
	private String introduction ;

}
