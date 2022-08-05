package com.spring.boardweb.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.boardweb.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	//By뒤에는 Entity의 컬럼명
	//And Or로 Where 절에 붙을 조건절 설정
	//Containing 키워드로 like '%%' 구문 설정
	
//	Page<Review> findByReviewSeqContaining(int reviewseq, Pageable pageable);
	List<Review> findByStoreStoreSeqOrderByReviewSeqDesc(int storeSeq);
	
	List<Review> findByUserIdOrderByReviewSeqDesc(String userId);
	
	@Query(value="select ifnull(max(r.review_seq), 0) + 1 from TO_REVIEW r where r.store_seq = :storeSeq", nativeQuery=true)
	int selectNextReviewSeqByStoreSeq(@Param("storeSeq") int storeSeq);
}
