package com.iotassistant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.notifications.DeviceOfflineNotification;

public interface TransductorOfflineNotificationsJPARepository extends JpaRepository<DeviceOfflineNotification, Integer>{


	List<DeviceOfflineNotification> findAllByOrderByIdDesc();

}
