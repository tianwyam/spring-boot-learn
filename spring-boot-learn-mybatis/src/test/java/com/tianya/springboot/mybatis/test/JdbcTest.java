package com.tianya.springboot.mybatis.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * @description
 *	JDBC 获取元数据方式
 * @author TianwYam
 * @date 2021年10月6日上午10:39:46
 */
public class JdbcTest {
	
	
	public static void main(String[] args) throws Exception {
		
		// 1. 配置 数据库链接信息
		String driver="com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC";
		String username="root";
		String password="mysql";

		//2. 加载驱动
		Class.forName(driver);

		//3. 建立连接
		Connection connection= DriverManager.getConnection(url,username,password);
		
		//4. 获取元数据
		DatabaseMetaData metaData = connection.getMetaData();
		
		//5. 获取表的字段 列
		ResultSet resultSet = metaData.getColumns("test", null, "user", null);

		//6. 处理ResultSet
		while(resultSet.next()){
		    //处理resultSet
		}

		//7. 释放资源
	    resultSet.close();
	    connection.close();
	}

}
