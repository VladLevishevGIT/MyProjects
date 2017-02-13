package couponSystem.facades.clients;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import couponSystem.db.dao.dbdao.CompanyDBDAO;
import couponSystem.db.dao.dbdao.CouponDBDAO;
import couponSystem.db.javaBeans.Company;
import couponSystem.db.javaBeans.Coupon;
import couponSystem.db.javaBeans.Coupon.CouponType;
import couponSystem.exceptions.dao.CompanyDAOException;
import couponSystem.exceptions.dao.CouponDAOException;
import couponSystem.exceptions.facade.CompanyFacadeException;
import couponSystem.facades.CouponClientFacade;

public class CompanyFacade extends CouponClientFacade {

	// Attributes

	private long compId;

	// CTOR
	public CompanyFacade() {

	}

	// Methods

	/**
	 * This method creates a new coupon if there is no coupon with the same name
	 * that already exists.
	 */
	public void createCoupon(Coupon coupon) throws CompanyFacadeException {

		CouponDBDAO couponDBDAO = new CouponDBDAO();
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Collection<Coupon> coupons = new HashSet<>();
		try {
			coupons = couponDBDAO.getAllCoupons();
			if (!(coupons.contains(coupon.getTitle()))) {
				couponDBDAO.createCoupon(coupon);
				companyDBDAO.insertCompanyCoupon(compId, coupon.getId());
			} else {
				System.out.println("A coupon with this title already exists.");
			}

		} catch (CouponDAOException | CompanyDAOException e) {
			throw new CompanyFacadeException("CompanyFacade: creating coupon is failed", e);
		}
	}

	/**
	 * This method removes the given coupon.
	 */
	public void removeCoupon(Coupon coupon) throws CompanyFacadeException {

		CouponDBDAO couponDBDAO = new CouponDBDAO();
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Collection<Coupon> coupons = new HashSet<>();
		Company company = new Company();
		company.setId(compId);

		try {
			coupons = companyDBDAO.getCoupons(company);
			if (coupons.contains(coupon.getId())) {
				couponDBDAO.removeCoupon(coupon);
			} else {
				throw new CompanyFacadeException("CompanyFacade: coupon removing is failed");
			}
		} catch (CouponDAOException | CompanyDAOException e) {
			throw new CompanyFacadeException("CompanyFacade: coupon removing is failed", e);
		}

	}

	/**
	 * This method updates the given coupon's price and end date.
	 */
	public void updateCoupon(Coupon coupon) throws CompanyFacadeException {

		CouponDBDAO couponDBDAO = new CouponDBDAO();
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Collection<Coupon> coupons = new HashSet<>();
		Company company = new Company();
		company.setId(compId);
		Coupon dbCoupon;
		try {
			coupons = companyDBDAO.getCoupons(company);
			for (Coupon coupon2 : coupons) {
				if (coupon2.getId() == coupon.getId()) {
					
					dbCoupon = couponDBDAO.getCoupon(coupon.getId());
					dbCoupon.setPrice(coupon.getPrice());
					dbCoupon.setEndDate(coupon.getEndDate());
					
					couponDBDAO.updateCoupon(dbCoupon);
				}
			} 
		} catch (CouponDAOException | CompanyDAOException e) {
			throw new CompanyFacadeException("CompanyFacade: updating coupon is failed", e);
		}

	}

	/**
	 * This method returns a company using a given id.
	 */
	public Company getCompany(long compId) throws CompanyFacadeException {

		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Company company = new Company();

		try {
			company = companyDBDAO.getCompany(compId);
			return company;
		} catch (CompanyDAOException e) {
			throw new CompanyFacadeException("CompanyFacade: getting company is failed", e);
		}


	}

