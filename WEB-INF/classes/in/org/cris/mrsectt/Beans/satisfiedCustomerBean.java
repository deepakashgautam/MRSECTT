package in.org.cris.mrsectt.Beans;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class satisfiedCustomerBean {
	private int positive;
	private int total;
	public int getPositive() {
		return positive;
	}
	public void setPositive(int positive) {
		this.positive = positive;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	

}
