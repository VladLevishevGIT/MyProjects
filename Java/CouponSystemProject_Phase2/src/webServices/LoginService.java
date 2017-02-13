package webServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import couponSystem.exceptions.singleton.CouponSystemSingletonException;
import couponSystem.facades.CouponClientFacade;
import couponSystem.facades.clients.AdminFacade;
import couponSystem.facades.clients.CompanyFacade;
import couponSystem.facades.clients.CustomerFacade;
import couponSystem.singleton.CouponSystemSingleton;
import couponSystem.singleton.CouponSystemSingleton.ClientType;

@Path("/LoginService")
public class LoginService {

	@Context
	private HttpServletRequest req;
	@Context
	private HttpServletResponse res;
	
	@GET
	@Path("login/{user}/{name}/{password}")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@PathParam("user") String user, @PathParam("name") String name,
			@PathParam("password") String password) {
		
		String facade = "facade";
		String url = "http://localhost:8080/CouponSystemProject_State02/";
		
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
//			System.out.println("SESSION INVALIDATED");
		}
		session = req.getSession(true);
//		if (session.isNew()) {
//			session.setAttribute(facade, new CouponClientFacade());
//		}
//		try {
//			Class.forName("org.apache.derby.jdbc.ClientDriver");
//		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		}

		if (user.equals("Administrator")) {
			try {
				CouponClientFacade sessionFacade  = (AdminFacade) CouponSystemSingleton.getInstance().login(name, password, ClientType.ADMIN);
				if (sessionFacade != null) {
					
					session.setAttribute(facade, sessionFacade);
//					session.setMaxInactiveInterval(30);
					
					System.out.println(user);
//					res.sendRedirect(url + "AdminFacadePage.html");
					return url + "AdminFacadePage.html";
//					req.getRequestDispatcher(url + "AdminFacadePage.html").forward(req, res);
				}
			} catch (CouponSystemSingletonException e) {
				e.printStackTrace();
			}
		} else if (user.equals("Company")) {
			try {
				CouponClientFacade sessionFacade = (CompanyFacade) CouponSystemSingleton.getInstance().login(name, password, ClientType.COMPANY);
				if (sessionFacade != null) {
					System.out.println(user);
					session.setAttribute(facade, sessionFacade);
//					res.sendRedirect(url + "CompanyFacadePage.html");
					return url + "CompanyFacadePage.html";
				}
			} catch (CouponSystemSingletonException e) {
				e.printStackTrace();
			}
		} else if (user.equals("Customer")) {
			try {
				CouponClientFacade sessionFacade = (CustomerFacade) CouponSystemSingleton.getInstance().login(name, password, ClientType.CUSTOMER);
				if (sessionFacade != null) {
					System.out.println(user);
					session.setAttribute(facade, sessionFacade);
//					res.sendRedirect(url + "CustomerFacadePage.html");
					return url + "CustomerFacadePage.html";
				}
			} catch (CouponSystemSingletonException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("user type was not selected");
		}

		return null;
	}
}