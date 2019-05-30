package sg.nus.iss.mvc.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.nus.iss.mvc.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Integer>{
	//change to Integer

	public Staff findByStaffName(String staffname);
	public Staff findTopByOrderByStaffIdDesc();
	
	@Transactional
	@Query("SELECT s FROM Staff s where s.staff.staffId = ?1")
	public List<Staff> findHistoryByid(int id);
	
	@Query(value="SELECT s FROM Staff s WHERE s.id IN (SELECT DISTINCT s.staff.staffId FROM Staff s)")
	public List<Staff> getBossList();
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Staff s where s.staffId = :staffid")
	void deleteStaff(@Param("staffid") int staffid);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Staff s SET s.staff.staffId = 1 WHERE s.staff.staffId = :staffid")
	void removeBossFromSubordinates(@Param("staffid") int bossid);

	@Transactional
	@Query("SELECT s FROM Staff s where s.staffId = ?1")
	public Staff findByStaffId(int id);
	
}
