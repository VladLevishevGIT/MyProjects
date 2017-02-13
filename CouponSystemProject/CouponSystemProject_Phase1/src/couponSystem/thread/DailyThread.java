package couponSystem.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import couponSystem.db.dao.dbdao.CouponDBDAO;
import couponSystem.db.javaBeans.Coupon;
import couponSystem.exceptions.dao.CouponDAOException;

public class DailyThread implements Runnable {

	/**
	 * The "run" method for daily coupons: checking "END DATE" of coupons. 
	 * If the coupon already expired it is immediately removed from the system.
	 */
	@Override
	public void run() {

		CouponDBDAO coupon = new CouponDBDAO();
		Collection<Coupon> coupons = new ArrayList<>();
		Date date = new Date();

		try {
			while (!Thread.currentThread().isInterrupted()) {
				coupons = coupon.getAllCoupons();
				for (Coupon curr : coupons) {

					if (curr.getEndDate().before(date)) {
						CouponDBDAO couponDBDAO = new CouponDBDAO();
						couponDBDAO.removeCoupon(curr);
					}

				}
				Thread.sleep(1000*60*60*24);
			}

		} catch (CouponDAOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("DailyThread: INTERRUPTED!");
		}

	}

}
