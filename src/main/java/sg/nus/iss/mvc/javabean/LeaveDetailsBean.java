package sg.nus.iss.mvc.javabean;

public class LeaveDetailsBean {

	private int leaveTypeId;
	private String leaveTypeName;
	private int designationId;
	private String designationName;
	private int maximumLeave;
	
	public LeaveDetailsBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getLeaveTypeName() {
		return leaveTypeName;
	}
	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	
	public int getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(int leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public int getDesignationId() {
		return designationId;
	}
	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}
	public int getMaximumLeave() {
		return maximumLeave;
	}
	public void setMaximumLeave(int maximumleave) {
		this.maximumLeave = maximumleave;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + designationId;
		result = prime * result + leaveTypeId;
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
		LeaveDetailsBean other = (LeaveDetailsBean) obj;
		if (designationId != other.designationId)
			return false;
		if (leaveTypeId != other.leaveTypeId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LeaveDetailsBean [leaveTypeId=" + leaveTypeId + ", leaveTypeName=" + leaveTypeName + ", designationId="
				+ designationId + ", designationName=" + designationName + ", maximumLeave=" + maximumLeave + "]";
	}

	
}
