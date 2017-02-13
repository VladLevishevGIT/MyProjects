package couponSystem.db.javaBeans;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {

	/*
	 * Customer's attributes.
	 */
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	/**
	 * Customer's CTOR.
	 */
	public Customer() {
		super();
	}

	/*
	 * Customer's attributes GET/SET methods.
	 */
	public long getId() {
		return id;
	}

	public String getCustName() {
		return custName;
	}

	public String getPassword() {
		return password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer [id= " + getId() + ", custName= " + getCustName() + ", password= " + getPassword() + "]";
	}

}
