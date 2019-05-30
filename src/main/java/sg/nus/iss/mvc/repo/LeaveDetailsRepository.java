package sg.nus.iss.mvc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.mvc.model.Designation;
import sg.nus.iss.mvc.model.LeaveDetails;
import sg.nus.iss.mvc.model.LeaveDetailsId;
import sg.nus.iss.mvc.model.LeaveType;

public interface LeaveDetailsRepository extends JpaRepository<LeaveDetails, LeaveDetailsId>{

	public LeaveDetails findByLeaveTypeAndDesignation(LeaveType leavetype, Designation designation);
	public List<LeaveDetails> findByLeaveType(LeaveType leavetype);
	
	@Transactional
	@Modifying
	@Query(value = 
			"UPDATE LeaveDetails l SET l.maximumLeave = :maxleave WHERE l.leaveType.typeId = :leavetypeid AND l.designation.id = :designationid")
	public void updateAndSave(@Param("leavetypeid") int leavetypeid, @Param("designationid") int designationid, 
			@Param("maxleave") int maxleave);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO designation_leave VALUES (:designationid, :leavetypeid, 0 )",
	nativeQuery = true)
	public void addNewLeaveType( @Param("designationid") int designationid, @Param("leavetypeid") int leavetypeid);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM LeaveDetails ld where ld.leaveType.typeId = :leavetypeid")
	void deleteDetailsForDeletedLeaveType(@Param("leavetypeid") int leavetypeid);
}
