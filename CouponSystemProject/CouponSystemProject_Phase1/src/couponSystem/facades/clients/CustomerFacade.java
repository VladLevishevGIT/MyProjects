package couponSystem.facades.clients;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import couponSystem.db.dao.dbdao.CouponDBDAO;
import couponSystem.db.dao.dbdao.CustomerDBDAO;
import couponSystem.db.javaBeans.Coupon;
import couponSystem.db.javaBeans.Coupon.CouponType;
import couponSystem.db.javaBeans.Customer;
import couponSystem.exceptions.dao.CouponDAOException;
import couponSystem.exceptions.dao.CustomerDAOException;
import couponSystem.exceptions.facade.CustomerFacadeException;
import couponSystem.facades.CouponClientFacade;

public class CustomerFacade extends CouponClientFacade {

	// Attributes

	private long custId;

	// CTOR

	public CustomerFacade() {

	}

	// Methods

	/**
	 * This method receives a coupon and checks if the customer can purchase it.
	 * Checking if the customer already purchased this coupon, if the coupon is
	 * still in stock, and if the time of purchase is not past the expiration
	 * date.
	 */
	public void purchaseCoupon(Coupon coupon) throws CustomerFacadeException {

		CouponDBDAO couponDBDAO = new CouponDBDAO();
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Collection<Coupon> coupons = new ArrayList<>();

		try {
			Coupon dbCoupon = couponDBDAO.getCoupon(coupon.getId());
			coupons = customerDBDAO.getCoupons(custId);
			Date date = new Date();

			if (!(coupons.equals(null))) {

				if (!(coupons.contains(dbCoupon.getId())) || dbCoupon.getAmount() != 0
						|| !(dbCoupon.getEndDate().before(date))) {

					dbCoupon.setAmount(dbCoupon.getAmount() - 1);
					customerDBDAO.insertCustomerCoupon(custId, dbCoupon.getId());
					couponDBDAO.updateCoupon(dbCoupon);

				} else {

					System.out.println("This coupon cannot be purchased.");

				}
			} else {
				dbCoupon.setAmount(dbCoupon.getAmount() - 1);
				customerDBDAO.insertCustomerCoupon(custId, dbCoupon.getId());
				couponDBDAO.updateCoupon(dbCoupon);
			}
		} catch (CustomerDAOException | CouponDAOException e) {
			throw new CustomerFacadeException("CustomerFacade: purchase coupon is failed", e);
		}

	}

	/**
	 * This method returns the list off all purchased coupons for specific user.
	 */
	public Collection<Coupon> getAllPurchasedCoupons() throws CustomerFacadeException {

		CustomerDBDAO customer = new CustomerDBDAO();
		Collection<Coupon> coupons = new ArrayList<>();

		try {
			coupons = customer.getCoupons(custId);
		} catch (CustomerDAOException e) {
			throw new CustomerFacadeException("CustomerFacade: getting all purchased coupons is failed", e);
		}

		return coupons;

	}

	/**
	 * This method receives a coupon type and returns the list of coupons are
	 * selected by specific type.
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws CustomerFacadeException {

		CustomerDBDAO customer = new CustomerDBDAO();
		Collection<Coupon> coupons = new ArrayList<>();
		Collection<Coupon> couponsByType = new ArrayList<>();

		try {
			coupons = customer.getCoupons(custId);
		} catch (CustomerDAOException e) {
			throw new CustomerFacadeException("CustomerFacade: getting all purchased coupons by type is failed", e);
		}

		for (Coupon curr : coupons) {

			if (curr.getType().equals(type)) {
				couponsByType.add(curr);
			}

		}

		return couponsByType;

	}

	/**
	 * This method receives a coupon price and returns the list of coupons that
	 * price is not bigger than selected one.
	 */
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws CustomerFacadeException {

		CustomerDBDAO customer = new CustomerDBDAO();
		Collection<Coupon> coupons = new ArrayList<>();
		Collection<Coupon> couponsByPrice = new ArrayList<>();

		try {
			coupons = customer.getCoupons(custId);
		} catch (CustomerDAOException e) {
			throw new CustomerFacadeException("CustomerFacade: getting all purchased coupons by price is failed", e);
		}

		for (Coupon curr : coupons) {

			if (curr.getPrice() <= price) {
				couponsByPrice.add(curr);
			}

		}

		return couponsByPrice;

	}

	/**
	 * The method to get specific customer ID of user after login process.
	 */
	public long getCustId() {
		return custId;
	}

	// public void setCustId(long custId) {
	// this.custId = custId;
	// }
	public void setCustId(String name) throws CustomerFacadeException {
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Collection<Customer> customers = new HashSet<>();
		try {
			customers = customerDBDAO.getAllCustomers();
		} catch (CustomerDAOException e) {
			throw new CustomerFacadeException("CustomerFacade: could not set customer id", e);
		}
		for (Customer customer : customers) {
			if (customer.getCustName().equals(name)) {

				this.custId = customer.getId();
				break;
			}
		}

	}

}
