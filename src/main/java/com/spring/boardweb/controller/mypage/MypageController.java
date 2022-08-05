package com.spring.boardweb.controller.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
		
		User user = userService.idCheck(userId);
		
		List<Review> reviewList = mypageService.getMyReviewList(userId);
		
		for(Review review : reviewList) {
			review.setFileList(storeService.getStoreFileList(review.getStore().getStoreSeq()));
		}
		
		if(loginUser == null) {
			mv.setViewName("user/login.html");
		} else {
			mv.setViewName("mypage/mypage.html");
		}
		
		mv.addObject(user);
		mv.addObject(reviewList);
		
		return mv;
	}
	
	@GetMapping("/{userId}/showAllReview")
	public ModelAndView showAllReview(@PathVariable String userId, @AuthenticationPrincipal CustomUserDetails loginUser) {
		ModelAndView mv = new ModelAndView();
		
		User user = userService.idCheck(userId);
		
		List<Review> reviewList = mypageService.getMyReviewList(userId);
		
		if(loginUser == null) {
			mv.setViewName("user/login.html");
		} else {
			mv.setViewName("mypage/" + userId + "/likeList.html");
		}
		
		mv.addObject(user);
		mv.addObject(reviewList);
		
		return mv;
	}
}
