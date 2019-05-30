package sg.nus.iss.mvc.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.mvc.model.Staff;
import sg.nus.iss.mvc.model.LeaveBalance;
import sg.nus.iss.mvc.model.LeaveType;
import sg.nus.iss.mvc.repo.LeaveBalanceRepository;
import sg.nus.iss.mvc.repo.LeaveTypeRepository;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService{
	
	@Resource
	private LeaveBalanceRepository leaveBalanceRepo;
	@Resource
	private LeaveTypeRepository leaveTypeRepo;
	
	@Override
	@Transactional
	public LeaveBalance findByStaffAndLeavetype(Staff Staff, LeaveType leavetype) {
		LeaveBalance bal = leaveBalanceRepo.findByStaffAndLeavetype(Staff, leavetype);
		return bal;
	}
	
	@Override
	@Transactional
	public void saveBalanceByStaffAndType(LeaveType leavetype, double bal, Staff Staff) {
		leaveBalanceRepo.saveBalanceByStaffAndType(leavetype.getTypeId(), bal, Staff.getStaffId());
	}
	
	@Override
	@Transactional
	public List<LeaveBalance> findByStaff(Staff Staff) {
		List<LeaveBalance> bal = leaveBalanceRepo.findByStaff(Staff);
		return bal;
	}
}
