package couponSystem.db.dao;

import java.util.Collection;

import couponSystem.db.javaBeans.Company;
import couponSystem.db.javaBeans.Coupon;

public interface CompanyDAO {

	/**
	 * This method creates a new company in the system.
	 */
	void createCompany(Company company) throws Exception;

	/**
	 * This method removes a company from the system.
	 */
	void removeCompany(Company company) throws Exception;

	/**
	 * This method updates an existing company.
	 */
	void updateCompany(Company company) throws Exception;

	/**
	 * This method returns a company from the system using a given id.
	 */
	Company getCompany(long id) throws Exception;

	/**
	 * This method returns a collection of all the companies from the system.
	 */
	Collection<Company> getAllCompanies() throws Exception;

	/**
	 * This method returns a collection of all the coupons from the system for
	 * specific company.
	 */
	Collection<Coupon> getCoupons(Company company) throws Exception;

	/**
	 * This method returns a boolean for whether the given company name and
	 * password exist in the system.
	 */
	boolean login(String compName, String password) throws Exception;

}
