package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.sensorrules.SensorRule;

public interface SensorRuleJPARepository extends JpaRepository<SensorRule, Integer>{

}
