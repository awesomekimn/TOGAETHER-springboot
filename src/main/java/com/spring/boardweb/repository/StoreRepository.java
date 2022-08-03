package com.spring.boardweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boardweb.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Integer>{
	//By뒤에는 Entity의 컬럼명
	//And Or로 Where 절에 붙을 조건절 설정
	//Containing 키워드로 like '%%' 구문 설정
	
	Page<Store> findByStoreNmContaining(String searchKeyword, Pageable pageable);
	
	Page<Store> findByCategoryNm(String category, Pageable pageable);
	
	//Store findOneByStore(Store store);
	
}
