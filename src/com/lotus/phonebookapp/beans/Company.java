package com.lotus.phonebookapp.beans;

public class Company {
	private long companyId;
	private String companyName;
	private String companyCode;
	private String companyDescription;

	private Company(Builder build) {
		this.companyId = build.companyId;
		this.companyName = build.companyName;
		this.companyCode = build.companyCode;
		this.companyDescription = build.companyDescription;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public static class Builder {
		private long companyId;
		private String companyName;
		private String companyCode;
		private String companyDescription;

		public Builder(long companyId, String companyName, String companyCode,
				String companyDescription) {
			this.companyId = companyId;
			this.companyName = companyName;
			this.companyCode = companyCode;
			this.companyDescription = companyDescription;
		}

		public Company build() {
			return new Company(this);

		}

	}

	@Override
	public String toString() {
		return " "+companyName + ", " + companyCode + ", " + companyDescription;
	}

}
