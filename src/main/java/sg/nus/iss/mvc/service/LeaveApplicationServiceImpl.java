package sg.nus.iss.mvc.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.mvc.model.LeaveApplication;
import sg.nus.iss.mvc.model.Staff;
import sg.nus.iss.mvc.repo.LeaveApplicationRepository;
import sg.nus.iss.mvc.repo.LeaveBalanceRepository;
import sg.nus.iss.mvc.repo.LeaveTypeRepository;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

	@Resource
	private LeaveApplicationRepository leaveApplicationRepo;

	@Override
	@Transactional
	public List<LeaveApplication> findLeaveByStaff(Optional<Staff> staff) {
		return leaveApplicationRepo.findLeaveByStaff(staff);
	}

	@Override
	@Transactional
	public List<LeaveApplication> findEmployeeByid(int id) {
		return leaveApplicationRepo.findEmployeeByid(id);
	}

	@Override
	@Transactional
	public LeaveApplication findByid(int id) {
		return leaveApplicationRepo.findByid(id);
	}

	@Override
	@Transactional
	public List<LeaveApplication> checkOverlapLeave(LocalDate startdate, LocalDate enddate, int staffid) {
		return leaveApplicationRepo.checkOverlapLeave(startdate, enddate, staffid);
	}

	@Override
	@Transactional
	public boolean overlapWithHistory(LeaveApplication la) {
		String s= "APPLIED";
		List<LeaveApplication> las = leaveApplicationRepo.findByStaffAccordingStatus(la.getStaff().getStaffId());
		LocalDate start = la.getStartDate();
		LocalDate end = la.getEndDate();
		for (LeaveApplication leavr_app : las) {
			if ((!start.isBefore(leavr_app.getStartDate()) && !start.isAfter(leavr_app.getEndDate()))
					|| (!end.isBefore(leavr_app.getStartDate()) && !end.isAfter(leavr_app.getEndDate()))) {
				return true;
			}
		}
		return false;
	}
	@Override	
	public List<LeaveApplication> findAll() {
		return leaveApplicationRepo.findAll(); 
	}

	
	public List<LeaveApplication> getleavelist(LocalDate startDate,LocalDate endDate) {
		// TODO Auto-generated method stub
		return leaveApplicationRepo.getleavelist(startDate, endDate);
	}

}
