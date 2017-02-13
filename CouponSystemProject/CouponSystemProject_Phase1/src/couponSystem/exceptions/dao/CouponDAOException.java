package couponSystem.exceptions.dao;

import couponSystem.exceptions.CouponSystemExceptions;

/**
 * This is CouponDAOException class that responsible to take care about
 * exceptions are thrown from DAO-Coupon level methods.
 */
public class CouponDAOException extends CouponSystemExceptions {

	private static final long serialVersionUID = 1L;

	public CouponDAOException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CouponDAOException(String reason, String SQLState) {
		super(reason, SQLState);
		// TODO Auto-generated constructor stub
	}

	public CouponDAOException(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

	public CouponDAOException(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public CouponDAOException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
