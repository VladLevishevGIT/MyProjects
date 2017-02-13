package couponSystem.singleton;

import couponSystem.db.connectionPool.ConnectionPool;
import couponSystem.db.dao.dbdao.CompanyDBDAO;
import couponSystem.db.dao.dbdao.CustomerDBDAO;
import couponSystem.exceptions.dao.CompanyDAOException;
import couponSystem.exceptions.dao.CustomerDAOException;
import couponSystem.exceptions.facade.CompanyFacadeException;
import couponSystem.exceptions.facade.CustomerFacadeException;
import couponSystem.exceptions.singleton.CouponSystemSingletonException;
import couponSystem.facades.CouponClientFacade;
import couponSystem.facades.clients.AdminFacade;
import couponSystem.facades.clients.CompanyFacade;
import couponSystem.facades.clients.CustomerFacade;
import couponSystem.thread.DailyThread;

public class CouponSystemSingleton {

	// Attributes
	public static enum ClientType {
		ADMIN, CUSTOMER, COMPANY;
	}

	private DailyThread task = new DailyThread();
	private Thread t = new Thread(task);

	// CTOR : ConeectionPoll creation & thread running
	private CouponSystemSingleton() {
		ConnectionPool.getInstance();
		t.start();
	}

	private static CouponSystemSingleton instance = new CouponSystemSingleton();

	public static CouponSystemSingleton getInstance() {
		return instance;
	}

	/**
	 * The method for main login process, to get relevant facade to work with.
	 */
	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws CouponSystemSingletonException {

		switch (clientType) {
		case ADMIN:
			if (password.equals("123456") && name.equals("admin")) {
				AdminFacade adminFacade = new AdminFacade();
				return adminFacade;
			} else {
				throw new CouponSystemSingletonException("CouponSystemSingleton: admin facade login is failed");
			}
		case CUSTOMER:
			CustomerDBDAO customerDBDAO = new CustomerDBDAO();
			try {
				// if (customerDBDAO.login(name, password) != null) {
				// CustomerFacade customerFacade = new CustomerFacade();
				// Customer customer = customerDBDAO.login(name, password);
				// customerFacade.setCustId(customer.getId());
				// return customerFacade;
				// }
				if (customerDBDAO.login(name, password)) {
					CustomerFacade customerFacade = new CustomerFacade();
					customerFacade.setCustId(name);
					return customerFacade;
				}
			} catch (CustomerDAOException | CustomerFacadeException e) {
				throw new CouponSystemSingletonException("CouponSystemSingleton: customer facade login is failed", e);
			}
		case COMPANY:
			CompanyDBDAO companyDBDAO = new CompanyDBDAO();
			try {
				// if (companyDBDAO.login(name, password) != null) {
				// CompanyFacade companyFacade = new CompanyFacade();
				// Company company = companyDBDAO.login(name, password);
				// companyFacade.setCompId(company.getId());
				// return companyFacade;
				// }
				if (companyDBDAO.login(name, password)) {
					CompanyFacade companyFacade = new CompanyFacade();
					companyFacade.setCompId(name);
					return companyFacade;
				}
			} catch (CompanyDAOException | CompanyFacadeException e) {
				throw new CouponSystemSingletonException("CouponSystemSingleton: company facade login is failed", e);
			}

		default:
			return null;
		}

	}

	/**
	 * The method for proper shutdown.
	 */
	public void shutdown() throws CouponSystemSingletonException {
		ConnectionPool.getInstance().closeAllCons();
		t.interrupt();
	}

}
