package com.spring.boardweb.service.mypage;

import java.util.List;

import com.spring.boardweb.entity.Review;

public interface MypageService {
	List<Review> getMyReviewList(String userId);
}
