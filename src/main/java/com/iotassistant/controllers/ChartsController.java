package com.iotassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iotassistant.controllers.dtos.ChartDTO;
import com.iotassistant.controllers.dtos.ChartsDTO;
import com.iotassistant.controllers.dtos.ErrorDTO;
import com.iotassistant.models.SensorChart;
import com.iotassistant.services.ChartsService;
import com.iotassistant.services.SensorsService;


@RestController
@RequestMapping("${charts.uri}")
public class ChartsController { // NO_UCD (unused code)
 
	
	@Autowired
	private ChartsService chartsService;

	@Autowired
	private SensorsService sensorsService;
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<?> newChart(@RequestBody ChartDTO chartDTO)  {
		SensorChart chart = chartDTO.getChart();
		ErrorDTO errorDTO = null;
		if (chartsService.existChart(chart)) {
			errorDTO = ErrorDTO.CHART_ALREADY_EXIST;
		}
		if (!sensorsService.exist(chart.getSensorName()))  {
			errorDTO = ErrorDTO.DEVICE_NOT_FOUND;
	    }
		if (!sensorsService.hasSensorProperty(chart.getSensorName(), chart.getPropertyObserved()))  {
			errorDTO = ErrorDTO.SENSOR_HAS_NOT_PROPERTY;
			errorDTO.formatMessage(chart.getPropertyObserved().toString());
	    }
		if (errorDTO != null) {
			return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
		}
		chartsService.newChart(chart);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}
	

    @RequestMapping(value="/", method = RequestMethod.GET)
	public ChartsDTO getCharts() throws Exception {
		return new ChartsDTO(chartsService.getAllCharts());
		
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteChartById(@PathVariable("id") int id)  {
		if(chartsService.getChartById(id) == null) {
			ErrorDTO chartnofFoundError = ErrorDTO.CHART_NOT_FOUND;
			return new ResponseEntity<>(chartnofFoundError, chartnofFoundError.getHttpStatus());
        }
		chartsService.deleteChartById(id);
		return new ResponseEntity<>(null, HttpStatus.OK);			
	}


}
