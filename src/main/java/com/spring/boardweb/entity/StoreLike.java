package com.spring.boardweb.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="TO_LIKE")
@Data
//다중 pk 설정
//다중 pk가 모여있는 객체를 만들어서 연결해줌
@IdClass(StoreLikeId.class)
public class StoreLike {
	@Id
	//foreign key 설정
	//매핑 관계
	//일대일 매핑일 때
	//@OneToOne
	//다대일 매핑일 때
	//@ManyToOne
	//일대다 매핑일 때
	//@OneToMany
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@Id
	@ManyToOne
	@JoinColumn(name="STORE_SEQ")
	private Store store;
	
//	@Id
//	private int likeSeq;
	
}
