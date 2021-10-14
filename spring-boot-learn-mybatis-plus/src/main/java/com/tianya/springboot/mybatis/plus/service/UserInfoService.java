package com.tianya.springboot.mybatis.plus.service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tianya.springboot.mybatis.plus.entity.UserInfo;
import com.tianya.springboot.mybatis.plus.mapper.IUserInfoMapper;

/**
 * @description
 *	服务层：mybatis-plus 提供的基类 IService
 * @author TianwYam
 * @date 2021年10月14日下午12:09:48
 */
@Service
public class UserInfoService implements IService<UserInfo> {
	
	
	@Autowired
	private IUserInfoMapper userInfoMapper ;
	

	@Override
	public boolean saveBatch(Collection<UserInfo> entityList, int batchSize) {
		
		return false;
	}

	@Override
	public boolean saveOrUpdateBatch(Collection<UserInfo> entityList, int batchSize) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBatchById(Collection<UserInfo> entityList, int batchSize) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveOrUpdate(UserInfo entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserInfo getOne(Wrapper<UserInfo> queryWrapper, boolean throwEx) {


		
		return null;
	}

	@Override
	public Map<String, Object> getMap(Wrapper<UserInfo> queryWrapper) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public <V> V getObj(Wrapper<UserInfo> queryWrapper, Function<? super Object, V> mapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseMapper<UserInfo> getBaseMapper() {
		return userInfoMapper;
	}

}
