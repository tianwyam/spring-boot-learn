package com.tianya.springboot.mybatis.service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tianya.springboot.mybatis.entity.TableColumnBean;
import com.tianya.springboot.mybatis.entity.UserBean;
import com.tianya.springboot.mybatis.mapper.IUserMapper;

@Service
public class UserService {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory ;
	
	@Autowired
	private IUserMapper userMapper ;
	
	public List<UserBean> getList(){
		return userMapper.getUserList();
	}
	
	public List<Map<String, Object>> getUserTableColumns(){
		return userMapper.getUserTableColumns();
	}
	
	
	public List<TableColumnBean> getUserMeta() {
		
		List<TableColumnBean> columnList = new ArrayList<>();
		try {
			
			DatabaseMetaData metaData = sqlSessionFactory.openSession().getConnection().getMetaData();
			ResultSet columns = metaData.getColumns("test", null, "user", null);
			
			System.out.println("column: ");
			while(columns.next()){
				TableColumnBean columnBean = TableColumnBean.builder()
						.dbName(columns.getString("TABLE_CAT"))
						.tableName(columns.getString("TABLE_NAME"))
						.columnName(columns.getString("COLUMN_NAME"))
						.autoIncrement(columns.getString("IS_AUTOINCREMENT"))
						.generatedColumn(columns.getString("IS_GENERATEDCOLUMN"))
						.columnType(columns.getString("TYPE_NAME"))
						.columnLength(columns.getInt("COLUMN_SIZE"))
						.columnRemark(columns.getString("REMARKS"))
						.build();
				columnList.add(columnBean);
	        }
			
			System.out.println(JSON.toJSONString(columnList, true));
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return columnList;
	}
	
	
	
	
	
	
	
	

}
