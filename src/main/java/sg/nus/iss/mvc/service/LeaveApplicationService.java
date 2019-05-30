package sg.nus.iss.mvc.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.mvc.model.LeaveApplication;
import sg.nus.iss.mvc.model.Staff;

public interface LeaveApplicationService {

	List<LeaveApplication> findEmployeeByid(int id);

	LeaveApplication findByid(int id);

	List<LeaveApplication> checkOverlapLeave(LocalDate startdate, LocalDate enddate, int staffid);

	//boolean overlapWithHistory(LeaveApplication la, Staff s);

	List<LeaveApplication> findLeaveByStaff(Optional<Staff> staff);

	//boolean overlapWithHistory(LeaveApplication la, Optional<Staff> s);

	boolean overlapWithHistory(LeaveApplication la);
	
	List<LeaveApplication> findAll();
	 
	 
	 //boolean createCsv(List<LeaveApplication> leaveApplicationList,ServletContext context);


	 List<LeaveApplication> getleavelist(LocalDate startDate, LocalDate endDate);

}