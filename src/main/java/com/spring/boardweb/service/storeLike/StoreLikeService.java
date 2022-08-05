package com.spring.boardweb.service.storeLike;

import com.spring.boardweb.entity.StoreLike;

public interface StoreLikeService {
	StoreLike likeCheck(String userId, int storeSeq);
	
}
