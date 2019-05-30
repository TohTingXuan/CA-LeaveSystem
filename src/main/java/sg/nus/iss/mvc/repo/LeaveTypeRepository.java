package sg.nus.iss.mvc.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.nus.iss.mvc.model.LeaveType;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType,Integer>{

	public LeaveType findByTypeId(int id);
	public LeaveType findTopByOrderByTypeIdDesc();
}
