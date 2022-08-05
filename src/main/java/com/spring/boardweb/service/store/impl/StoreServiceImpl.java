package com.spring.boardweb.service.store.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.boardweb.dto.StoreDTO;
import com.spring.boardweb.dto.StoreFileDTO;
import com.spring.boardweb.entity.BoardFile;
import com.spring.boardweb.entity.Review;
import com.spring.boardweb.entity.Store;
import com.spring.boardweb.entity.StoreFile;
import com.spring.boardweb.mapper.ReviewMapper;
import com.spring.boardweb.mapper.StoreMapper;
import com.spring.boardweb.repository.ReviewRepository;
import com.spring.boardweb.repository.StoreFileRepository;
import com.spring.boardweb.repository.StoreRepository;
import com.spring.boardweb.service.store.StoreService;

@Service
public class StoreServiceImpl implements StoreService {
	@Autowired
	StoreRepository storeRepository;

	@Autowired
	StoreFileRepository storeFileRepository;

	@Autowired
	StoreMapper storeMapper;
	
	@Autowired
	ReviewMapper reviewMapper;
	
	@Autowired
	ReviewRepository reviewRepository;

	@Override
	public Page<Store> getStoreList(String categoryNm, Pageable pageable) {
		if (categoryNm.equals("hotel")) {
			return storeRepository.findByCategoryNm("hotel", pageable);
		} else if (categoryNm.equals("meal")) {
			return storeRepository.findByCategoryNm("meal", pageable);
		} else {
			return storeRepository.findByCategoryNm("cafe", pageable);
		}
//		return storeRepository.findAll(pageable);
	}

	@Override
	public int insertStore(Store store) {
		int storeSeq = storeMapper.getNextStoreSeq();
		store.setStoreSeq(storeSeq);
		storeRepository.save(store);
		storeRepository.flush();
		return store.getStoreSeq();
	}

	@Override
	public Store getStore(int storeSeq) {
		return storeRepository.findById(storeSeq).get();
	}

	@Override
	public void deleteStore(int storeSeq) {
		storeRepository.deleteById(storeSeq);

		storeMapper.updateStoreSeq(storeSeq);
	}

	@Override
	public void updateStore(Store store) {
		storeMapper.updateStoreFileSeq(store.getStoreSeq());
		storeRepository.save(store);
		
	}

	@Override
	public void insertStoreFileList(List<StoreFile> fileList) {
		for (StoreFile storeFile : fileList) {
			storeFile.setFileSeq(
					storeFileRepository.selectNextFileSeqByStoreStoreSeq(storeFile.getStore().getStoreSeq()));
			storeFileRepository.save(storeFile);
		}
	}

	@Override
	public List<StoreFileDTO> getStoreFileList(int storeSeq) {
		List<StoreFileDTO> fileList = storeMapper.selectStoreFileList(storeSeq);
//		System.out.println(fileList);
//		if (fileList == null || fileList.isEmpty()) {
//			System.out.println(fileList);
//			return null;
//		} else {
		return fileList;
//		}
	}

	@Override
	public void deleteStoreFile(int storeSeq, int fileSeq) {
		Store store = new Store();
		store.setStoreSeq(storeSeq);
		
		StoreFile storeFile = new StoreFile();
		storeFile.setStore(store);
		storeFile.setFileSeq(fileSeq);

		storeFileRepository.delete(storeFile);
	}
	
	@Override
	public List<StoreDTO> getCarousel() {
		return storeMapper.getCarousel();
	}
	
	@Override
	public String getUserAni(String userId) {
		return reviewMapper.getUserAni(userId);
	}

	@Override
	public void insertReview(Review review) {
		review.setReviewSeq(reviewMapper.getNextReviewSeq());
		
		int storeSeq = review.getStore().getStoreSeq();
		String userId = review.getUserId();
		
		int reviewCount = reviewMapper.getReviewCount(storeSeq, userId);
		
		if(reviewCount == 0)
			reviewRepository.save(review);
	}

	@Override
	public String getReviewAvg(int storeSeq) {
		return reviewMapper.getReviewAvg(storeSeq);
	}

	@Override
	public List<Review> getreviewList(int storeSeq) {
		return reviewRepository.findByStoreStoreSeqOrderByReviewSeqDesc(storeSeq);
	}

	@Override
	public int getReviewCnt(int storeSeq) {
		return reviewMapper.getReviewCnt(storeSeq);
	}
	
}
