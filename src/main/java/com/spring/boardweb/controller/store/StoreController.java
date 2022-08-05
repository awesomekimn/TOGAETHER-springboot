
package com.spring.boardweb.controller.store;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boardweb.commons.PicUtils;
import com.spring.boardweb.entity.CustomUserDetails;
import com.spring.boardweb.entity.Review;
import com.spring.boardweb.entity.Store;
import com.spring.boardweb.entity.StoreFile;
import com.spring.boardweb.entity.StoreLike;
import com.spring.boardweb.entity.User;
import com.spring.boardweb.service.store.StoreService;
import com.spring.boardweb.service.storeLike.StoreLikeService;
import com.spring.boardweb.service.user.UserService;

@RestController
@RequestMapping("/store")
public class StoreController {
	@Autowired
	StoreService storeService;

	@Autowired
	UserService userService;
	
	@Autowired
	StoreLikeService storeLikeService;

	@GetMapping("/getStoreList/{categoryNm}")
	public ModelAndView getStoreListView(@PathVariable String categoryNm,
			@PageableDefault(page = 0, size = 12) Pageable pageable, @AuthenticationPrincipal CustomUserDetails loginUser) {
		ModelAndView mv = new ModelAndView();
		
		User user = new User();
		if (loginUser != null) {
			user = userService.idCheck(loginUser.getUsername());
		} else {
			user.setUserId("NotFound");
		}

		Page<Store> storeList = storeService.getStoreList(categoryNm, pageable);

		for (Store content : storeList) {
			content.setFileList(storeService.getStoreFileList(content.getStoreSeq()));
			content.setReviewCnt(storeService.getReviewCnt(content.getStoreSeq()));
		}

		mv.setViewName("store/getStoreList.html");
		mv.addObject("storeList", storeList);
		mv.addObject("category", categoryNm);
		mv.addObject(user);

		return mv;
	}
	
	@PostMapping("/addStoreLike")
	public String addStoreLike(@RequestParam String userId, @RequestParam int storeSeq) {
		
		User user = new User();
		user.setUserId(userId);
		
		StoreLike likeCheck = storeLikeService.likeCheck(user.getUserId(), storeSeq);
		
		if(likeCheck == null) {
			return "ok";
		} else {
			return "fail";
		}
		
	}

	@GetMapping("/storeDetail/{storeSeq}")
	public ModelAndView getStoreView(@PathVariable int storeSeq) {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("store/storeDetail.html");

		Store store = storeService.getStore(storeSeq);

		store.setFileList(storeService.getStoreFileList(store.getStoreSeq()));

		if (storeService.getReviewAvg(storeSeq) == null) {
			store.setReviewAvg(0);
		} else {
			store.setReviewAvg(Double.parseDouble(storeService.getReviewAvg(storeSeq)));
		}

		List<Review> reviewList = storeService.getreviewList(storeSeq);
		mv.addObject("store", store);
		mv.addObject(reviewList);

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

	@PostMapping("/insertReview")
	public void insertReview(HttpServletResponse response, Review review, @RequestParam int storeSeq,
			@AuthenticationPrincipal CustomUserDetails loginUser) throws IOException {
		Store store = new Store();
		store.setStoreSeq(storeSeq);

		review.setStore(store);

		if (loginUser != null) {
			review.setUserAni(storeService.getUserAni(loginUser.getUsername()));
		}
		System.out.println(review.getUserAni());

		storeService.insertReview(review);

		response.sendRedirect("/store/storeDetail/" + review.getStore().getStoreSeq());
	}

	@GetMapping("/deleteStore")
	public void deleteStore(@RequestParam int storeSeq, @RequestParam String categoryNm, HttpServletResponse response)
			throws IOException {

		storeService.deleteStore(storeSeq);

		response.sendRedirect("/store/getStoreList/" + categoryNm);
	}

	@PostMapping("/updateStore")
	public void updateStore(Store store, HttpServletResponse response, HttpServletRequest request,
			MultipartHttpServletRequest multipartServletRequest) throws IOException {
		storeService.updateStore(store);

		PicUtils picUtils = new PicUtils();

		List<StoreFile> fileList = picUtils.parseFileInfo(store.getStoreSeq(), request, multipartServletRequest);

		storeService.insertStoreFileList(fileList);

		response.sendRedirect("/store/storeDetail/" + store.getStoreSeq());
	}

	@GetMapping("/updateStore/{storeSeq}")
	public ModelAndView getupdateStoreView(@PathVariable int storeSeq) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("store/editStore.html");
		Store store = storeService.getStore(storeSeq);

		store.setFileList(storeService.getStoreFileList(store.getStoreSeq()));
		store.setReviewAvg(storeSeq);

		System.out.println(store.toString());
		System.out.println(store.getParking());
		mv.addObject("store", store);
		return mv;
	}
}
