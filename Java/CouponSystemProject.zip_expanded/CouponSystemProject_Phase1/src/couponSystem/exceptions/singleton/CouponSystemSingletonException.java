package couponSystem.exceptions.singleton;

import couponSystem.exceptions.CouponSystemExceptions;

/**
 * This is main-singleton-exception class that responsible to take care about
 * exceptions are thrown from CouponSystem-Singleton level methods.
 */
public class CouponSystemSingletonException extends CouponSystemExceptions {

	private static final long serialVersionUID = 1L;

	public CouponSystemSingletonException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CouponSystemSingletonException(String reason, String SQLState) {
		super(reason, SQLState);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemSingletonException(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemSingletonException(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemSingletonException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
