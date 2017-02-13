package couponSystem.db.dao.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import couponSystem.db.connectionPool.ConnectionPool;
import couponSystem.db.dao.CompanyDAO;
import couponSystem.db.javaBeans.Company;
import couponSystem.db.javaBeans.Coupon;
import couponSystem.exceptions.dao.CompanyDAOException;

public class CompanyDBDAO implements CompanyDAO {

	private Connection con;
	private String sql;

	/**
	 * This method receives a certain company and adds it to the database.
	 */
	@Override
	public void createCompany(Company company) throws CompanyDAOException {

		con = ConnectionPool.getInstance().getConnection();

		sql = "INSERT INTO company (comp_name, password, email) VALUES (?, ?, ?)";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, company.getCompName());
			pstmt.setString(2, company.getPassword());
			pstmt.setString(3, company.getEmail());
			pstmt.executeUpdate();

			ResultSet key = pstmt.getGeneratedKeys();
			key.next();
			long companyGeneratedID = key.getLong(1);
			company.setId(companyGeneratedID);

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new CompanyDAOException("CompanyDAO: create company by companyDAO is failed. Company named: --"
					+ company.getCompName() + "-- already exists", e);
		} catch (SQLException e) {
			throw new CompanyDAOException("CompanyDAO: create company by companyDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}

	}

	/**
	 * This method receives a certain company and removes it from the database
	 * using its id.
	 */
	@Override
	public void removeCompany(Company company) throws CompanyDAOException {

		con = ConnectionPool.getInstance().getConnection();

		sql = "DELETE FROM company WHERE id = ?";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, company.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CompanyDAOException("CompanyDAO: company removing by companyDAO is faled", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}

	}

	/**
	 * This method receives a certain company and is updating it in the database
	 * using its id.
	 */
	@Override
	public void updateCompany(Company company) throws CompanyDAOException {

		con = ConnectionPool.getInstance().getConnection();

		sql = "UPDATE company SET comp_name = ?, password = ?, email = ? WHERE id = ?";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, company.getCompName());
			pstmt.setString(2, company.getPassword());
			pstmt.setString(3, company.getEmail());
			pstmt.setLong(4, company.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CompanyDAOException("CompanyDAO: company updating by companyDAO is faled", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}

	}

	/**
	 * This method receives a certain id and returns the corresponding company.
	 */
	@Override
	public Company getCompany(long id) throws CompanyDAOException {

		con = ConnectionPool.getInstance().getConnection();

		sql = "SELECT id , comp_name, password, email FROM company WHERE id = ?";
		PreparedStatement pstmt;
		Company company = new Company();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				company.setId(rs.getLong(1));
				company.setCompName(rs.getString(2));
				company.setPassword(rs.getString(3));
				company.setEmail(rs.getString(4));

				sql = "SELECT coupon_id FROM company_coupon WHERE comp_id = ?";
				pstmt = con.prepareStatement(sql);

				pstmt.setLong(1, id);
				rs = pstmt.executeQuery();

				Collection<Coupon> coupons = new HashSet<>();
				CouponDBDAO coupon = new CouponDBDAO();

				while (rs.next()) {
					coupons.add(coupon.getCoupon(rs.getLong("coupon_id")));
				}

				company.setCoupons(coupons);

				return company;
			} else {
				throw new CompanyDAOException("CompanyDAO: a company with the specified ID was not found");
			}
		} catch (SQLException e) {
			throw new CompanyDAOException("CompanyDAO: getting company by companyDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

	/**
	 * This method creates a collection of all the companies that exists in the
	 * database.
	 */
	@Override
	public Collection<Company> getAllCompanies() throws CompanyDAOException {

		con = ConnectionPool.getInstance().getConnection();

		sql = "SELECT id, comp_name, password, email FROM company";
		PreparedStatement pstmt;
		Collection<Company> companies = new HashSet<>();
		try {
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getLong(1));
				company.setCompName(rs.getString(2));
				company.setPassword(rs.getString(3));
				company.setEmail(rs.getString(4));
				companies.add(company);

			}

			return companies;
		} catch (SQLException e) {
			throw new CompanyDAOException("CompanyDAO: getting all companies by companyDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

	/**
	 * This method returns a collection of all the coupons are related to
	 * specific company that exists in the database.
	 */
	@Override
	public Collection<Coupon> getCoupons(Company company) throws CompanyDAOException {

		con = ConnectionPool.getInstance().getConnection();

		sql = "SELECT coupon_id FROM company_coupon WHERE comp_id = ?";
		PreparedStatement pstmt;
		Collection<Coupon> coupons = new HashSet<>();

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, company.getId());
			ResultSet rs = pstmt.executeQuery();

			CouponDBDAO couponDBDAO = new CouponDBDAO();

			while (rs.next()) {
				coupons.add(couponDBDAO.getCoupon(rs.getLong(1)));
			}

			return coupons;
		} catch (SQLException e) {
			throw new CompanyDAOException("CompanyDAO: getting coupons by companyDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}
		
	}

	/**
	 * This method receives companie's name and password and returns a boolean
	 * after checking whether they match or not.
	 */
	@Override
	public boolean login(String compName, String password) throws CompanyDAOException {

		con = ConnectionPool.getInstance().getConnection();

		sql = "SELECT * FROM company WHERE comp_name=?";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, compName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString("password").equals(password)) {

					return true;
				}
				// if (rs.getString("password").equals(password)) {
				// CompanyDBDAO companyDBDAO = new CompanyDBDAO();
				// Company company = new Company();
				// company = companyDBDAO.getCompany(rs.getLong("id"));
				// ConnectionPool.getInstance().returnConnection(con);
				// return company;
				else {
					return false;
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new CompanyDAOException("CompanyDAO: login by companyDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

	/**
	 * This method inserts the company_id + coupon_id into the join table.
	 */
	public void insertCompanyCoupon(long compID, long coupID) throws CompanyDAOException {

		con = ConnectionPool.getInstance().getConnection();

		String sql = "INSERT INTO company_coupon VALUES (?, ?)";
		PreparedStatement pstmt;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, compID);
			pstmt.setLong(2, coupID);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CompanyDAOException("CompanyDAO: Company-Coupon inserting is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}
		
	}

}
