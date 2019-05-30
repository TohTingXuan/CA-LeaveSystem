package sg.nus.iss.mvc.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.mvc.model.Staff;
import sg.nus.iss.mvc.model.LeaveBalance;
import sg.nus.iss.mvc.model.LeaveType;

public interface LeaveBalanceService {
	
	LeaveBalance findByStaffAndLeavetype(Staff Staff, LeaveType leavetype);
	List<LeaveBalance> findByStaff(Staff Staff);
	
	void saveBalanceByStaffAndType(LeaveType leavetype, double bal, Staff Staff);

}
