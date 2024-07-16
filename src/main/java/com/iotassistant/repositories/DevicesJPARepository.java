package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.Device;

public interface DevicesJPARepository extends JpaRepository<Device, String>{

}
