package com.metro.bean;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
	private int id;
	private int cardId;
	private int sourceId;
	private int destinationId;
	private Timestamp swipeInTime;
	private Timestamp swipeOutTime;
	private double fare;
}
