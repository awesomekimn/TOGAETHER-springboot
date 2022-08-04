package com.spring.boardweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.boardweb.entity.StoreFile;
import com.spring.boardweb.entity.StoreLike;
import com.spring.boardweb.entity.StoreLikeId;

public interface StoreLikeRepository extends JpaRepository<StoreLike, StoreLikeId>{
	//구현가능한 메소드는 find, read, query, count, get
	@Query(value="select a.store_seq, a.file_seq, a.file_name, a.file_path, a.original_file_name from TO_store_file a where a.store_seq = :storeSeq", nativeQuery = true)
	List<StoreLike> selectByStoreSeq(@Param("storeSeq") int storeSeq);
	
	//@Query : 원하는 쿼리를 작성하여 사용할 수 있다. nativeQuery옵션을 true로 설정하면
	//Repository의 메소드 명도 원하는 대로 지정가능
	//value옵션이 사용할 쿼리 작성
	@Query(value="select ifnull(max(f.file_seq), 0) + 1 from TO_store_file f where f.store_seq = :storeSeq", nativeQuery=true)
	int selectNextFileSeqByStoreStoreSeq(@Param("storeSeq") int storeSeq);
}
