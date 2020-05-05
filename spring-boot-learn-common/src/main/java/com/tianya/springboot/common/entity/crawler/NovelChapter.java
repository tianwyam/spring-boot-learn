package com.tianya.springboot.common.entity.crawler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NovelChapter {

	
	private long id ;
	
	// 卷
	private String volume ;
	
	
	// 章节
	private String chapter ;
	
	
	private String url ;
	
}
