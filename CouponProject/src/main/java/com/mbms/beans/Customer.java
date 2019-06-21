package com.mbms.beans;

public class Customer {

	private long id;
	private String custName;
	private String password;
	
	//empty cTor for customer
	public Customer() {
	}
	
	//full cTor for the customer 
	public Customer (long ID, String cust_name, String password ){
		super();
		
		setId(ID);
		setCust_name(cust_name);
		setPassword(password);
		
//		this.id = ID;
//		this.custName = cust_name;
//		this.password = password;
	}
	
	public Customer (String cust_name, String password ){
		this.custName = cust_name;
		this.password = password;	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCust_name() {
		return custName;
	}

	public void setCust_name(String cust_name) {
		this.custName = cust_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custName == null) ? 0 : custName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (custName == null) {
			if (other.custName != null)
				return false;
		} else if (!custName.equals(other.custName))
			return false;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + "]";
	}		
}
