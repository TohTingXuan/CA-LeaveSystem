package sg.nus.iss.mvc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.mvc.model.Staff;
import sg.nus.iss.mvc.model.LeaveBalance;
import sg.nus.iss.mvc.model.LeaveBalanceKey;
import sg.nus.iss.mvc.model.LeaveType;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance,LeaveBalanceKey>{

	LeaveBalance findByStaffAndLeavetype(Staff staff, LeaveType lt);
	List<LeaveBalance> findByStaff(Staff staff);
	
	@Transactional
	@Modifying
	@Query("update LeaveBalance lb set lb.balance = :bal where lb.staff.staffId = :staffid and lb.leavetype.typeId = :leavetypeid")
	void saveBalanceByStaffAndType(@Param("leavetypeid") int leavetypeid, @Param("bal") double bal, @Param("staffid") int staffid);
	
	@Transactional
	@Modifying
	@Query(value = "delete from LeaveBalance lb where lb.leavetype.typeId = :leavetypeid")
	void deleteBalanceForDeletedLeaveType(@Param("leavetypeid") int leavetypeid);
	
	@Transactional
	@Modifying
	@Query(value = "delete from LeaveBalance lb where lb.staff.staffId = :staffid")
	void deleteBalanceForDeletedStaff(@Param("staffid") int staffid);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO leave_balance VALUES (:leavetypeid, :staffid, 0 )",
	nativeQuery = true)
	void addStaffNewLeaveBalance(@Param("staffid") int staffid, @Param("leavetypeid") int leavetypeid);
	
	@Transactional
	@Modifying
	@Query(value = 
			"UPDATE LeaveBalance lb SET lb.balance = :leavebalance WHERE lb.leavetype.typeId = :leavetypeid AND lb.staff.staffId = :staffid")
	void updateAndSave(@Param("leavetypeid") int leavetypeId, @Param("staffid") int staffId, @Param("leavebalance") double d);
	
}
