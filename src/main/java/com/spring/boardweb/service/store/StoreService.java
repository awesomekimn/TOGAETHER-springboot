package com.spring.boardweb.service.store;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.boardweb.dto.StoreDTO;
import com.spring.boardweb.dto.StoreFileDTO;
import com.spring.boardweb.entity.Review;
import com.spring.boardweb.entity.Store;
import com.spring.boardweb.entity.StoreFile;

public interface StoreService {
	Page<Store> getStoreList(String categoryNm, Pageable pageable);

	int insertStore(Store store);

	Store getStore(int storeSeq);

	void deleteStore(int storeSeq);

	void updateStore(Store store);

	void insertStoreFileList(List<StoreFile> fileList);

	List<StoreFileDTO> getStoreFileList(int storeSeq);

	void deleteStoreFile(int storeSeq, int fileSeq);

	List<StoreDTO> getCarousel();

	String getUserAni(String userId);

	void insertReview(Review review);

	String getReviewAvg(int storeSeq);
	
	List<Review> getreviewList(int storeSeq);
	
	int getReviewCnt(int storeSeq);
	
}