package webServices;

import java.io.IOException;
import java.util.Collection;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import couponSystem.db.javaBeans.Coupon;
import couponSystem.db.javaBeans.Coupon.CouponType;
import couponSystem.facades.clients.CustomerFacade;

@WebService
@Path("customerService")
public class CustomerService {

	@Context
	private HttpServletRequest req;

	@Context
	private HttpServletResponse res;

	private CustomerFacade init() throws IOException {
		
		HttpSession session = req.getSession(false);
		if (session != null) {
			return (CustomerFacade) session.getAttribute("facade");
		}else {
			res.sendError(505);
		}
		return null;
	}
	
	@GET
	@Path("buyCoupon/{coupId}")
	public Void buyCoupon(@PathParam("coupId") Long id) throws IOException {
		
		Coupon coupon = new Coupon();
		coupon.setId(id);
		try {
			init().purchaseCoupon(coupon);
		} catch (Exception e) {
			res.sendError(505);
		}
		return null;
	}
	
	@GET
	@Path("getPurchasedCoupons")
	public Collection<Coupon> getPurchasedCoupons() throws IOException {
		
		try {
			init().getAllPurchasedCoupons();
		}  catch (Exception e) {
			res.sendError(505);
		}
		return null;
	}
	
	@GET
	@Path("getCouponsByType/{type}")
	public Collection<Coupon> getCouponsByType(@PathParam("type") String type) throws IOException {
		
		try {
			init().getAllPurchasedCouponsByType(CouponType.valueOf(type));
		} catch (Exception e) {
			res.sendError(505);
		}
		return null;
	}
	
	@GET
	@Path("getCouponsByPrice/{price}")
	public Collection<Coupon> getCouponsByPrice(@PathParam("price") Double price) throws IOException {
		
		try {
			init().getAllPurchasedCouponsByPrice(price);
		} catch (Exception e) {
			res.sendError(505);
		}
		return null;
	}
	
}
