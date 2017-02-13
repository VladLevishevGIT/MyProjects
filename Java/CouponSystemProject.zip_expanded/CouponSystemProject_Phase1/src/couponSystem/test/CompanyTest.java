package couponSystem.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

import couponSystem.db.javaBeans.Company;
import couponSystem.db.javaBeans.Coupon;
import couponSystem.db.javaBeans.Coupon.CouponType;
import couponSystem.exceptions.CouponSystemExceptions;
import couponSystem.exceptions.facade.CompanyFacadeException;
import couponSystem.exceptions.singleton.CouponSystemSingletonException;
import couponSystem.facades.clients.CompanyFacade;
import couponSystem.singleton.CouponSystemSingleton;
import couponSystem.singleton.CouponSystemSingleton.ClientType;

public class CompanyTest {

	private static CouponSystemSingleton system;
	private static CompanyFacade facade;
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("=== WELCOME TO COUPON SYSTEM COMPANY APPLICATION ===");
		start();
		try {
			login();
			work();
		} catch (CouponSystemSingletonException e) {
			System.out.println(e.getMessage());
		}
		stop();
	}

	private static void start() {

		system = CouponSystemSingleton.getInstance();

	}

	private static void stop() {
		sc.close();
		try {
			system.shutdown();
		} catch (CouponSystemSingletonException e) {
			e.printStackTrace();
		}
	}

	private static void login() throws CouponSystemSingletonException {

		for (int i = 1; i <= 3; i++) {
			System.out.println("login trial " + i + " of 3");
			System.out.print("user: ");
			String user = sc.nextLine();
			System.out.print("password: ");
			String pass = sc.nextLine();
			try {
				facade = (CompanyFacade) system.login(user, pass, ClientType.COMPANY);
				if (facade != null) {
					System.out.println("you are logged in as a company");
					return;
				} else {
					System.out.println("failed login: wrong username or password");
					continue;
				}
			} catch (CouponSystemSingletonException e) {
				System.out.println(e.getMessage());
			}
		}

		throw new CouponSystemSingletonException("you failed to login in 3 trials");
	}

	private static void work() {

		String command;
		lbl: while (true) {
			try {

				showMenu();
				command = sc.nextLine();
				System.out.println();
				switch (command) {
				case "addCoup":
					doAddCoup();
					break;
				case "delCoup":
					doDelCoup();
					break;
				case "updCoup":
					doUpdCoup();
					break;
				case "getAllCoup":
					doRepAllCoup();
					break;
				case "getAllCoupType":
					doGetAllCoupType();
					break;
				case "getAllCoupPrice":
					doGetAllCoupPrice();
					break;
				case "getAllCoupDate":
					doGetAllCoupUntilDate();
					break;
				case "repComp":
					doRepComp();
					break;
				case "getCoup":
					doGetCoupon();
					break;
				case "q":
					System.out.println("exiting");
					break lbl;

				default:
					System.out.println(command + " is not a legal choice");
					break;
				}
			} catch (CouponSystemExceptions e) {
				System.out.println("ERROR !");
				Throwable t = e;
				do {
					System.out.println(t.getMessage());
					t = t.getCause();
				} while (t instanceof CouponSystemExceptions);

				// USE THIS LINE FOR DEBUG
				// e.printStackTrace(System.out);
			} catch (RuntimeException e) {
				System.out.println("ERROR !");
				System.out.println(e);
			}
		}

	}

	private static void showMenu() {
		System.out.println("\n== menu =================================================");
		System.out.println("add coupon ........................ addCoup");
		System.out.println("delete coupon ..................... delCoup");
		System.out.println("update coupon ..................... updCoup");
		System.out.println("get coupon ........................ getCoup");
		System.out.println("get all coupons ................... getAllCoup");
		System.out.println("get all coupons by type ........... getAllCoupType");
		System.out.println("get all coupons by price .......... getAllCoupPrice");
		System.out.println("get all coupons by date ........... getAllCoupDate");
		System.out.println("company report .................... repComp");
		System.out.println("quite ............................. q");
		System.out.print("your choice: ");
	}

	private static void doAddCoup() throws CompanyFacadeException {
		System.out.println("adding a new coupon");
		Coupon coupon = new Coupon();
		System.out.println("enter coupon name: ");
		coupon.setTitle(sc.nextLine());
		System.out.println("enter coupon start date in this format dd-MM-YYYY: ");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date startDate;
		try {
			startDate = dateFormat.parse(sc.nextLine());
			coupon.setStartDate(startDate);
		} catch (ParseException e) {
			System.out.println("Not able to create a coupon with this start date");
		}
		System.out.println("enter coupon end date in this format dd-MM-yyyy: ");
		Date endDate;
		try {
			endDate = dateFormat.parse(sc.nextLine());
			coupon.setEndDate(endDate);
		} catch (ParseException e) {
			System.out.println("Not able to create a coupon with this end date");
		}
		System.out.println("enter coupon amount: ");
		coupon.setAmount(Integer.parseInt(sc.nextLine()));
		System.out.println("enter coupon type: ");
		coupon.setType(CouponType.valueOf(sc.nextLine()));
		System.out.println("enter message: ");
		coupon.setMessage(sc.nextLine());
		System.out.println("enter coupon price: ");
		coupon.setPrice(Double.parseDouble(sc.nextLine()));
		System.out.println("enter coupon image: ");
		coupon.setImage(sc.nextLine());
		System.out.println("processing request... \n");
		facade.createCoupon(coupon);
		System.out.println("coupon added: " + coupon);

	}

	private static void doDelCoup() throws CompanyFacadeException {
		System.out.println("deleting a coupon");
		Coupon coupon = new Coupon();
		System.out.println("enter coupon id: ");
		coupon.setId(Long.parseLong(sc.nextLine()));
		System.out.println("processing request...\n");
		facade.removeCoupon(coupon);
		System.out.println("coupon with id " + coupon.getId() + " deleted");

	}

	private static void doUpdCoup() throws CompanyFacadeException {
		System.out.println("updating a coupon");
		System.out.println("enter coupon id: ");
		long coupId = Long.parseLong(sc.nextLine());
		Coupon coupon = facade.getCoupon(coupId);
		if (coupon.getTitle().equals("")) {
			System.out.println("This coupon does not belong to the company");
		} else {
			System.out.println("coupon current details: " + coupon);
			System.out.println("enter coupon new end date using this format dd-MM-yyyy: ");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date endDate;
			try {
				endDate = dateFormat.parse(sc.nextLine());
				coupon.setEndDate(endDate);
			} catch (ParseException e) {
				System.out.println("could not update the end date");
			}
			System.out.println("enter coupon new price: ");
			coupon.setPrice(Double.parseDouble(sc.nextLine()));
			System.out.println("processing request...\n");
			facade.updateCoupon(coupon);
			System.out.println("coupon updated: " + facade.getCoupon(coupId));
		}
	}

	private static void doRepComp() {
		System.out.println("show company details");
		try {
			Company company = facade.getCompany(facade.getCompId());
			System.out.println("company details: " + company);
		} catch (CompanyFacadeException e) {
			System.out.println("Not able to display company details");
		}
	}

	private static void doRepAllCoup() {
		System.out.println("processing request...\n");
		System.out.println("All company's coupons report: ");
		Collection<Coupon> coupons = new HashSet<>();
		try {
			coupons = facade.getCoupons(facade.getCompany(facade.getCompId()));
			for (Coupon coupon : coupons) {
				System.out.println(coupon);
			}
		} catch (CompanyFacadeException e) {
			System.out.println("Not able to display all coupons");
		}

	}

	private static void doGetAllCoupType() {
		System.out.println("All company's coupons report by type:\n");
		System.out.print("enter coupon's type you want to display: ");
		CouponType type = CouponType.valueOf(sc.nextLine());
		Collection<Coupon> coupons = new HashSet<>();
		try {
			coupons = facade.getCouponByType(facade.getCompany(facade.getCompId()), type);
			for (Coupon coupon : coupons) {
				System.out.println(coupon);
			}
		} catch (CompanyFacadeException e) {
			System.out.println("Not able to display all coupons of this type");
		}

	}

	private static void doGetAllCoupPrice() {
		System.out.println("All company's coupons report by price:\n");
		System.out.println("Enter the maximum price to get all coupons with lower price:");
		Double price = Double.parseDouble(sc.nextLine());
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			coupons = facade.getCouponsCheaperThan(facade.getCompany(facade.getCompId()), price);
			for (Coupon coupon : coupons) {
				System.out.println(coupon);
			}
		} catch (CompanyFacadeException e) {
			System.out.println("Not able to display all coupons with lower prices");
		}

	}

	private static void doGetAllCoupUntilDate() {
		System.out.println("All company's coupons report by date:\n");
		System.out.println("Enter the date (dd-MM-yyyy) to get all coupons with later expiration dates:");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date;
		try {
			Collection<Coupon> coupons = new ArrayList<>();
			date = dateFormat.parse(sc.nextLine());
			coupons = facade.getCouponsUntilDate(facade.getCompany(facade.getCompId()), date);
			for (Coupon coupon : coupons) {
				System.out.println(coupon);
			}
		} catch (ParseException | CompanyFacadeException e1) {
			System.out.println("Not able to display all coupons with later expiration dates");
		}

	}

	private static void doGetCoupon() {
		System.out.println("show coupon details");
		System.out.print("enter coupon id: ");
		long coupId = Long.parseLong(sc.nextLine());
		Coupon coupon;
		try {
			coupon = facade.getCoupon(coupId);
			if (coupon.getTitle().equals("")) {
				System.out.println("coupon with the given ID does not belong to the company");
			} else {
				System.out.println("customer details: " + coupon);				
			}
		} catch (CompanyFacadeException e) {
			System.out.println("Not able to display a coupon with the given ID");
		}
	}
	
}
