package com.spring.boardweb.dto;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class StoreDTO {
	
	private int storeSeq;
	private String storeNm;
	private String storeIntro;
	private String storeAddress;
	private String storeLink;
	private String storeTel;
	private String storeTime;
	private String storeContent;
	private String internet;
	private String petpad;
	private String parking;
	private String parkingFree;
	private String convenience;
	private String swimming;
	private String smoking;
	private String storePolicy;
	private String storeMenu;
	private String storeEtc;
	private Date storeRegdate;
	private String storeWriter;
	private String categoryNm;
	private int fileSeq;
	private String fileName;
	private String userAni;
	private double reviewAvg;
}
