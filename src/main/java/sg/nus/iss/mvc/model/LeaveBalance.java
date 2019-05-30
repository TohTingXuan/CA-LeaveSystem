package sg.nus.iss.mvc.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="leave_balance")
@IdClass(LeaveBalanceKey.class)
public class LeaveBalance implements Serializable{

	@Id
	@ManyToOne
	@JoinColumn(name="leavetype_id")
	private LeaveType leavetype;
	@Id
	@ManyToOne
	@JoinColumn(name="staff_id")
	private Staff staff;
	
	@Column(name="leave_available")
	private double balance;
	
	public LeaveBalance() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LeaveBalance(LeaveType leavetype, Staff Staff, double balance) {
		super();
		this.leavetype = leavetype;
		this.staff = Staff;
		this.balance = balance;
	}
	public LeaveType getLeavetype() {
		return leavetype;
	}
	public void setLeavetype(LeaveType leavetype) {
		this.leavetype = leavetype;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff Staff) {
		this.staff = Staff;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "LeaveBalance [leavetype=" + leavetype + ", Staff=" + staff + ", balance=" + balance + "]";
	}
	
	
}
