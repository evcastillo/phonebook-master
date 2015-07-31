package com.lotus.phonebookapp.beans;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Contact {
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	private long contactId;
	private String contactName;
	private String contactNumber;
	private Date contactBirthday = null;
	private boolean isVIP;
	private long companyId;
	@JsonIgnore
	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Date getContactBirthday() {
		return this.contactBirthday;
	}

	public void setContactBirthday(Date contactBirthday) {
		this.contactBirthday = contactBirthday;
	}

	public boolean isVIP() {
		return isVIP;
	}

	public void setVIP(boolean isVIP) {
		this.isVIP = isVIP;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	private Contact(Builder builder) {
		this.contactId = builder.contactId;
		this.contactName = builder.contactName;
		this.contactNumber = builder.contactNumber;
		this.contactBirthday = builder.contactBirthday;
		this.isVIP = builder.isVIP;
		this.companyId = builder.companyId;
	}

	public static class Builder {

		private long contactId;
		private Date contactBirthday = null;

		private String contactName;
		private String contactNumber;
		private boolean isVIP = false;
		private long companyId;

		public Builder(String contactName, String contactNumber, boolean isVIP,
				long companyId) {
			this.contactName = contactName;
			this.contactNumber = contactNumber;
			this.isVIP = isVIP;
			this.companyId = companyId;
		}

		public Builder birthday(Date contactBirthday) {
			
			this.contactBirthday = contactBirthday;
			return this;

		}

		public Builder id(long contactId) {
			this.contactId = contactId;
			return this;
		}

		public Contact build() {
			return new Contact(this);

		}
	}

	
	@Override
	public String toString() {
		String bday = df.format(contactBirthday);
		return contactName + ", " + contactNumber + ", " + bday
				+ ", " + isVIP;
	}

}
