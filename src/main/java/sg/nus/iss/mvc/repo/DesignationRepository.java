package sg.nus.iss.mvc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.mvc.model.Designation;
import sg.nus.iss.mvc.model.LeaveDetails;
import sg.nus.iss.mvc.model.LeaveDetailsId;

public interface DesignationRepository extends JpaRepository<Designation, Integer>{

}
