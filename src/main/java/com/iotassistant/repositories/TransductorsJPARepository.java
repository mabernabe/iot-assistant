package com.iotassistant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iotassistant.models.devices.transductors.Transductor;


@Repository
public interface TransductorsJPARepository extends JpaRepository<Transductor, String>{

}
