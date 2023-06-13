package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotassistant.models.Camera;

public interface CamerasJPARepository  extends JpaRepository<Camera, String>{

}
