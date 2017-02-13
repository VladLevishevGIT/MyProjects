package couponSystem.db.dao.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import couponSystem.db.connectionPool.ConnectionPool;
import couponSystem.db.dao.CouponDAO;
import couponSystem.db.javaBeans.Coupon;
import couponSystem.db.javaBeans.Coupon.CouponType;
import couponSystem.exceptions.dao.CouponDAOException;

public class CouponDBDAO implements CouponDAO {

	private Connection con;

	public CouponDBDAO() {
		super();
	}

	/**
	 * This method receives a certain coupon and adds it to the database.
	 */
	@Override
	public void createCoupon(Coupon coupon) throws CouponDAOException {

		con = ConnectionPool.getInstance().getConnection();

		String sql = "INSERT INTO coupon (title, start_date, end_date, amount, type, message, price, image) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt;

		try {
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, coupon.getTitle());
			java.sql.Date startDate = new java.sql.Date(coupon.getStartDate().getTime());
			pstmt.setDate(2, startDate);
			java.sql.Date endDate = new java.sql.Date(coupon.getEndDate().getTime());
			pstmt.setDate(3, endDate);
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getType().toString());
			pstmt.setString(6, coupon.getMessage());
			pstmt.setDouble(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());

			pstmt.executeUpdate();

			ResultSet key = pstmt.getGeneratedKeys();
			key.next();
			long companyGeneratedID = key.getLong(1);
			coupon.setId(companyGeneratedID);

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new CouponDAOException("CouponDAO: create coupon by couponDAO is failed. Coupon named: --"
					+ coupon.getTitle() + "-- already exists", e);
		} catch (SQLException e) {
			throw new CouponDAOException("CouponDAO: create coupon by couponDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}
	}

	/**
	 * This method receives a certain coupon and removes it from the database
	 * using its id.
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws CouponDAOException {

		con = ConnectionPool.getInstance().getConnection();

		String sql = "DELETE FROM coupon WHERE id=?";
		PreparedStatement pstmt;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, coupon.getId());
			pstmt.executeUpdate();

			String sql1 = "DELETE FROM customer_coupon WHERE coupon_id=?";
			PreparedStatement pstmt1 = con.prepareStatement(sql1);
			pstmt1.setLong(1, coupon.getId());
			pstmt1.executeUpdate();

			String sql2 = "DELETE FROM company_coupon WHERE coupon_id=?";
			PreparedStatement pstmt2 = con.prepareStatement(sql2);
			pstmt2.setLong(1, coupon.getId());
			pstmt2.executeUpdate();
		} catch (SQLException e) {
			throw new CouponDAOException("CouponDAO: removing coupon by couponDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

	/**
	 * This method receives a certain coupon and is updating it in the database
	 * using its id.
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponDAOException {

		con = ConnectionPool.getInstance().getConnection();

		String sql = "UPDATE coupon SET title=?, start_date=?, end_date=?, amount=?, type=?, message=?, price=?, image=? WHERE id=?";
		PreparedStatement pstmt;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, coupon.getTitle());
			java.sql.Date startDate = new java.sql.Date(coupon.getStartDate().getTime());
			pstmt.setDate(2, startDate);
			java.sql.Date endDate = new java.sql.Date(coupon.getEndDate().getTime());
			pstmt.setDate(3, endDate);
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getType().toString());
			pstmt.setString(6, coupon.getMessage());
			pstmt.setDouble(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());
			pstmt.setLong(9, coupon.getId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponDAOException("CouponDAO: coupon updating by couponDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

	/**
	 * This method receives a certain id and returns the corresponding coupon.
	 */
	@Override
	public Coupon getCoupon(long id) throws CouponDAOException {

		con = ConnectionPool.getInstance().getConnection();

		Coupon coupon = new Coupon();
		String sql = "SELECT * FROM coupon WHERE id=?";
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {

				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStartDate((java.util.Date) rs.getDate(3));
				coupon.setEndDate((java.util.Date) rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setType(Coupon.CouponType.valueOf(rs.getString(6)));
				coupon.setMessage(rs.getString(7));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));

				return coupon;
			} else {
				throw new CouponDAOException("CouponDAO: a coupon with the specified ID was not found");
			}
		} catch (SQLException e) {
			throw new CouponDAOException("CouponDAO: getting coupon by couponDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

	/**
	 * This method creates a collection of all the coupons that exists in the
	 * database.
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws CouponDAOException {

		con = ConnectionPool.getInstance().getConnection();

		Collection<Coupon> col = new ArrayList<>();

		String sql = "SELECT * FROM coupon";
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong("id"));
				coupon.setTitle(rs.getString("title"));
				coupon.setStartDate((java.util.Date) rs.getDate("start_date"));
				coupon.setEndDate((java.util.Date) rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setType(Coupon.CouponType.valueOf(rs.getString("type")));
				coupon.setMessage(rs.getString("message"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));

				col.add(coupon);// adding the coupon to the collection.

			}
			
			return col;
		} catch (SQLException e) {
			throw new CouponDAOException("CouponDAO: getting all coupons by couponDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

	/**
	 * This method receives a couponType and returns a collection of the
	 * corresponding coupons.
	 */
	@Override
	public Collection<Coupon> getCouponByType(CouponType type) throws CouponDAOException {

		con = ConnectionPool.getInstance().getConnection();

		Collection<Coupon> col = new ArrayList<Coupon>();

		String sql = "SELECT * FROM coupon WHERE type=?";
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, type.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong("id"));
				coupon.setTitle(rs.getString("title"));
				coupon.setStartDate((java.util.Date) rs.getDate("start_date"));
				coupon.setEndDate((java.util.Date) rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setType(Coupon.CouponType.valueOf(rs.getString("type")));
				coupon.setMessage(rs.getString("message"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));

				col.add(coupon);// adding the coupon to the collection.

			}

			return col;
		} catch (SQLException e) {
			throw new CouponDAOException("CouponDAO: getting coupon (by type) by couponDAO is failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);			
		}

	}

}
