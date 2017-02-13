package couponSystem.exceptions.facade;

import couponSystem.exceptions.CouponSystemExceptions;

/**
 * This is CustomerFacadeException class that responsible to take care about
 * exceptions are thrown from Facade-Customer level methods.
 */
public class CustomerFacadeException extends CouponSystemExceptions {

	private static final long serialVersionUID = 1L;

	public CustomerFacadeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerFacadeException(String reason, String SQLState) {
		super(reason, SQLState);
		// TODO Auto-generated constructor stub
	}

	public CustomerFacadeException(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

	public CustomerFacadeException(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public CustomerFacadeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
