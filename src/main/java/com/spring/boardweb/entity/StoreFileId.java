package com.spring.boardweb.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class StoreFileId implements Serializable{
	private int store;
	private int fileSeq;
}
