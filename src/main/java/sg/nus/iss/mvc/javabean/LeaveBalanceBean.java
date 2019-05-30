package sg.nus.iss.mvc.javabean;

public class LeaveBalanceBean {
	
	private int leavetypeId;
	private String leavetypeName;
	private int staffId;
	private String staffName;
	private double leaveBalance;
	public LeaveBalanceBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(leaveBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + leavetypeId;
		result = prime * result + ((leavetypeName == null) ? 0 : leavetypeName.hashCode());
		result = prime * result + staffId;
		result = prime * result + ((staffName == null) ? 0 : staffName.hashCode());
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
		LeaveBalanceBean other = (LeaveBalanceBean) obj;
		if (Double.doubleToLongBits(leaveBalance) != Double.doubleToLongBits(other.leaveBalance))
			return false;
		if (leavetypeId != other.leavetypeId)
			return false;
		if (leavetypeName == null) {
			if (other.leavetypeName != null)
				return false;
		} else if (!leavetypeName.equals(other.leavetypeName))
			return false;
		if (staffId != other.staffId)
			return false;
		if (staffName == null) {
			if (other.staffName != null)
				return false;
		} else if (!staffName.equals(other.staffName))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "LeaveBalanceBean [leavetypeId=" + leavetypeId + ", leavetypeName=" + leavetypeName + ", staffId="
				+ staffId + ", staffName=" + staffName + ", leaveBalance=" + leaveBalance + "]";
	}
	public int getLeavetypeId() {
		return leavetypeId;
	}
	public void setLeavetypeId(int leavetypeId) {
		this.leavetypeId = leavetypeId;
	}
	public String getLeavetypeName() {
		return leavetypeName;
	}
	public void setLeavetypeName(String leavetypeName) {
		this.leavetypeName = leavetypeName;
	}
	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public double getLeaveBalance() {
		return leaveBalance;
	}
	public void setLeaveBalance(double d) {
		this.leaveBalance = d;
	}
	
	
}
