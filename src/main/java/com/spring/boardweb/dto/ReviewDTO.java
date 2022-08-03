package com.spring.boardweb.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReviewDTO {
	
	private int reviewSeq;
	private String userId;
	private int storeSeq;
	private String reviewText;
	private LocalDateTime storeRegdate = LocalDateTime.now();
	private LocalDateTime storeMdfdate = LocalDateTime.now();
	private int userAni;
	private int reviewRate;
}
