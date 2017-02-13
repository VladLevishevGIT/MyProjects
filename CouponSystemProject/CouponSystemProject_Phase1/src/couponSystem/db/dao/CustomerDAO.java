package couponSystem.db.dao;

import java.util.Collection;

import couponSystem.db.javaBeans.Company;
import couponSystem.db.javaBeans.Coupon;
import couponSystem.db.javaBeans.Customer;

public interface CustomerDAO {

	/**
	 * This method creates a new customer in the system.
	 */
	public void createCustomer(Customer customer) throws Exception;

	/**
	 * This method removes a customer from the system.
	 */
	public void removeCustomer(Customer customer) throws Exception;

	/**
	 * This method updates an existing customer.
	 */
	public void updateCustomer(Customer customer) throws Exception;

	/**
	 * This method returns a customer from the system using a given id.
	 */
	public Customer getCustomer(long id) throws Exception;

	/**
	 * This method returns a collection of companies from the system that owns
	 * the coupons that a specific customer has using a given id.
	 */
	public Collection<Company> getAllCustomer(long id) throws Exception;

	/**
	 * This method returns a collection of coupons that a specific customer has
	 * using a given id.
	 */
	public Collection<Coupon> getCoupons(long id) throws Exception;

	/**
	 * This method returns a boolean for whether the given customer name and
	 * password exist in the system.
	 */
	public boolean login(String custName, String password) throws Exception;

}
