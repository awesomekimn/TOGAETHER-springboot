package com.spring.boardweb.service.mypage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boardweb.entity.Review;
import com.spring.boardweb.mapper.ReviewMapper;
import com.spring.boardweb.repository.ReviewRepository;
import com.spring.boardweb.service.mypage.MypageService;

@Service
public class MypageServiceImpl implements MypageService {
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	ReviewMapper reviewMapper;
	

	@Override
	public List<Review> getMyReviewList(String userId) {
		return reviewRepository.findByUserIdOrderByReviewSeqDesc(userId);
	}

	@Override
	public void deleteReview(int reviewSeq) {		
		reviewRepository.deleteById(reviewSeq);
		reviewMapper.updateReviewSeq(reviewSeq);
	}

	@Override
	public void updateReview(Review review) {
		reviewRepository.save(review);
		reviewMapper.updateReviewSeq(review.getReviewSeq());
	}
	

}