	/**
	 * This method returns a coupon using a given id.
	 */
	public Coupon getCoupon(long coupId) throws CompanyFacadeException {

		CouponDBDAO couponDBDAO = new CouponDBDAO();
		Coupon coupon = new Coupon();
		Company company = new Company();
		company.setId(compId);
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Collection<Coupon> coupons = new HashSet<>();

		try {
			coupons = companyDBDAO.getCoupons(company);
			coupon = couponDBDAO.getCoupon(coupId);
			for (Coupon coupon2 : coupons) {
				if (coupon.getId() == coupon2.getId()) {
					return coupon;
				}
			}
		} catch (CouponDAOException | CompanyDAOException e) {
			throw new CompanyFacadeException("CompanyFacade: getting coupon is failed", e);
		}
		coupon.setTitle("");
		return coupon;
	}

	/**
	 * This method returns a collection of all the existing coupons of the
	 * company that claimed the facade.
	 */
	public Collection<Coupon> getCoupons(Company company) throws CompanyFacadeException {

		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Collection<Coupon> coupons = new ArrayList<>();

		try {
			coupons = companyDBDAO.getCoupons(company);
		} catch (CompanyDAOException e) {
			throw new CompanyFacadeException("CompanyFacade: getting coupons is failed", e);
		}

		return coupons;

	}

	/**
	 * This method returns a collection of all the coupons from a given type
	 * that are owned by a given company,
	 */
	public Collection<Coupon> getCouponByType(Company company, CouponType type) throws CompanyFacadeException {

		CouponDBDAO couponDBDAO = new CouponDBDAO();
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Collection<Coupon> coupons = new ArrayList<>();
		Collection<Coupon> coupons1 = new ArrayList<>();

		try {
			coupons = companyDBDAO.getCoupons(company);
			coupons1 = couponDBDAO.getCouponByType(type);
			coupons.retainAll(coupons1);
		} catch (CompanyDAOException | CouponDAOException e) {
			throw new CompanyFacadeException("CompanyFacade: getting coupon by type is failed", e);
		}

		return coupons;

	}

	/**
	 * This method returns a collection of coupons of a company that are cheaper
	 * than a given price.
	 */
	public Collection<Coupon> getCouponsCheaperThan(Company company, double price) throws CompanyFacadeException {

		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Collection<Coupon> coupons = new HashSet<>();
		Collection<Coupon> couponsCheaperThan = new HashSet<>();

		try {
			coupons = companyDBDAO.getCoupons(company);
		} catch (CompanyDAOException e) {
			throw new CompanyFacadeException("CompanyFacade: getting all purchased coupons by price is failed", e);
		}
		for (Coupon coupon : coupons) {

			if (coupon.getPrice() <= price) {
				couponsCheaperThan.add(coupon);
			}
		}
		return couponsCheaperThan;
	}

	/**
	 * This method returns a collection of coupons of a company that their end
	 * date is after the given date.
	 */
	public Collection<Coupon> getCouponsUntilDate(Company company, Date date) throws CompanyFacadeException {

		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Collection<Coupon> coupons = new ArrayList<>();
		Collection<Coupon> couponsUntilDate = new ArrayList<>();

		try {
			coupons = companyDBDAO.getCoupons(company);
			for (Coupon coupon : coupons) {

				if (coupon.getEndDate().after(date)) {
					couponsUntilDate.add(coupon);
				}
			}
		} catch (CompanyDAOException e) {
			throw new CompanyFacadeException("CompanyFacade: getting coupons unit date ... is failed", e);
		}

		return couponsUntilDate;

	}

	/**
	 * The method to get specific company ID of user after login process.
	 */
	public long getCompId() {
		return compId;
	}

	// public void setCompId(long compId) {
	// this.compId = compId;
	// }

	public void setCompId(String name) throws CompanyFacadeException {
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Collection<Company> companys = new HashSet<>();
		try {
			companys = companyDBDAO.getAllCompanies();
		} catch (CompanyDAOException e) {
			throw new CompanyFacadeException("CompanyFacade: could not set company id", e);
		}
		for (Company company : companys) {
			if (company.getCompName().equals(name)) {

				this.compId = company.getId();
				break;
			}
		}

	}
}
