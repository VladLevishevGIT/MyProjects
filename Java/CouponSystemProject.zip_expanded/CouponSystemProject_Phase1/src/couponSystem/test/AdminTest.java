package couponSystem.test;

import java.util.Scanner;

import couponSystem.db.javaBeans.Company;
import couponSystem.db.javaBeans.Customer;
import couponSystem.exceptions.CouponSystemExceptions;
import couponSystem.exceptions.facade.AdminFacadeException;
import couponSystem.exceptions.singleton.CouponSystemSingletonException;
import couponSystem.facades.clients.AdminFacade;
import couponSystem.singleton.CouponSystemSingleton;
import couponSystem.singleton.CouponSystemSingleton.ClientType;

public class AdminTest {

	private static CouponSystemSingleton system;
	private static AdminFacade facade;
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("=== WELCOME TO COUPON SYSTEM ADMIN APPLICATION ===");
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
				facade = (AdminFacade) system.login(user, pass, ClientType.ADMIN);
				System.out.println("you are logged in as system administrator");
				return;
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
				case "addComp":
					doAddCompany();
					break;
				case "delComp":
					doDelCompany();
					break;
				case "updComp":
					doUpdateCompany();
					break;
				case "repAllComp":
					doRepAllCompany();
					break;
				case "repComp":
					doRepCompany();
					break;
				// ==================
				case "addCust":
					doAddCustomer();
					break;
				case "delCust":
					doDeleteCustomer();
					break;
				case "updCust":
					doUpdateCustomer();
					break;
				case "repAllCust":
					doRepAllCustomers();
					break;
				case "repCust":
					doRepCustomer();
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
		System.out.println("\n== menu ==========================");
		System.out.println("add company ............ addComp");
		System.out.println("delete company ......... delComp");
		System.out.println("update company ......... updComp");
		System.out.println("all company report ..... repAllComp");
		System.out.println("company report ......... repComp");
		System.out.println("add client ............. addCust");
		System.out.println("delete client .......... delCust");
		System.out.println("update client .......... updCust");
		System.out.println("all client report ...... repAllCust");
		System.out.println("client report .......... repCust");
		System.out.println("quit ................... q");
		System.out.print("your choice: ");
	}

	private static void doRepCustomer() throws AdminFacadeException {
		System.out.println("show customer detalis");
		System.out.print("enter customer id: ");
		long custId = Long.parseLong(sc.nextLine());
		Customer customer = facade.getCustomer(custId);
		System.out.println("customer details: " + customer);
	}

	private static void doRepAllCustomers() throws AdminFacadeException {
		System.out.println("processing request...\n");
		System.out.println("all customer report:");
		for (Customer curr : facade.getAllCustomers()) {
			System.out.println(curr);
		}

	}

	private static void doUpdateCustomer() throws AdminFacadeException {
		System.out.println("updating a customer");
		System.out.print("enter customer id: ");
		long custId = Long.parseLong(sc.nextLine());
		Customer customer = facade.getCustomer(custId);
		System.out.println("customer current details: " + customer);
		System.out.print("enter customer new password: ");
		customer.setPassword(sc.nextLine());
		System.out.println("processing request...\n");
		facade.updateCustomer(customer);
		System.out.println("customer updated: " + customer);
	}

	private static void doDeleteCustomer() throws AdminFacadeException {
		System.out.println("deleting a customer");
		Customer customer = new Customer();
		System.out.print("enter customer id: ");
		customer.setId(Long.parseLong(sc.nextLine()));
		System.out.println("processing request...\n");
		facade.removeCustomer(customer);
		System.out.println("customer with id " + customer.getId() + " deleted");
	}

	private static void doAddCustomer() throws AdminFacadeException {
		System.out.println("adding a new customer");
		Customer customer = new Customer();
		System.out.print("enter customer name: ");
		customer.setCustName(sc.nextLine());
		System.out.print("enter customer password: ");
		customer.setPassword(sc.nextLine());
		System.out.println("processing request...\n");
		facade.createCustomer(customer);
		System.out.println("customer added: " + customer);
	}

	private static void doRepCompany() throws AdminFacadeException {
		System.out.println("show company detalis");
		System.out.print("enter company id: ");
		long compId = Long.parseLong(sc.nextLine());
		Company company = facade.getCompany(compId);
		System.out.println("company details: " + company);
	}

	private static void doRepAllCompany() throws AdminFacadeException {
		System.out.println("processing request...\n");
		System.out.println("all company report:");
		for (Company curr : facade.getAllCompanies()) {
			System.out.println(curr);
		}
	}

	private static void doUpdateCompany() throws AdminFacadeException {
		System.out.println("updating a company");
		System.out.print("enter company id: ");
		long compId = Long.parseLong(sc.nextLine());
		Company company = facade.getCompany(compId);
		System.out.println("company current details: " + company);
		System.out.print("enter company new email: ");
		company.setEmail(sc.nextLine());
		System.out.print("enter company new password: ");
		company.setPassword(sc.nextLine());
		System.out.println("processing request...\n");
		facade.updateCompany(company);
		System.out.println("company updated: " + company);
	}

	private static void doDelCompany() throws AdminFacadeException {
		System.out.println("deleting a company");
		Company company = new Company();
		System.out.print("enter company id: ");
		company.setId(Long.parseLong(sc.nextLine()));
		System.out.println("processing request...\n");
		facade.removeCompany(company);
		System.out.println("company with id " + company.getId() + " deleted");
	}

	private static void doAddCompany() throws AdminFacadeException {
		System.out.println("adding a new company");
		Company company = new Company();
		System.out.print("enter company name: ");
		company.setCompName(sc.nextLine());
		System.out.print("enter company password: ");
		company.setPassword(sc.nextLine());
		System.out.print("enter company email: ");
		company.setEmail(sc.nextLine());
		System.out.println("processing request...\n");
		facade.createCompany(company);
		System.out.println("company added: " + company);
	}

}