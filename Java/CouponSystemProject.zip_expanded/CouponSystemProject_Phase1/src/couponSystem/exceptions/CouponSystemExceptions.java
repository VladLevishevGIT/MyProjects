package couponSystem.exceptions;

import java.sql.SQLException;

/**
 * This is main Exception class for all exceptions in "Coupon-System" project
 */
public class CouponSystemExceptions extends SQLException {

	private static final long serialVersionUID = 1L;

	public CouponSystemExceptions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CouponSystemExceptions(String reason, String SQLState) {
		super(reason, SQLState);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemExceptions(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemExceptions(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemExceptions(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
