package com.iotassistant.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.iotassistant.models.Camera;

@Repository
@Scope("singleton")
public class CamerasRepository {
	
	private Map<String, Camera> inMemoryCamera = new HashMap<String, Camera>();

	@Autowired
	private CamerasJPARepository camerasJPARepository;
	
	
	@PostConstruct
	/* Populate in memory cameras map with the cameras in persistence */
    private void populateInMemoryCameras() {
        List<Camera> allCameras = camerasJPARepository.findAll();
        for (Camera camera : allCameras) {
        	inMemoryCamera.put(camera.getName(), camera);
        }
	}
	
	public Camera getCameraByName(String name) {
		return inMemoryCamera.get(name);
	}
	
	public List<Camera> getAllCameras() {
		return new ArrayList<Camera>(inMemoryCamera.values());
	}
	
	public Camera save(Camera camera) {
		camera = camerasJPARepository.save(camera);
		inMemoryCamera.put(camera.getName(), camera);
		return camera;
	}

	public void deleteCameraByName(String name) {
		camerasJPARepository.deleteById(name);
		inMemoryCamera.remove(name);	
	}
	
	public void update(Camera camera) {
		camerasJPARepository.save(camera);
	}

}
