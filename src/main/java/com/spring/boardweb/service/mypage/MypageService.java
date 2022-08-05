package com.spring.boardweb.service.mypage;

import java.util.List;

import com.spring.boardweb.entity.Review;
import com.spring.boardweb.entity.Store;

public interface MypageService {
	List<Review> getMyReviewList(String userId);
	
	void deleteReview(int reviewSeq);
	
	void updateReview(Review review);
}
