package com.spring.boardweb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.spring.boardweb.dto.StoreFileDTO;

@Mapper
public interface StoreMapper {
	@Select("SELECT IFNULL(MAX(STORE_SEQ), 0) + 1 FROM STORE")
	int getNextStoreSeq();
	
	@Update("UPDATE STORE SET STORE_SEQ = STORE_SEQ - 1 WHERE STORE_SEQ > #{storeSeq}")
	void updateStoreSeq(int storeSeq);
	
	@Select("SELECT * FROM STORE_FILE WHERE STORE_SEQ = #{storeSeq}")
	List<StoreFileDTO> selectStoreFileList(int storeSeq);
}
