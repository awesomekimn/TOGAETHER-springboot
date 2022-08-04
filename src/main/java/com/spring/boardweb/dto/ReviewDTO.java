package com.spring.boardweb.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ReviewDTO {
	private String userId;
	private int storeSeq;
	private int reviewSeq;
	private String reviewText;
	private Date reviewRegdate;
	private Date reviewMdfdate;
	private int reviewRate;
	private int userAni;
}
