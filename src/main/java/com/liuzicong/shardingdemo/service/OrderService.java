package com.liuzicong.shardingdemo.service;

import com.liuzicong.shardingdemo.entity.Order;
import com.liuzicong.shardingdemo.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void initEnvironment() throws SQLException {
        orderRepository.createTableIfNotExists();
        orderRepository.truncateTable();
    }

    public void cleanEnvironment() throws SQLException {
        orderRepository.dropTable();
    }

    @Transactional
    public void processSuccess() throws SQLException {
        log.info("-------------- Process Success Begin ---------------");
        List<Long> orderIds = insertData();
        printData();
        deleteData(orderIds);
        printData();
        log.info("-------------- Process Success Finish --------------");
    }

    @Transactional
    public void processFailure() throws SQLException {
        log.info("-------------- Process Failure Begin ---------------");
        insertData();
        log.info("-------------- Process Failure Finish --------------");
        throw new RuntimeException("Exception occur for transaction test.");
    }

    private List<Long> insertData() throws SQLException {
        log.info("---------------------------- Insert Data ----------------------------");
        List<Long> result = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            Order order = new Order();
            order.setUserId(i);
            order.setAddressId(i);
            order.setStatus("INSERT_TEST");
            orderRepository.insert(order);
            result.add(order.getOrderId());
        }
        return result;
    }

    private void deleteData(final List<Long> orderIds) throws SQLException {
        log.info("---------------------------- Delete Data ----------------------------");
        for (Long each : orderIds) {
            orderRepository.delete(each);
        }
    }

    public void printData() throws SQLException {
        log.info("---------------------------- Print Order Data -----------------------");
        for (Order each : orderRepository.selectAll()) {
            log.info("order:{}", each);
        }
    }
}
