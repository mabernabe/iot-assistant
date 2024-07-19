package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.sensorrules.SensorRule;

interface SensorRuleJPARepository extends JpaRepository<SensorRule, Integer>{

}
