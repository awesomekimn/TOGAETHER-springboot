package com.spring.boardweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.boardweb.entity.StoreLike;

public interface StoreLikeRepository extends JpaRepository<StoreLike, Integer>{
	
//	StoreLike findByUserIdAndStoreSeq(String userId, int storeSeq);
}
