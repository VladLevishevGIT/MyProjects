package couponSystem.db.javaBeans;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Coupon {

	/**
	 * Coupon's attributes.
	 */
	public static enum CouponType {
		RESTURANTS, ELECTRICITY, FOOD, HEALTH, SPORTS, CAMPING, TRAVELLING;
	}

	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	/**
	 * Coupon's CTOR.
	 */
	public Coupon() {
		super();
	}

	/**
	 * Coupon's attributes GET/SET methods.
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (obj == null) {
//			return false;
//		}
//		if (!(obj instanceof Coupon)) {
//			return false;
//		}
//		final Coupon other = (Coupon) obj;
//		if (this.id != other.id) {
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public int hashCode() {
//
//		return super.hashCode();
//	}
	@Override
	public String toString() {

		return "Coupon [id= " + getId() + ", title= " + getTitle() + ", startDate= " + getStartDate() + ", endDate= "
				+ getEndDate() + ", amount= " + getAmount() + ", couponType= " + getType() + ", message= "
				+ getMessage() + ", price= " + getPrice() + ", image= " + getImage() + "]";

	}

	
}
