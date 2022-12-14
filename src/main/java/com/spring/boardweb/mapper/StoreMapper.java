package com.spring.boardweb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.spring.boardweb.dto.StoreDTO;
import com.spring.boardweb.dto.StoreFileDTO;

@Mapper
public interface StoreMapper {
	@Select("SELECT IFNULL(MAX(STORE_SEQ), 0) + 1 FROM TO_STORE")
	int getNextStoreSeq();

	@Update("UPDATE TO_STORE SET STORE_SEQ = STORE_SEQ - 1 WHERE STORE_SEQ > #{storeSeq}")
	void updateStoreSeq(int storeSeq);
	
	@Delete("DELETE FROM TO_STORE_FILE WHERE FILE_SEQ > 1 AND STORE_SEQ = #{storeSeq}")
	void updateStoreFileSeq(int storeSeq);

	@Select("SELECT * FROM TO_STORE_FILE WHERE STORE_SEQ = #{storeSeq} AND FILE_SEQ = 1")
	List<StoreFileDTO> selectStoreFileList(int storeSeq);

	@Select("SELECT A.*,\n" + "       B.FILE_NAME\n" + "	FROM TO_store A,\n" + "         TO_STORE_FILE B\n"
			+ "    WHERE A.CATEGORY_NM = 'hotel'\n" + "      AND A.STORE_SEQ = (\n"
			+ "						  SELECT MIN(A.STORE_SEQ)\n" + "							FROM TO_STORE A\n"
			+ "                            WHERE A.category_nm = 'hotel'\n" + "                      )\n"
			+ "	  AND A.STORE_SEQ = B.STORE_SEQ\n" + "      AND B.FILE_SEQ = 1\n" + "UNION ALL\n" + "SELECT A.*,\n"
			+ "       B.FILE_NAME\n" + "	FROM TO_store A,\n" + "         TO_STORE_FILE B\n"
			+ "    WHERE A.CATEGORY_NM = 'meal'\n" + "      AND A.STORE_SEQ = (\n"
			+ "						  SELECT MIN(A.STORE_SEQ)\n" + "							FROM TO_STORE A\n"
			+ "                            WHERE A.category_nm = 'meal'\n" + "                      )\n"
			+ "	  AND A.STORE_SEQ = B.STORE_SEQ\n" + "      AND B.FILE_SEQ = 1\n" + "UNION ALL\n" + "SELECT A.*,\n"
			+ "       B.FILE_NAME\n" + "	FROM TO_store A,\n" + "         TO_STORE_FILE B\n"
			+ "    WHERE A.CATEGORY_NM = 'cafe'\n" + "      AND A.STORE_SEQ = (\n"
			+ "						  SELECT MIN(A.STORE_SEQ)\n" + "							FROM TO_STORE A\n"
			+ "                            WHERE A.category_nm = 'cafe'\n" + "                      )\n"
			+ "	  AND A.STORE_SEQ = B.STORE_SEQ\n" + "      AND B.FILE_SEQ = 1")
	List<StoreDTO> getCarousel();
}
