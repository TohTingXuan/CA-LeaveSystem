package sg.nus.iss.mvc.service;

import java.time.LocalDate;
import java.util.List;

import sg.nus.iss.mvc.model.LeaveApplication;

public interface HolidayService {

	int findLeaveDaysWithoutHoliday(LocalDate start, LocalDate end);

	List<LocalDate> findAllDate();

	boolean isWorkingDay(LeaveApplication la);

	//boolean isBalanceEnough(LeaveApplication la, int balance);

	boolean isBalanceEnough(LeaveApplication la);
}
