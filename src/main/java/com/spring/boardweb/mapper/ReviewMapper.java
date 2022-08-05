package com.spring.boardweb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReviewMapper {
	@Select("SELECT IFNULL(MAX(REVIEW_SEQ), 0) + 1 FROM TO_REVIEW")
	int getNextReviewSeq();

	@Update("UPDATE TO_REVIEW SET REVIEW_SEQ = REVIEW_SEQ - 1 WHERE REVIEW_SEQ > #{reviewSeq}")
	void updateReviewSeq(int reviewSeq);

	@Select("SELECT USER_ANI FROM USER WHERE USER_ID = #{userId}")
	String getUserAni(String userId);

	@Select("SELECT round(AVG(REVIEW_RATE), 1)\n"
			+ "	FROM TO_REVIEW\n"
			+ "    WHERE STORE_SEQ = #{storeSeq}")
	String getReviewAvg(int storeSeq);
	
	@Select("SELECT COUNT(*) FROM TO_REVIEW WHERE STORE_SEQ = #{storeSeq}")
	int getReviewCnt(int storeSeq);
	
	@Select("SELECT COUNT(*) FROM TO_REVIEW WHERE STORE_SEQ=#{storeSeq} AND USER_ID = #{userId}")
	int getReviewCount(@Param("storeSeq") int storeSeq, @Param("userId") String userId);
}
