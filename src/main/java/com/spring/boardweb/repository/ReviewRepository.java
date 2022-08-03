package com.spring.boardweb.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boardweb.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	//By뒤에는 Entity의 컬럼명
	//And Or로 Where 절에 붙을 조건절 설정
	//Containing 키워드로 like '%%' 구문 설정
	
	Page<Review> findByReviewSeqContaining(int reviewseq, Pageable pageable);
	
}
