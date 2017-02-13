package webServices;

import java.io.IOException;
import java.util.Collection;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import couponSystem.db.javaBeans.Company;
import couponSystem.db.javaBeans.Customer;
import couponSystem.facades.clients.AdminFacade;

@WebService
@Path("adminService")
public class AdminService {

	@Context
	private HttpServletRequest req;

	@Context
	private HttpServletResponse res;

	private AdminFacade init() throws IOException {

		HttpSession session = req.getSession(false);
		if (session != null) {
			System.out.println("GOOD");
			return (AdminFacade) session.getAttribute("facade");
		} else {
			res.sendError(505);
		}
		return null;
	}

	@Path("getAllCompanies")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getAllCompanies() throws IOException {

		try {
			return init().getAllCompanies();
		} catch (Exception e) {
			res.sendError(505);
		}
		return null;
	}

	@Path("createCompany")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Void createCompany(Company company) throws IOException {

		try {
			init().createCompany(company);
		} catch (Exception e2) {
			res.sendError(505);
		}
		return null;
	}

	@GET
	@Path("removeCompany/{compId}")
	public Void removeCompany(@PathParam("compId") Long compId) throws IOException {

		HttpSession session = req.getSession(false);
		if (session != null) {
			AdminFacade facade = new AdminFacade();
			try {
				Company company = facade.getCompany(compId);
				facade.removeCompany(company);
			} catch (Exception e) {
				res.sendError(505);
			}
		}
		return null;
	}

	@Path("getCompany/{compId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@PathParam("compId") Long compId) throws IOException {

		HttpSession session = req.getSession(false);
		if (session != null) {
			AdminFacade facade = new AdminFacade();
			try {
				return facade.getCompany(compId);
			} catch (Exception e) {
				res.sendError(505);
			}
		}
		return null;
	}

	@Path("updateCompany/{compId}/{compName}/{compEmail}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Company updateCompany(@PathParam("compId") Long compId, @PathParam("compName") String compName,
			@PathParam("compEmail") String compEmail) throws IOException {

		HttpSession session = req.getSession(false);
		if (session != null) {
			AdminFacade facade = new AdminFacade();
			try {
				Company company = facade.getCompany(compId);
				company.setEmail(compEmail);
				facade.updateCompany(company);
			} catch (Exception e) {
				res.sendError(505);
			}
		}
		return null;
	}

	@Path("getAllCustomers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAllCustomers() throws IOException {

		try {
			return init().getAllCustomers();
		} catch (Exception e) {
			res.sendError(505);
		}
		return null;
	}

	@Path("createCustomer")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Void createCustomer(Customer customer) throws IOException {

		try {
			init().createCustomer(customer);
		} catch (Exception e2) {
			res.sendError(505);
		}
		return null;
	}

	@GET
	@Path("removeCustomer/{customerId}")
	public Void removeCustomer(@PathParam("customerId") Long customerId) throws IOException {

		try {
			Customer customer = init().getCustomer(customerId);
			init().removeCustomer(customer);
		} catch (Exception e2) {
			res.sendError(505);
		}
		return null;
	}

	@Path("getCustomer/{customerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("customerId") Long customerId) throws IOException {

		try {
			return init().getCustomer(customerId);
		} catch (Exception e2) {
			res.sendError(505);
		}
		return null;
	}

	@Path("updateCustomer/{customerId}/{customerName}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Void updateCustomer(@PathParam("customerId") Long customerId, @PathParam("customerName") String customerName)
			throws IOException {

		try {
			Customer customer = init().getCustomer(customerId);
			customer.setCustName(customerName);
			init().updateCustomer(customer);
		} catch (Exception e2) {
			res.sendError(505);
		}
		return null;
	}
}
