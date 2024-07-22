package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.devices.Camera;

public interface CamerasJPARepository  extends JpaRepository<Camera, String>{

}
