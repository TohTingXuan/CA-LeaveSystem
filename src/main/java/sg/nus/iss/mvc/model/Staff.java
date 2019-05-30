package sg.nus.iss.mvc.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

@Entity
public class Staff {

	@Id
	@Column(name="id")
	private int staffId;
	@NotEmpty
	@Size(min = 2, max =50, message="Staff name must be between {min} and {max}.")
	@Column(name="name")
	private String staffName;
	@OneToOne
	@JoinColumn(name="designation_id")
	private Designation designation;
	
	@Length(min = 3, message="password must be more than {min} characters.")
	private String password;
	@OneToMany(targetEntity = LeaveApplication.class, mappedBy = "staff")
	private Collection<LeaveApplication> leave_application;

	@Column(name="email")
	private String email;
	
	@OneToOne
	@JoinColumn(name="boss_id")
	private Staff staff;
	
	//CONSTRUCTORS
	public Staff() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Staff(String staff_name,Designation designation, String password) {
		super();
		this.staffName = staff_name;
		this.designation = designation;
		this.password = password;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//GETTERS & SETTERS
	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<LeaveApplication> getLeave_application() {
		return leave_application;
	}

	public void setLeave_application(Collection<LeaveApplication> leave_application) {
		this.leave_application = leave_application;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
}
