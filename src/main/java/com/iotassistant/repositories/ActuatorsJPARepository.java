package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.devices.transductors.Actuator;

public interface ActuatorsJPARepository extends JpaRepository<Actuator, String>{

}
