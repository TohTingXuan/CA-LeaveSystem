package sg.nus.iss.mvc.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="designation_leave")
@IdClass(LeaveDetailsId.class)
public class LeaveDetails implements Serializable{
	
	@Id
	@ManyToOne
	@JoinColumn(name="leavetype_id")
	private LeaveType leaveType;
	@Id
	@ManyToOne
	@JoinColumn(name="designation_id")
	private Designation designation;
	
	@Column(name="maximum_leave")
	private int maximumLeave;

	
	
	public LeaveDetails() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + ((leaveType == null) ? 0 : leaveType.hashCode());
		result = prime * result + maximumLeave;
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
		LeaveDetails other = (LeaveDetails) obj;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		if (leaveType == null) {
			if (other.leaveType != null)
				return false;
		} else if (!leaveType.equals(other.leaveType))
			return false;
		if (maximumLeave != other.maximumLeave)
			return false;
		return true;
	}


	public LeaveType getLeaveType() {
		return leaveType;
	}


	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}


	public Designation getDesignation() {
		return designation;
	}


	public void setDesignation(Designation designation) {
		this.designation = designation;
	}


	public int getMaximumLeave() {
		return maximumLeave;
	}

	public void setMaximumLeave(int maximumLeave) {
		this.maximumLeave = maximumLeave;
	}
	
	
	
}
