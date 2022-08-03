package com.spring.boardweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boardweb.dto.StoreDTO;
import com.spring.boardweb.service.store.StoreService;

@RestController
public class HomeController {
	@Autowired
	StoreService storeService;
	
	@GetMapping("/")
	public ModelAndView mainPage() {
		ModelAndView mv = new ModelAndView();
		
		List<StoreDTO> carouselList = storeService.getCarousel();
		
		mv.addObject("carouselList", carouselList);
		mv.setViewName("index.html");
		
		return mv;
	}
}
