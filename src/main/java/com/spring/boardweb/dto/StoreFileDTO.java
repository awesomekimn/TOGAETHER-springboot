package com.spring.boardweb.dto;

import lombok.Data;

@Data
public class StoreFileDTO {
	private int storeSeq;
	private int fileSeq;
	private String originalFileName;
	private String fileName;
	private String filePath;
}
