package webServices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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

import couponSystem.db.javaBeans.Coupon;
import couponSystem.db.javaBeans.Coupon.CouponType;
import couponSystem.facades.clients.CompanyFacade;

@WebService
@Path("companyService")
public class CompanyService {

	@Context
	private HttpServletRequest req;

	@Context
	private HttpServletResponse res;

	private CompanyFacade init() throws IOException {

		HttpSession session = req.getSession(false);
		if (session != null) {
			return (CompanyFacade) session.getAttribute("facade");
		} else {
			res.sendError(505);
		}
		return null;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("addCoupon")
	public Void addCoupon(Coupon coupon) throws IOException {

		try {
			init().createCoupon(coupon);
		} catch (Exception e) {
			res.sendError(505);
		}

		return null;
	}

	@GET
	@Path("removeCoupon/{coupId}")
	public Void removeCoupon(@PathParam("coupId") Long coupId) throws IOException {

		try {
			init().removeCoupon(init().getCoupon(coupId));
		} catch (Exception e) {
			res.sendError(505);
		}

		return null;
	}

	@GET
	@Path("updateCoupon/{coupID}/{endDate}/{price}")
	public Void updateCoupon(@PathParam("coupID") Long coupId, @PathParam("endDate") String endDateString,
			@PathParam("price") Double price) throws IOException {

		try {
			Coupon coupon = init().getCoupon(coupId);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date endDate = dateFormat.parse(endDateString);
			coupon.setEndDate(endDate);
			coupon.setPrice(price);
			init().updateCoupon(coupon);
		} catch (Exception e) {
			res.sendError(505);
		}

		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCoupon/{coupId}")
	public Coupon getCoupon(@PathParam("coupId") Long coupId) throws IOException {

		HttpSession session = req.getSession(false);
		if (session != null) {
			CompanyFacade facade = new CompanyFacade();
			try {
				return facade.getCoupon(coupId);
			} catch (Exception e) {
				res.sendError(505);
			}
		}
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getAllCoupons")
	public Collection<Coupon> getAllCoupons() throws IOException {

		try {
			return init().getCoupons(init().getCompany(init().getCompId()));
		} catch (Exception e) {
			res.sendError(505);
		}

		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCouponsByType/{type}")
	public Collection<Coupon> getCouponsByType(@PathParam("type") String type) throws IOException {

		try {
			return init().getCouponByType(init().getCompany(init().getCompId()), CouponType.valueOf(type));
		} catch (Exception e) {
			res.sendError(505);
		}
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCouponsByPrice/{price}")
	public Collection<Coupon> getCouponsByPrice(@PathParam("price") Double price) throws IOException {

		try {
			return init().getCouponsCheaperThan(init().getCompany(init().getCompId()), price);
		} catch (Exception e) {
			res.sendError(505);
		}
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCouponsByDate/{date}")
	public Collection<Coupon> getCouponsByDate(@PathParam("date") String date1) throws IOException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = dateFormat.parse(date1);
			return init().getCouponsUntilDate(init().getCompany(init().getCompId()), date);
		} catch (Exception e) {
			res.sendError(505);
		}
		return null;
	}
}
