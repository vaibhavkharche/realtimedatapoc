package com.maveric.realtimedatapoc.repository;

import com.maveric.realtimedatapoc.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
