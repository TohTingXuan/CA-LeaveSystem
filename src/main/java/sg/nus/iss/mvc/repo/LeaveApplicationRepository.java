package sg.nus.iss.mvc.repo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.mvc.model.LeaveApplication;
import sg.nus.iss.mvc.model.Staff;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication,Integer>{

	public List<LeaveApplication> findLeaveByStaff(Optional<Staff> s);
	
	public List<LeaveApplication> findByStaff(Staff s);
	
	@Query("SELECT la FROM LeaveApplication la WHERE la.startDate >= :startDate AND la.endDate <= :endDate")
	List<LeaveApplication> getleavelist(LocalDate startDate,LocalDate endDate);
	
	@Transactional
	@Query("SELECT la from LeaveApplication la where la.staff.staffId = :staffid and la.status in ('APPROVED','APPLIED')")
	public List<LeaveApplication> findByStaffAccordingStatus(@Param("staffid") int staffid);
	
	@Transactional
	@Query("SELECT la FROM Staff s, LeaveApplication la WHERE s.staffId = la.staff.staffId AND s.staff.staffId = ?1 AND la.status='APPLIED'")
	List<LeaveApplication> findSubordinateByBossId(int bossId);
	
	@Transactional
	@Query("SELECT la FROM Staff s, LeaveApplication la WHERE s.staffId = la.staff.staffId AND s.staff.staffId = ?1")
	public List<LeaveApplication> findEmployeeByid(int id);
	
	public LeaveApplication findByid(int id);
	
	@Query(value= "select u from LeaveApplication u where u.endDate >= :startdate  and  u.startDate <= :enddate and"
			+ " u.staff in (select s.id from Staff s where s.staff.staffId = :staffid)")
	List<LeaveApplication> checkOverlapLeave (@Param("startdate") LocalDate startdate, @Param("enddate") LocalDate enddate,
			@Param("staffid") int staffid);
	
	@Query(value= "select la from LeaveApplication la where month(la.endDate) = :month  and  month(la.startDate) = :month "
			+ "and la.status='APPROVED'")
	List<LeaveApplication> checkMonthLeave (@Param("month") int month);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM LeaveApplication la where la.staff.staffId = :staffid")
	void deleteLeaveForDeletedStaff(@Param("staffid") int staffid);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM LeaveApplication la where la.leavetype.typeId = :leavetypeid")
	public void deleteApplicationForDeletedLeaveType(@Param("leavetypeid") Integer leavetypeid);
	
}