package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.sensorrules.CameraSensorRule;

public interface CameraSensorRuleJPARepository extends JpaRepository<CameraSensorRule, Integer>{

}

