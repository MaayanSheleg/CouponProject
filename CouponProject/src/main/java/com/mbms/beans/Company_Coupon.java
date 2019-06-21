package com.mbms.beans;

/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/

public class Company_Coupon {

	private long companyId;
	private long couponId;

	public Company_Coupon() {
	}

	public Company_Coupon(long companyId, long couponId) {
		super();
		setCompanyId(companyId);
		setCouponId(couponId);
		
//		this.companyId = companyId;
//		this.couponId = couponId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	@Override
	public String toString() {
		return "Company_Coupon [companyId=" + companyId + ", couponId=" + couponId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (companyId ^ (companyId >>> 32));
		result = prime * result + (int) (couponId ^ (couponId >>> 32));
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
		Company_Coupon other = (Company_Coupon) obj;
		if (companyId != other.companyId)
			return false;
		if (couponId != other.couponId)
			return false;
		return true;
	}
}