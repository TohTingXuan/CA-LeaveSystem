package sg.nus.iss.mvc.service;

import java.util.List;

import sg.nus.iss.mvc.javabean.LeaveBalanceBean;
import sg.nus.iss.mvc.javabean.LeaveDetailsBean;
import sg.nus.iss.mvc.model.LeaveBalance;
import sg.nus.iss.mvc.model.LeaveType;
import sg.nus.iss.mvc.model.Staff;

public interface LeaveTypeService {

	LeaveDetailsBean createDetailsBean(Integer leavetypeid, Integer designationid);
	void saveAndPopulate(LeaveType leavetype);
	void deleteType(Integer leavetypeid); 
	void updateSaveDetails(LeaveDetailsBean ldbean);
	List<LeaveType> getLeaveTypeList();
	List<Staff> getAllStaff();
	Staff findStaff(int staffid);
	List<LeaveBalance> getLeaveBalanceList();
	LeaveBalance getSingleLeaveBalance(Integer staffid, Integer leavetypeid);
	void updateLeaveBalance(LeaveBalanceBean leavebalance);
	LeaveBalanceBean createBalanceBean(Integer staffid, Integer leavetypeid);
	
	
}