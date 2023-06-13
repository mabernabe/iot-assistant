package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.sensorrules.TriggerActuatorSensorRule;

public interface TriggerActuatorSensorRuleJPARepository extends JpaRepository<TriggerActuatorSensorRule, Integer>{

}

