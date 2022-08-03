package com.spring.boardweb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface ReviewMapper {
	@Select("SELECT IFNULL(MAX(REVIEW_SEQ), 0) + 1 FROM REVIEW")
	int getNextReviewSeq();
	
	@Update("UPDATE REVIEW SET REVIEW_SEQ = REVIEW_SEQ - 1 WHERE REVIEW_SEQ > #{reviewSeq}")
	void updateReviewSeq(int reviewSeq);

	@Select("SELECT USER_ANI FROM USER WHERE USER_ID = #{userId}")
	 String getUserAni(String userId); 

}
