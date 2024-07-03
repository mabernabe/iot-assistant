package com.iotassistant.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iotassistant.models.transductor.Sensor;

@Repository
public interface SensorsJPARepository extends JpaRepository<Sensor, String>{

}

