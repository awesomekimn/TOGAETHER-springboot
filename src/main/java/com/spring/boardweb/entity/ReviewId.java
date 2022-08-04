package com.spring.boardweb.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReviewId implements Serializable{
	private int store;
	private int reviewSeq;
}
