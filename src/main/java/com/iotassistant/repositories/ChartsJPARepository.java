package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.SensorChart;

public interface ChartsJPARepository extends JpaRepository<SensorChart, Integer>{

}
