package com.tianya.springboot.common.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageUtils {
	
	// 当前页 页码
	@Builder.Default
	private int pageNum = 1 ;

	// 一页大小 页大小
	@Builder.Default
	private int pageSize = 10 ;
	
	// 总记录数 总条数
	private int totalSize ;
	
	// 总页数
	private int totalNum ;
	
	
	
	
	
}
