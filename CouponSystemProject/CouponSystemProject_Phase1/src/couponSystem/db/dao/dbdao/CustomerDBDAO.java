package couponSystem.db.dao.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import couponSystem.db.connectionPool.ConnectionPool;
import couponSystem.db.dao.CustomerDAO;
import couponSystem.db.javaBeans.Company;
import couponSystem.db.javaBeans.Coupon;
import couponSystem.db.javaBeans.Customer;
import couponSystem.exceptions.dao.CustomerDAOException;

public class CustomerDBDAO implements CustomerDAO {

	private Connection con;

	public CustomerDBDAO() {
		super();
	}

	/**
	 * This method receives a certain customer and adds it to the database.
	 */
	@Override
	public void createCustomer(Customer customer) throws CustomerDAOException {

		con = ConnectionPool.getInstance().getConnection();

		String sql = "INSERT INTO customer (cust_name, password) VALUES(?, ?)";
		PreparedStatement pstmt;

		try {
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, customer.getCustName());
			pstmt.setString(2, customer.getPassword());

			pstmt.executeUpdate();

			ResultSet key = pstmt.getGeneratedKeys();
			key.next();
			long companyGeneratedID = key.getLong(1);
			customer.setId(companyGeneratedID);

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new CustomerDAOException("CustomerDAO: create customer by customerDAO is failed. Customer named: --"
					+ customer.getCustName() + "-- already exists", e);
		} catch (SQLException e) {
			throw new CustomerDAOException("CustomerDAO: create customer by customerDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}
	}

	/**
	 * This method receives a certain customer and removes it from the database
	 * using its id.
	 */
	@Override
	public void removeCustomer(Customer customer) throws CustomerDAOException {

		con = ConnectionPool.getInstance().getConnection();

		String sql = "DELETE FROM customer WHERE id=?";
		PreparedStatement pstmt;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, customer.getId());
			pstmt.executeUpdate();

			String sql1 = "DELETE FROM customer_coupon WHERE cust_id=?";
			PreparedStatement pstmt1 = con.prepareStatement(sql1);
			pstmt1.setLong(1, customer.getId());
			pstmt1.executeUpdate();
			
		} catch (SQLException e) {
			throw new CustomerDAOException("CustomerDAO: remove customer by customerDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}
		
	}

	/**
	 * This method receives a certain customer and is updating it in the
	 * database using its id.
	 */
	@Override
	public void updateCustomer(Customer customer) throws CustomerDAOException {

		con = ConnectionPool.getInstance().getConnection();

		String sql = "UPDATE customer SET cust_name=?, password=? WHERE id=?";
		PreparedStatement pstmt;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customer.getCustName());
			pstmt.setString(2, customer.getPassword());
			pstmt.setLong(3, customer.getId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CustomerDAOException("CustomerDAO: update customer by customerDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}
		
	}

	/**
	 * This method receives a certain id and returns the corresponding customer.
	 */
	@Override
	public Customer getCustomer(long id) throws CustomerDAOException {

		con = ConnectionPool.getInstance().getConnection();

		Customer customer = new Customer();
		String sql = "SELECT * FROM customer WHERE id=?";
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				customer.setId(rs.getLong("id"));
				customer.setCustName(rs.getString("cust_name"));
				customer.setPassword(rs.getString("password"));

				String sql1 = "SELECT coupon_id FROM customer_coupon WHERE cust_id = ?";
				pstmt = con.prepareStatement(sql1);

				pstmt.setLong(1, id);
				rs = pstmt.executeQuery();

				Collection<Coupon> coupons = new HashSet<>();
				CouponDBDAO coupon = new CouponDBDAO();

				while (rs.next()) {
					coupons.add(coupon.getCoupon(rs.getLong("coupon_id")));
				}

				customer.setCoupons(coupons);

				return customer;

			} else {
				throw new CustomerDAOException("CustomerDAO: a customer with the specified ID was not found");
			}
		} catch (SQLException e) {
			throw new CustomerDAOException("CustomerDAO: getting customer by customerDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

	/**
	 * This method receives a certain customer's id and returns a collection of
	 * the companies who owns the coupons he has.
	 */
	@Override
	public Collection<Company> getAllCustomer(long id) throws CustomerDAOException {

		con = ConnectionPool.getInstance().getConnection();

		CompanyDBDAO companies = new CompanyDBDAO();
		Company company = new Company();
		Collection<Company> col = new HashSet<>();
		String sql = "SELECT coupon_id FROM customer_coupon WHERE cust_id=?";
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				ResultSet rs1;
				String sql1 = "SELECT comp_id FROM company_coupon WHERE coupon_id=?";
				PreparedStatement pstmt1 = con.prepareStatement(sql1);
				pstmt1.setLong(1, rs.getLong("coupon_id"));
				rs1 = pstmt1.executeQuery();

				while (rs1.next()) {

					company = companies.getCompany(rs1.getLong("comp_id"));
					col.add(company);// adding the company to the collection.

				}

			}
			
			return col;
		} catch (SQLException e) {
			throw new CustomerDAOException("CustomerDAO: getting all customer's companies by customerDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

	/**
	 * This method receives a certain customer's id and returns a collection of
	 * his coupons.
	 */
	@Override
	public Collection<Coupon> getCoupons(long id) throws CustomerDAOException {

		con = ConnectionPool.getInstance().getConnection();

		CouponDBDAO coupons = new CouponDBDAO();
		Coupon coupon = new Coupon();
		Collection<Coupon> col = new ArrayList<Coupon>();
		String sql = "SELECT coupon_id FROM customer_coupon WHERE cust_id=?";
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				coupon = coupons.getCoupon(rs.getLong("coupon_id"));
				col.add(coupon);

			}

			return col;
		} catch (SQLException e) {
			throw new CustomerDAOException("CustomerDAO: getting coupons by customerDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}
		
	}

	/**
	 * This method receives customer's name and password and returns a boolean
	 * after checking whether they match or not.
	 */
	@Override
	public boolean login(String custName, String password) throws CustomerDAOException {

		con = ConnectionPool.getInstance().getConnection();

		String sql = "SELECT id, cust_name, password FROM customer WHERE cust_name=?";
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, custName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString("password").equals(password)) {
					ConnectionPool.getInstance().returnConnection(con);
					return true;
				}
				// if (rs.getString("password").equals(password)) {
				// CustomerDBDAO customerDBDAO = new CustomerDBDAO();
				// Customer customer = new Customer();
				// customer = customerDBDAO.getCustomer(rs.getLong("id"));
				// ConnectionPool.getInstance().returnConnection(con);
				// return customer;
				// }
				else {
					return false;
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new CustomerDAOException("CustomerDAO: login by customerDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

	/**
	 * This method inserts the customer_id + coupon_id into the join table.
	 */
	public void insertCustomerCoupon(long custID, long couponID) throws CustomerDAOException {

		con = ConnectionPool.getInstance().getConnection();

		String sql = "INSERT INTO customer_coupon VALUES (?, ?)";
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, custID);
			pstmt.setLong(2, couponID);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CustomerDAOException("CustomerDAO: Customer-Coupon inserting is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

	/**
	 * This method returns a collection of all customers.
	 */
	public Collection<Customer> getAllCustomers() throws CustomerDAOException {
		con = ConnectionPool.getInstance().getConnection();

		String sql = "SELECT id from customer";
		PreparedStatement pstsm;
		Collection<Customer> customers = new HashSet<>();

		try {
			pstsm = con.prepareStatement(sql);
			ResultSet rs = pstsm.executeQuery();

			while (rs.next()) {
				CustomerDBDAO curr = new CustomerDBDAO();
				customers.add(curr.getCustomer(rs.getLong(1)));
			}

			return customers;
		} catch (SQLException e) {
			throw new CustomerDAOException("CustomerDAO: getting all customers is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

}
