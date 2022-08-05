package com.spring.boardweb.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.spring.boardweb.entity.StoreLike;

@Mapper
public interface StoreLikeMapper {
	@Select("SELECT IFNULL(MAX(LIKE_SEQ), 0) + 1 FROM TO_LIKE")
	int getNextLikeSeq();
	
	@Select("SELECT * FROM TO_LIKE WHERE USER_ID = #{userId} AND STORE_SEQ = #{storeSeq}")
	StoreLike findStoreLike(String userId, int storeSeq);
	
	@Insert("INSERT INTO (USER_ID, STORE_SEQ) VALUES (#{userId}, #{storeSeq})")
	void addLike(String userId, int storeSeq);

//	@Update("UPDATE TO_STORE SET STORE_SEQ = STORE_SEQ - 1 WHERE STORE_SEQ > #{storeSeq}")
//	void updateStoreSeq(int storeSeq);
//	
//	@Delete("DELETE FROM TO_STORE_FILE WHERE FILE_SEQ > 1 AND STORE_SEQ = #{storeSeq}")
//	void updateStoreFileSeq(int storeSeq);
//
//	@Select("SELECT * FROM TO_STORE_FILE WHERE STORE_SEQ = #{storeSeq} AND FILE_SEQ = 1")
//	List<StoreFileDTO> selectStoreFileList(int storeSeq);

}
