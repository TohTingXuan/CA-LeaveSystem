package sg.nus.iss.mvc.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@SuppressWarnings("serial")
public class LeaveBalanceKey implements Serializable{

	private LeaveType leavetype;

	private Staff staff;

	public LeaveBalanceKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveBalanceKey(LeaveType leavetype, Staff Staff) {
		super();
		this.leavetype = leavetype;
		this.staff = Staff;
	}
	
	@Override
	public String toString() {
		return "LeaveBalanceKey [leavetype=" + leavetype + ", Staff=" + staff + "]";
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((leavetype == null) ? 0 : leavetype.hashCode());
		result = prime * result + ((staff == null) ? 0 : staff.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaveBalanceKey other = (LeaveBalanceKey) obj;
		if (leavetype == null) {
			if (other.leavetype != null)
				return false;
		} else if (!leavetype.equals(other.leavetype))
			return false;
		if (staff == null) {
			if (other.staff != null)
				return false;
		} else if (!staff.equals(other.staff))
			return false;
		return true;
	}
	
	
	
}
