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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "novel_category")
public class NovelCategory {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id ;
	
	
	@Column
	private String category;
	
	
	@Column
	private String url ;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
