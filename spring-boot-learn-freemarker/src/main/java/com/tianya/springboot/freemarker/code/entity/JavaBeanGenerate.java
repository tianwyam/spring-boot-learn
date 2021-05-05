package com.tianya.springboot.freemarker.code.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: 
 * Java Bean自动生成
 * @author: TianwYam
 * @date 2021年5月5日 上午9:12:40
 */
@Data
@Builder
public class JavaBeanGenerate {
	
	// 包名
	private String packageName ;
	
	// 类名
	private String className ;
	
	// 属性
	private List<JavaBeanAttribute> attributes ;
	
	// 需要导入的包
	private List<String> importClassList ;
	
	// 生成的文件需要保存的全路径
	private String path ;

}
