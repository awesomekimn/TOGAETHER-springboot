package com.spring.boardweb.controller.store;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boardweb.commons.PicUtils;
import com.spring.boardweb.entity.CustomUserDetails;
import com.spring.boardweb.entity.Store;
import com.spring.boardweb.entity.StoreFile;
import com.spring.boardweb.service.store.StoreService;

@RestController
@RequestMapping("/store")
public class StoreController {
	@Autowired
	StoreService storeService;

	@GetMapping("/getStoreList/{categoryNm}")
	public ModelAndView getStoreListView(@PathVariable String categoryNm,
			@PageableDefault(page = 0, size = 12) Pageable pageable) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("store/getStoreList.html");

		Page<Store> storeList = storeService.getStoreList(categoryNm, pageable);
		
		//System.out.println(storeList.getNumberOfElements());

		for (Store content : storeList) {
			//System.out.println(content.getStoreSeq());
			content.setFileList(storeService.getStoreFileList(content.getStoreSeq()));
//			System.out.println(content + "////////////////////");
		}

		mv.addObject("storeList", storeList);

//		System.out.println(storeList);

		return mv;
	}

	@GetMapping("/storeDetail/{storeSeq}")
	public ModelAndView getStoreView(@PathVariable int storeSeq, @AuthenticationPrincipal CustomUserDetails loginUser) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("store/storeDetail.html");

		Store store = storeService.getStore(storeSeq);

		store.setFileList(storeService.getStoreFileList(store.getStoreSeq()));
		store.setUserAni(storeService.getUserAni(loginUser.getUsername()));
//		List<Review> reviewList = storeService.getreviewList(reviewSeq);
//		System.out.println(store.getParking());
		mv.addObject("store", store);
//		mv.addObject("reviewList", reviewList);
		
		return mv;
	}

	@GetMapping("/insertStore")
	public ModelAndView insertStoreView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("store/insertStore.html");

		return mv;
	}

	@PostMapping("/insertStore")
	public void insertStore(HttpServletResponse response, Store store, HttpServletRequest request,
			MultipartHttpServletRequest multipartServletRequest) throws IOException {
		int storeSeq = storeService.insertStore(store);

		PicUtils picUtils = new PicUtils();
		List<StoreFile> fileList = picUtils.parseFileInfo(storeSeq, request, multipartServletRequest);

		storeService.insertStoreFileList(fileList);

		response.sendRedirect("/store/storeDetail/" + storeSeq);
	}
	
//	@GetMapping("/insertReview/{username}")
//	public ModelAndView getUserAni(@PathVariable String username) {
//		ModelAndView mv = new ModelAndView();
//		storeService.getUserAni(username);
//	}
	
	
	@PostMapping("/insertReview/{storeSeq}")
	public void insertReview(@PathVariable int storeSeq) {
		
	}
	

	@GetMapping("/deleteStore/{storeSeq}")
	public void deleteStore(@PathVariable int storeSeq, HttpServletResponse response) throws IOException {
		storeService.deleteStore(storeSeq);

		response.sendRedirect("/store/getStoreList");
	}

	@PostMapping("/updateStore")
	public void updateStore(Store store, HttpServletResponse response, HttpServletRequest request,
			MultipartHttpServletRequest multipartServletRequest) throws IOException {
		storeService.updateStore(store);

		PicUtils picUtils = new PicUtils();

		List<StoreFile> fileList = picUtils.parseFileInfo(store.getStoreSeq(), request, multipartServletRequest);

		// boardService.addBoardFileList(fileList, fileSeq);

		storeService.insertStoreFileList(fileList);

		response.sendRedirect("/store/getStoreList");
	}
	
	

}
