package couponSystem.db.dao;

import java.util.Collection;

import couponSystem.db.javaBeans.Coupon;
import couponSystem.db.javaBeans.Coupon.CouponType;

public interface CouponDAO {

	/**
	 * This method creates a new coupon in the system.
	 */
	public void createCoupon(Coupon coupon) throws Exception;

	/**
	 * This method removes a coupon from the system.
	 */
	public void removeCoupon(Coupon coupon) throws Exception;

	/**
	 * This method updates an existing coupon.
	 */
	public void updateCoupon(Coupon coupon) throws Exception;

	/**
	 * This method returns a coupon from the system using a given id.
	 */
	public Coupon getCoupon(long id) throws Exception;

	/**
	 * This method returns a collection of all the coupons from the system.
	 */
	public Collection<Coupon> getAllCoupons() throws Exception;

	/**
	 * This method returns a collection of all the coupons of a given type.
	 */
	public Collection<Coupon> getCouponByType(CouponType type) throws Exception;

}
