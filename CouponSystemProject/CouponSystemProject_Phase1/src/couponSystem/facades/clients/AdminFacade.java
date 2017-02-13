package couponSystem.facades.clients;

import java.util.Collection;
import java.util.HashSet;

import couponSystem.db.dao.dbdao.CompanyDBDAO;
import couponSystem.db.dao.dbdao.CouponDBDAO;
import couponSystem.db.dao.dbdao.CustomerDBDAO;
import couponSystem.db.javaBeans.Company;
import couponSystem.db.javaBeans.Coupon;
import couponSystem.db.javaBeans.Customer;
import couponSystem.exceptions.dao.CompanyDAOException;
import couponSystem.exceptions.dao.CouponDAOException;
import couponSystem.exceptions.dao.CustomerDAOException;
import couponSystem.exceptions.facade.AdminFacadeException;
import couponSystem.facades.CouponClientFacade;

public class AdminFacade extends CouponClientFacade {

	// Attributes

	// private long adminId;

	// CTOR
	public AdminFacade() {

	}

	// Methods

	// -------Company---------//

	/**
	 * This method creates a new company if there is no company with the same
	 * name that already exists.
	 */
	public void createCompany(Company company) throws AdminFacadeException {

		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Collection<Company> companies = new HashSet<>();
		try {
			companies = companyDBDAO.getAllCompanies();
			if (!(companies.contains(company.getCompName()))) {
				companyDBDAO.createCompany(company);
			}
		} catch (CompanyDAOException e) {
			throw new AdminFacadeException("AdminFacade: company creation is failed", e);
		}
	}

	/**
	 * This method removes the given company including all of its coupons.
	 */
	public void removeCompany(Company company) throws AdminFacadeException {

		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		Collection<Coupon> coupons = new HashSet<>();

		Company dbComp;
		try {
			dbComp = companyDBDAO.getCompany(company.getId());
			coupons = dbComp.getCoupons();

			if (coupons != null) {
				for (Coupon coupon : coupons) {
					try {
						couponDBDAO.removeCoupon(coupon);
					} catch (CouponDAOException e) {
						throw new AdminFacadeException("AdminFacade: removing company's coupons is failed", e);
					}
				}
			}

			companyDBDAO.removeCompany(company);

		} catch (CompanyDAOException e) {
			throw new AdminFacadeException("AdminFacade: removing company is failed", e);
		}

	}

	/**
	 * This method updates the given company's Email and password.
	 */
	public void updateCompany(Company company) throws AdminFacadeException {

		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Company dbComp;
		try {
			dbComp = companyDBDAO.getCompany(company.getId());
			dbComp.setEmail(company.getEmail());
			dbComp.setPassword(company.getPassword());

			companyDBDAO.updateCompany(dbComp);
		} catch (CompanyDAOException e) {
			throw new AdminFacadeException("AdminFacade: updating company is failed", e);
		}

	}

	/**
	 * This method returns a company using a given id.
	 */
	public Company getCompany(long id) throws AdminFacadeException {

		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Company company = new Company();

		try {
			company = companyDBDAO.getCompany(id);
		} catch (CompanyDAOException e) {
			throw new AdminFacadeException("AdminFacade: getting company is failed", e);
		}

		return company;

	}

	/**
	 * This method returns a collection of all the existing companies.
	 */
	public Collection<Company> getAllCompanies() throws AdminFacadeException {

		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Collection<Company> companies = new HashSet<>();

		try {
			companies = companyDBDAO.getAllCompanies();
		} catch (CompanyDAOException e) {
			throw new AdminFacadeException("AdminFacade: getting all companies is failed", e);
		}

		return companies;

	}

	// --------Customer---------//

	/**
	 * This method creates a new customer if there is no other customer with the
	 * same name.
	 */
	public void createCustomer(Customer customer) throws AdminFacadeException {

		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Collection<Customer> customers = new HashSet<>();
		try {
			customers = customerDBDAO.getAllCustomers();
			if (!(customers.contains(customer.getCustName()))) {
				customerDBDAO.createCustomer(customer);
			} else {
				System.out.println("a customer with this name already exists.");
			}
		} catch (CustomerDAOException e) {
			throw new AdminFacadeException("AdminFacade: customer creation is failed", e);
		}

	}

	/**
	 * This method removes a given customer.
	 */
	public void removeCustomer(Customer customer) throws AdminFacadeException {

		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Customer dbCustomer = new Customer();
		
		try {
			dbCustomer = customerDBDAO.getCustomer(customer.getId());
			customerDBDAO.removeCustomer(dbCustomer);
		} catch (CustomerDAOException e) {
			throw new AdminFacadeException("AdminFacade: removing customer is failed", e);
		}

	}

	/**
	 * This method updates the given customer's password.
	 */
	public void updateCustomer(Customer customer) throws AdminFacadeException {

		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Customer dbCust;

		try {

			dbCust = customerDBDAO.getCustomer(customer.getId());
			dbCust.setCustName(customer.getCustName());
			dbCust.setPassword(customer.getPassword());
			customerDBDAO.updateCustomer(dbCust);

		} catch (CustomerDAOException e) {
			throw new AdminFacadeException("AdminFacade: updating customer is failed", e);
		}
	}

	/**
	 * This method returns a customer using a given id.
	 */
	public Customer getCustomer(long id) throws AdminFacadeException {

		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Customer customer = new Customer();

		try {
			customer = customerDBDAO.getCustomer(id);
		} catch (CustomerDAOException e) {
			throw new AdminFacadeException("AdminFacade: getting customer is failed", e);
		}

		return customer;

	}

	/**
	 * This method returns a collection of all the customers.
	 */
	public Collection<Customer> getAllCustomers() throws AdminFacadeException {

		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Collection<Customer> customers = new HashSet<>();

		try {
			customers = customerDBDAO.getAllCustomers();
		} catch (CustomerDAOException e) {
			throw new AdminFacadeException("AdminFacade: getting all customers is failed", e);
		}

		return customers;

	}

	// public Collection<Company> getAllCustomer(long id) {
	//
	// CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	// Collection<Company> companies = new HashSet<>();
	//
	// companies = customerDBDAO.getAllCustomer(id);
	//
	// return companies;
	//
	// }

}
