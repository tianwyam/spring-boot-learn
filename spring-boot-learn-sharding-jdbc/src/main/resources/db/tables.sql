
-- 订单分表1
CREATE TABLE `t_order_1` (
  `order_id` bigint(20) NOT NULL COMMENT '主键',
  `order_name` varchar(50) DEFAULT NULL COMMENT '订单名称',
  `order_type` varchar(50) DEFAULT NULL COMMENT '订单类型',
  `order_desc` varchar(200) DEFAULT NULL COMMENT '订单详情',
  `create_user_id` int(10) DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


-- 订单分表2
CREATE TABLE `t_order_2` (
  `order_id` bigint(20) NOT NULL COMMENT '主键',
  `order_name` varchar(50) DEFAULT NULL COMMENT '订单名称',
  `order_type` varchar(50) DEFAULT NULL COMMENT '订单类型',
  `order_desc` varchar(200) DEFAULT NULL COMMENT '订单详情',
  `create_user_id` int(10) DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;



-- 公共字典表
CREATE TABLE `t_type_dict` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dict_type` varchar(50) NOT NULL COMMENT '字典类型',
  `dict_desc` varchar(100) DEFAULT NULL COMMENT '字典描述',
  `dict_order` tinyint(10) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;






