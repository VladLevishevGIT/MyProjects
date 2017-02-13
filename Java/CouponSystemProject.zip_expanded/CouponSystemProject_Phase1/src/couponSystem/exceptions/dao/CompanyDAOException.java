package couponSystem.exceptions.dao;

import couponSystem.exceptions.CouponSystemExceptions;

/**
 * This is CompanyDAOException class that responsible to take care about
 * exceptions are thrown from DAO-Company level methods.
 */
public class CompanyDAOException extends CouponSystemExceptions {

	private static final long serialVersionUID = 1L;

	public CompanyDAOException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompanyDAOException(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

	public CompanyDAOException(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public CompanyDAOException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
