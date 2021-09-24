package com.liuzicong.shardingdemo.repository;

import com.liuzicong.shardingdemo.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderRepository extends CommonRepository<Order, Long>{
}
