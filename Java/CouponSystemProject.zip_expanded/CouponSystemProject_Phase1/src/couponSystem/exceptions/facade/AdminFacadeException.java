package couponSystem.exceptions.facade;

import couponSystem.exceptions.CouponSystemExceptions;

/**
 * This is AdminFacadeException class that responsible to take care about
 * exceptions are thrown from Facade-Admin level methods.
 */
public class AdminFacadeException extends CouponSystemExceptions {

	private static final long serialVersionUID = 1L;

	public AdminFacadeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminFacadeException(String reason, String SQLState) {
		super(reason, SQLState);
		// TODO Auto-generated constructor stub
	}

	public AdminFacadeException(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

	public AdminFacadeException(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public AdminFacadeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
