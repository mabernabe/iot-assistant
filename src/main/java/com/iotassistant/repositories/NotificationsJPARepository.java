package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.notifications.Notification;

interface NotificationsJPARepository extends JpaRepository<Notification, Integer>{

}
