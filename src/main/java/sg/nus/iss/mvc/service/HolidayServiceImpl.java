package sg.nus.iss.mvc.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.mvc.model.Holiday;
import sg.nus.iss.mvc.model.LeaveApplication;
import sg.nus.iss.mvc.model.LeaveBalance;
import sg.nus.iss.mvc.model.Staff;
import sg.nus.iss.mvc.repo.HolidayRepository;
import sg.nus.iss.mvc.repo.LeaveBalanceRepository;

@Service
public class HolidayServiceImpl implements HolidayService {

	@Resource
	private HolidayRepository holidayRepo;
	@Resource
	private LeaveBalanceRepository leaveBalanceRepo;

	@Override
	@Transactional
	public int findLeaveDaysWithoutHoliday(LocalDate start, LocalDate end) {
		Period p = Period.between(start, end);
		int res = p.getDays()+1;
		LocalDate tempdate = LocalDate.of(start.getYear(), start.getMonthValue(), start.getDayOfMonth());
		//Calendar calendar = Calendar.getInstance();
		List<LocalDate> holidays = holidayRepo.findAllDate();
		
		while(!tempdate.isAfter(end)) {
			
			if(holidays.contains(tempdate)||tempdate.getDayOfWeek().equals(DayOfWeek.SUNDAY)||tempdate.getDayOfWeek().equals(DayOfWeek.SATURDAY))
			{
				res--;
			}
			
			tempdate = tempdate.plusDays(1);
		}
		return res;
	}

	@Override
	@Transactional
	public boolean isBalanceEnough(LeaveApplication la) {
		LeaveBalance lb = leaveBalanceRepo.findByStaffAndLeavetype(la.getStaff(), la.getLeavetype());
		double balance = lb.getBalance();
		int leavedays = findLeaveDaysWithoutHoliday(la.getStartDate(), la.getEndDate());
		if (la.getLeavetype().getTypeId() == 3 || la.getLeavetype().getTypeId() == 4) {
			return (balance>0);
		}
		else if (leavedays > balance) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public List<LocalDate> findAllDate() {
		return holidayRepo.findAllDate();
	}

	@Override
	@Transactional
	public boolean isWorkingDay(LeaveApplication la) {
		LocalDate start = la.getStartDate();
		LocalDate end = la.getEndDate();
		// LocalDate tempdate = LocalDate.of(start.getYear(), start.getMonthValue(),
		// start.getDayOfMonth());
		List<LocalDate> holidays = holidayRepo.findAllDate();
		// while (!tempdate.isAfter(end)) {
		if (holidays.contains(start) || start.getDayOfWeek().equals(DayOfWeek.SUNDAY)
				|| start.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			return false;
		} else if (holidays.contains(end) || end.getDayOfWeek().equals(DayOfWeek.SUNDAY)
				|| end.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			return false;
		}
		// }
		// tempdate = tempdate.plusDays(1);
		// }
		else {
			return true;
		}

	}
}
