package com.iotassistant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.notifications.SensorRuleNotification;


interface SensorRuleNotificationsJPARepository extends JpaRepository<SensorRuleNotification, Integer>{

	List<SensorRuleNotification> findAllByOrderByIdDesc();

}
