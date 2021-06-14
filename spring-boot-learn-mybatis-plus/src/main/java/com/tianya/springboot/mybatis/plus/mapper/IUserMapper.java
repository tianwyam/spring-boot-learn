package com.tianya.springboot.mybatis.plus.mapper;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianya.springboot.mybatis.plus.entity.UserBean;

@Repository
public interface IUserMapper extends BaseMapper<UserBean> {

}
