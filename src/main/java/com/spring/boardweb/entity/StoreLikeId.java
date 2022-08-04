package com.spring.boardweb.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class StoreLikeId implements Serializable{
	private String user;
	private int likeSeq;
}
