package couponSystem.exceptions.dao;

import couponSystem.exceptions.CouponSystemExceptions;

/**
 * This is CustomerDAOException class that responsible to take care about
 * exceptions are thrown from DAO-Customer level methods.
 */
public class CustomerDAOException extends CouponSystemExceptions {

	private static final long serialVersionUID = 1L;

	public CustomerDAOException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerDAOException(String reason, String SQLState) {
		super(reason, SQLState);
		// TODO Auto-generated constructor stub
	}

	public CustomerDAOException(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

	public CustomerDAOException(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public CustomerDAOException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
