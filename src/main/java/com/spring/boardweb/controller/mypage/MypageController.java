package com.spring.boardweb.controller.mypage;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boardweb.entity.CustomUserDetails;
import com.spring.boardweb.entity.Review;
import com.spring.boardweb.entity.Store;
import com.spring.boardweb.entity.User;
import com.spring.boardweb.service.mypage.MypageService;
import com.spring.boardweb.service.store.StoreService;
import com.spring.boardweb.service.user.UserService;

@RestController
@RequestMapping("/mypage")
public class MypageController {
	@Autowired
	MypageService mypageService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	StoreService storeService;
	
	@GetMapping("/{userId}")
	public ModelAndView getMypage(@PathVariable String userId, @AuthenticationPrincipal CustomUserDetails loginUser) {
		ModelAndView mv = new ModelAndView();
		
		//User user = userService.idCheck(userId);
		
		List<Review> reviewList = mypageService.getMyReviewList(userId);
		
		for(Review review : reviewList) {
			review.setFileList(storeService.getStoreFileList(review.getStore().getStoreSeq()));
		}
		
		if(loginUser == null) {
			mv.setViewName("user/login.html");
		} else {
			mv.setViewName("mypage/mypage.html");
		}
		
		//mv.addObject(user);
		mv.addObject(reviewList);
		
		return mv;
	}
	
	@GetMapping("/reviewList/{userId}")
	public ModelAndView showAllReview(@PathVariable String userId, @AuthenticationPrincipal CustomUserDetails loginUser) {
		ModelAndView mv = new ModelAndView();
		
		User user = userService.idCheck(userId);
		
		List<Review> reviewList = mypageService.getMyReviewList(userId);
		
		for(Review review : reviewList) {
			review.setFileList(storeService.getStoreFileList(review.getStore().getStoreSeq()));
		}
		
		if(loginUser == null) {
			mv.setViewName("user/login.html");
		} else {
			mv.setViewName("mypage/reviewList.html");
		}
		
		mv.addObject(user);
		mv.addObject(reviewList);
		
		return mv;
	}
	
	@GetMapping("deleteReview/{reviewSeq}")
	public void deleteReview(@PathVariable int reviewSeq, HttpServletResponse response,
			@AuthenticationPrincipal CustomUserDetails loginUser, HttpServletRequest request) throws IOException {
		mypageService.deleteReview(reviewSeq);
		
		String referer = (String)request.getHeader("REFERER");
		
		if(referer.contains("reviewList")) {			
			response.sendRedirect("/mypage/reviewList/" + loginUser.getUsername());
		} else {
			response.sendRedirect("/mypage/" + loginUser.getUsername());
		}
		//reviewList에서 deleteReview했을때는??
	}

	@PostMapping("/updateReview")
	public void updateReview(Review review, @RequestParam int storeSeq, HttpServletResponse response,
			@AuthenticationPrincipal CustomUserDetails loginUser)throws IOException  {
		Store store = new Store();
		store.setStoreSeq(storeSeq);
		
		review.setUserId(loginUser.getUsername());
		review.setStore(store);
		
		System.out.println(review.getStore().getStoreSeq() + "/////////////////////////////////////////////////");
		mypageService.updateReview(review);
		response.sendRedirect("/mypage/" + loginUser.getUsername());
	}
	
	
	
	
}
