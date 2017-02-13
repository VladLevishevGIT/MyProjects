package couponSystem.exceptions.thread;

import couponSystem.exceptions.CouponSystemExceptions;

/**
 * This is ThreadException class that responsible to take care about exceptions
 * are thrown from Thread class methods.
 */
public class ThreadException extends CouponSystemExceptions {

	private static final long serialVersionUID = 1L;

	public ThreadException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ThreadException(String reason, String SQLState) {
		super(reason, SQLState);
		// TODO Auto-generated constructor stub
	}

	public ThreadException(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

	public ThreadException(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public ThreadException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
