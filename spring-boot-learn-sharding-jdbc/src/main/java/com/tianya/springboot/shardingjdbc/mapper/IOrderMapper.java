package com.tianya.springboot.shardingjdbc.mapper;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianya.springboot.shardingjdbc.entity.Order;

@Repository
public interface IOrderMapper extends BaseMapper<Order>{

}
