package couponSystem.exceptions.facade;

import couponSystem.exceptions.CouponSystemExceptions;

/**
 * This is CompanyFacadeException class that responsible to take care about
 * exceptions are thrown from Facade-Company level methods.
 */
public class CompanyFacadeException extends CouponSystemExceptions {

	private static final long serialVersionUID = 1L;

	public CompanyFacadeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompanyFacadeException(String reason, String SQLState) {
		super(reason, SQLState);
		// TODO Auto-generated constructor stub
	}

	public CompanyFacadeException(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

	public CompanyFacadeException(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public CompanyFacadeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
