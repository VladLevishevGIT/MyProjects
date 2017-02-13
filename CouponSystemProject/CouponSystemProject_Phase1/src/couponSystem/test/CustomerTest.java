package couponSystem.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

import couponSystem.db.javaBeans.Coupon;
import couponSystem.db.javaBeans.Coupon.CouponType;
import couponSystem.exceptions.CouponSystemExceptions;
import couponSystem.exceptions.facade.CustomerFacadeException;
import couponSystem.exceptions.singleton.CouponSystemSingletonException;
import couponSystem.facades.clients.CustomerFacade;
import couponSystem.singleton.CouponSystemSingleton;
import couponSystem.singleton.CouponSystemSingleton.ClientType;

public class CustomerTest {

	private static CouponSystemSingleton system;
	private static CustomerFacade facade;
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("=== WELCOME TO COUPON SYSTEM CUSTOMER APPLICATION ===");
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

	private static void login() throws CouponSystemSingletonException {
		for (int i = 1; i <= 3; i++) {
			System.out.println("login trial " + i + " of 3");
			System.out.print("user: ");
			String user = sc.nextLine();
			System.out.print("password: ");
			String pass = sc.nextLine();
			try {
				facade = (CustomerFacade) system.login(user, pass, ClientType.CUSTOMER);
				if (facade != null) {
					System.out.println("you are logged in as a customer");
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
				case "purCoup":
					doPurCoup();
					break;
				case "getAllPurCoup":
					doGetAllPurCoup();
					break;
				case "getAllPurCoupType":
					doGetAllPurCoupType();
					break;
				case "getAllPurCoupPrice":
					doGetAllPurCoupPrice();
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

	private static void stop() {
		sc.close();
		try {
			system.shutdown();
		} catch (CouponSystemSingletonException e) {
			e.printStackTrace();
		}
	}

	private static void showMenu() {
		System.out.println("\n== menu =================================================");
		System.out.println("purchase coupon ........................ purCoup");
		System.out.println("get all purchased coupons .............. getAllPurCoup");
		System.out.println("get all purchased coupons by type ...... getAllPurCoupType");
		System.out.println("get all purchased coupons by price ..... getAllPurCoupPrice");
		System.out.println("quit .................. q");
		System.out.print("your choice: ");
	}

	private static void doPurCoup() throws CustomerFacadeException {
		System.out.println("you are going to purchase new coupon");
		System.out.print("enter coupon id you want to purchase: ");
		long coupId = Long.parseLong(sc.nextLine());
		Coupon coupon = new Coupon();
		coupon.setId(coupId);
		facade.purchaseCoupon(coupon);
		System.out.println("Coupon with ID: " + coupId + " was purcased by customer: " + facade.getCustId());

	}

	private static void doGetAllPurCoup() throws CustomerFacadeException {
		System.out.println("all customer's coupons report:\n");
		Collection<Coupon> coupons = new HashSet<>();
		try {
			coupons = facade.getAllPurchasedCoupons();
			for (Coupon coupon : coupons) {
				System.out.println(coupon);
			}
		} catch (CustomerFacadeException e) {
			System.out.println("Can not get all purchased coupons");
		}
	}

	private static void doGetAllPurCoupType() throws CustomerFacadeException {
		System.out.println("all customer's coupons report by type:\n");
		System.out.print("enter coupons type you want to choose: ");
		CouponType type = CouponType.valueOf(sc.nextLine());
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			coupons = facade.getAllPurchasedCouponsByType(type);
			for (Coupon coupon : coupons) {
				System.out.println(coupon);
			}
		} catch (CustomerFacadeException e) {
			System.out.println("Cann't get all purchased coupons (by type)");
		}
	}

	private static void doGetAllPurCoupPrice() throws CustomerFacadeException {
		System.out.println("all customer's coupons report by price:\n");
		System.out.print("enter maximum price of purchased coupons: ");
		Double price = Double.parseDouble(sc.nextLine());
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			coupons = facade.getAllPurchasedCouponsByPrice(price);
			for (Coupon coupon : coupons) {
				System.out.println(coupon);
			}
		} catch (CustomerFacadeException e) {
			System.out.println("Cann't get all purchased coupons (by price)");
		}
	}

}