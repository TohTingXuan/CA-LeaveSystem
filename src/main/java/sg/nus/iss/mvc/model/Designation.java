package sg.nus.iss.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Designation")
public class Designation {
	
	@Id
	@Column(name="id")
	private int id;
	@Column(name="designation_name")
	private String designationName;
	public Designation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((designationName == null) ? 0 : designationName.hashCode());
		result = prime * result + id;
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
		Designation other = (Designation) obj;
		if (designationName == null) {
			if (other.designationName != null)
				return false;
		} else if (!designationName.equals(other.designationName))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
