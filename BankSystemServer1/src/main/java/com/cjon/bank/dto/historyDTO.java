package com.cjon.bank.dto;

public class historyDTO {
	
	private String checkId;
	private String kind;
	private int checkBalance;
	
	public historyDTO() {
		
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public int getCheckBalance() {
		return checkBalance;
	}

	public void setCheckBalance(int checkBalance) {
		this.checkBalance = checkBalance;
	}
	
	

}
