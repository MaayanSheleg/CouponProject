package com.mbms.beans;

public class Customer_Coupon {

	private long custId;
	private long couponId;
	
	public Customer_Coupon() {
	}
	
	public Customer_Coupon(long custId, long couponId ) {
		super();
		
		setCustId(custId);
		setCouponId(couponId);
		
		
//		this.custId=custId;
//		this.couponId=couponId;
	}
	
	public long getCustId() {
		return custId;
	}
	public void setCustId(long custId) {
		this.custId = custId;
	}
	public long getCouponId() {
		return couponId;
	}
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	
	@Override
	public String toString() {
		return "Customer_Coupon [custId=" + custId + ", couponId=" + couponId + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (couponId ^ (couponId >>> 32));
		result = prime * result + (int) (custId ^ (custId >>> 32));
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
		Customer_Coupon other = (Customer_Coupon) obj;
		if (couponId != other.couponId)
			return false;
		if (custId != other.custId)
			return false;
		return true;
	}
}
