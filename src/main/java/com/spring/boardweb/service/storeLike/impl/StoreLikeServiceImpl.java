package com.spring.boardweb.service.storeLike.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boardweb.entity.Store;
import com.spring.boardweb.entity.StoreLike;
import com.spring.boardweb.entity.User;
import com.spring.boardweb.mapper.StoreLikeMapper;
import com.spring.boardweb.repository.StoreLikeRepository;
import com.spring.boardweb.service.storeLike.StoreLikeService;

@Service
public class StoreLikeServiceImpl implements StoreLikeService {
	@Autowired
	StoreLikeRepository storeLikeRepository;
	
	@Autowired
	StoreLikeMapper storeLikeMapper;
	
	@Override
	public StoreLike likeCheck(String userId, int storeSeq) {
		StoreLike likeCheck = new StoreLike();
		
		User user = new User();
		Store store = new Store();
		
		if(storeLikeMapper.findStoreLike(userId, storeSeq) == null) {
			likeCheck.setLikeSeq(storeLikeMapper.getNextLikeSeq());
			storeLikeRepository.save(likeCheck);
		}
		
		return likeCheck;
		
	}

//	@Override
//	public void addStoreLike(StoreLike storeLike) {
//		storeLikeRepository.
//	}

}
