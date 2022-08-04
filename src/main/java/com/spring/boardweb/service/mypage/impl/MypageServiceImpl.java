package com.spring.boardweb.service.mypage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boardweb.entity.Review;
import com.spring.boardweb.repository.ReviewRepository;
import com.spring.boardweb.service.mypage.MypageService;

@Service
public class MypageServiceImpl implements MypageService {
	@Autowired
	ReviewRepository reviewRepository;

	@Override
	public List<Review> getMyReviewList(String userId) {
		return reviewRepository.findByUserIdOrderByReviewSeqDesc(userId);
	}

}
