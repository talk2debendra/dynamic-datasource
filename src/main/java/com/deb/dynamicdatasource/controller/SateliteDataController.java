package com.deb.dynamicdatasource.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.deb.dynamicdatasource.enums.Satelites;
import com.deb.dynamicdatasource.service.AryabhataDataService;
import com.deb.dynamicdatasource.service.SARALDataService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class SateliteDataController {

	@Autowired
	private SARALDataService saralDataService;

	@Autowired
	private AryabhataDataService aryabhatDataService;

	@GetMapping("/")
	public ModelAndView test() {
		ModelAndView view = new ModelAndView();
		view.setViewName("home");
		return view;
	}

	@GetMapping("/files")
	public ResponseEntity<String> getFilesByType(@RequestParam(name = "type") String type) {
		String baseFilePath = null;
		Satelites sateLiteType = Satelites.getSatelite(type);
		
		if (Objects.nonNull(sateLiteType)) {
			switch (sateLiteType) {
			case SARAL:
				baseFilePath = saralDataService.getFtpPath();
				break;
			case ARAYABHAT:
				baseFilePath = aryabhatDataService.getFtpPath();
				break;
			default:
				baseFilePath = "ftp://ftp.mosdac.gov.in/2020";
				break;
			}
		} else {
			baseFilePath = "ftp://ftp.mosdac.gov.in/2020";
		}

		return ResponseEntity.ok(baseFilePath);
	}

}
