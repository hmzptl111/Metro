package com.metro.bean;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
	private int id;
	private int cardId;
	private Metro source;
	private Metro destination;
	private LocalDate swipeInDate;
	private LocalTime swipeInTime;
	private LocalDate swipeOutDate;
	private LocalTime swipeOutTime;
}
