package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.devices.Device;

public interface DevicesJPARepository extends JpaRepository<Device, String>{

}
