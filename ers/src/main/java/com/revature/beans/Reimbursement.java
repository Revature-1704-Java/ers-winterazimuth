package com.revature.beans;

public class Reimbursement {
	public Reimbursement(int id, int requestedBy, int amount, String description, int status) {
		super();
		this.id = id;
		this.requestedBy = requestedBy;
		this.amount = amount;
		this.description = description;
		this.status = status;
	}
	int id;
	int requestedBy;
	int amount;
	String description;
	int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(int requestedBy) {
		this.requestedBy = requestedBy;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String toString() {
		String value = "";
		if (status == 2)
			value = "(" + id + ") (" + requestedBy + ") PENDING  " + amount + " \"" + description + "\"";
		if (status == 1)
			value = "(" + id + ") (" + requestedBy + ") APPROVED " + amount + " \"" + description + "\"";
		if (status == 0)
			value = "(" + id + ") (" + requestedBy + ") DENIED   " + amount + " \"" + description + "\"";
		return value;
	}
}
