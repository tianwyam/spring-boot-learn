package com.tianya.springboot.javafx.entity;

import java.io.File;

import lombok.Data;

/**
 * @description
 *	描述JAR包的实体类
 * @author TianwYam
 * @date 2021年6月25日下午1:45:45
 */
@Data
public class MavenJarBean {
	
	// 命令
	private String cmd ;
	
	private File pomFile ;
	
	private File jarFile ;
	
	private File sourceFile ;
	
	private File javadocFile ;
	
	
	public String cmd() {
		
		StringBuilder cmd = new StringBuilder();
		
		if (jarFile != null) {
			cmd.append(" -Dpackaging=jar -Dfile=");
			cmd.append(jarFile.getAbsolutePath());
		}else {
			cmd.append(" -Dfile=").append(pomFile.getAbsolutePath());
		}
		
		if (sourceFile != null) {
			cmd.append(" -Dsources=").append(sourceFile.getAbsolutePath());
		}
		
		if (javadocFile != null) {
			cmd.append(" -Djavadoc=").append(javadocFile.getAbsolutePath());
		}
		
		return cmd.toString() ;
	}
	

}
