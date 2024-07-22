package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.SensorRule;

public interface SensorRuleJPARepository extends JpaRepository<SensorRule, Integer>{

}
