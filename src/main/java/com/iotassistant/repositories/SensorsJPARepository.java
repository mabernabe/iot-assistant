package com.iotassistant.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.transductor.Sensor;

public interface SensorsJPARepository extends JpaRepository<Sensor, String>{

}

