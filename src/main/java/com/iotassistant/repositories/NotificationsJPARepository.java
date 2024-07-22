package com.iotassistant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.notifications.Notification;

interface NotificationsJPARepository extends JpaRepository<Notification, Integer>{

	List<Notification> findAllByOrderByIdDesc();

}
