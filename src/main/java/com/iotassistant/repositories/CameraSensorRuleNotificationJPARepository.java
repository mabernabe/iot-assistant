package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.notifications.SensorRuleCameraNotification;

interface CameraSensorRuleNotificationJPARepository extends JpaRepository<SensorRuleCameraNotification, Integer>{

}

