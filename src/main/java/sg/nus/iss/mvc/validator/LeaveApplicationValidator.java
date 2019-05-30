package sg.nus.iss.mvc.validator;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.nus.iss.mvc.model.LeaveApplication;
import sg.nus.iss.mvc.service.HolidayService;
import sg.nus.iss.mvc.service.LeaveApplicationService;
import sg.nus.iss.mvc.service.LeaveBalanceService;

@Component
public class LeaveApplicationValidator implements Validator {

	private final LeaveApplicationService leaveApplicationSer;

	//private final LeaveBalanceService leaveBalanceSer;
	private final HolidayService holidaySer;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return LeaveApplication.class.isAssignableFrom(clazz);
	}

	@Autowired
	public LeaveApplicationValidator(LeaveApplicationService leaveApplicationSer, LeaveBalanceService leaveBalanceSer,
			HolidayService holidaySer) {
		super();
		this.leaveApplicationSer = leaveApplicationSer;
		//this.leaveBalanceSer = leaveBalanceSer;
		this.holidaySer = holidaySer;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		LeaveApplication la = (LeaveApplication) target;
		if ((la.getStartDate() != null) && (la.getEndDate() != null) && la.getStartDate().isAfter(la.getEndDate())) {
			errors.reject("startDate", "End date should be after start date.");
			errors.rejectValue("startDate", "error.dates", "End date should be after start date.");
			errors.reject("endDate", "End date should be after start date.");
			errors.rejectValue("endDate", "error.dates", "End date should be after start date.");
		}
		else if((la.getStartDate() != null) && (!la.getStartDate().isAfter(LocalDate.now()))) {
			errors.reject("startDate", "Start date should be after today.");
			errors.rejectValue("startDate", "error.dates", "Start date should be after today.");
		}
		else if ((la.getStartDate() != null) && (la.getEndDate() != null) && !holidaySer.isWorkingDay(la)) {
			errors.reject("startDate", "Please select a working day.");
			errors.rejectValue("startDate", "error.dates", "Please select a working day.");
			errors.reject("endDate", "Please select a working day.");
			errors.rejectValue("endDate", "error.dates", "Please select a working day.");
		}
		
		else if ((la.getStartDate() != null) && (la.getEndDate() != null) && leaveApplicationSer.overlapWithHistory(la)) {
			errors.reject("startDate", "Duplicate leave record!");
			errors.rejectValue("startDate", "error.dates", "Duplicate leave record!.");
			errors.reject("endDate", "Duplicate leave record!");
			errors.rejectValue("endDate", "error.dates", "Duplicate leave record!");
		}
		else if ((la.getStartDate() != null) && (la.getEndDate() != null) && !holidaySer.isBalanceEnough(la)) {
			errors.reject("startDate", "Balance Not Enough!");
			errors.rejectValue("startDate", "error.dates", "Balance Not Enough!");
			errors.reject("endDate", "Balance Not Enough!");
			errors.rejectValue("endDate", "error.dates", "Balance Not Enough!");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "error.startDate", "Start Date is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "error.endDate", "End Date is required.");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comment", "error.comment", "Comment is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reason", "error.reason", "Reason is required.");
	}

}
