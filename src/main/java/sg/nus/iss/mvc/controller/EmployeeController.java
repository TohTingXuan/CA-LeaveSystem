package sg.nus.iss.mvc.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.nus.iss.mvc.model.LeaveApplication;
import sg.nus.iss.mvc.model.LeaveBalance;
import sg.nus.iss.mvc.model.LeaveType;
import sg.nus.iss.mvc.model.Staff;
import sg.nus.iss.mvc.repo.LeaveApplicationRepository;
import sg.nus.iss.mvc.repo.LeaveTypeRepository;
import sg.nus.iss.mvc.repo.StaffRepository;
import sg.nus.iss.mvc.service.HolidayService;
import sg.nus.iss.mvc.service.LeaveApplicationService;
import sg.nus.iss.mvc.service.LeaveBalanceService;
import sg.nus.iss.mvc.service.MailService;
import sg.nus.iss.mvc.validator.LeaveApplicationValidator;

@Controller
@SessionAttributes("User")
public class EmployeeController {

	private LeaveApplicationRepository leave_applicationRepo;
	private LeaveTypeRepository leave_typeRepo;
	private LeaveBalanceService leaveBalanceSer;
	@Autowired
	private HolidayService holidaySer;
	@Autowired
	private LeaveApplicationService leaveApplicationSer;
	private StaffRepository staff_typeRepo;
	@Autowired
	private MailService mailService;

	@Autowired
	private LeaveApplicationValidator laValidator;

	@InitBinder("leave_application")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(laValidator);
	}

	@Autowired
	public void setLeaveApplicationRepo(LeaveApplicationRepository leave_applicationRepo) {
		this.leave_applicationRepo = leave_applicationRepo;
	}

	@Autowired
	public void setStaff_typeRepo(StaffRepository staff_typeRepo) {
		this.staff_typeRepo = staff_typeRepo;
	}
	@Autowired
	public void setLeaveTypeRepo(LeaveTypeRepository leave_typeRepo) {
		this.leave_typeRepo = leave_typeRepo;
	}

	@Autowired
	public void setLeaveBalanceSer(LeaveBalanceService leaveBalanceSer) {
		this.leaveBalanceSer = leaveBalanceSer;
	}

	@RequestMapping(path = "/leave/apply", method = RequestMethod.GET)
	public String createLeaveApplication(Model model, @ModelAttribute("User") Staff staff) {
		LeaveApplication la = new LeaveApplication();
		la.setStaff(staff);
		model.addAttribute("leave_application", la);
		List<LeaveType> leave_types = leave_typeRepo.findAll();
		model.addAttribute("leave_types", leave_types);
		return "leaveApplicationForm";
	}

	@RequestMapping(path = "/leaveApplication/submit", method = RequestMethod.POST)
	public String saveApplication(Model model,
			@Valid @ModelAttribute("leave_application") LeaveApplication leave_application, BindingResult bindingResult,
			@ModelAttribute("User") Staff staff) {
		leave_application.setStaff(staff);
		List<LeaveType> leave_types = leave_typeRepo.findAll();
		model.addAttribute("leave_types", leave_types);
		if (bindingResult.hasErrors()) {
			return "leaveApplicationForm";
		}

		LeaveBalance lb = leaveBalanceSer.findByStaffAndLeavetype(staff, leave_application.getLeavetype());
		double balance = lb.getBalance();
		int leavedays = holidaySer.findLeaveDaysWithoutHoliday(leave_application.getStartDate(),
				leave_application.getEndDate());
		if (leave_application.getLeavetype().getTypeId() == 3 || leave_application.getLeavetype().getTypeId() == 4) {
			if (leavedays == 1 && (leave_application.getLeavetype().getTypeId() == 3
					|| leave_application.getLeavetype().getTypeId() == 4)) {
				double bal = balance - 0.5;
				System.out.println(bal);
				leave_applicationRepo.save(leave_application);
				leaveBalanceSer.saveBalanceByStaffAndType(leave_application.getLeavetype(), bal, staff);
				// return "HomePage";
			} else
				return "cannottakemorethanoneday";
		}
		else if (leavedays <= balance && (leave_application.getLeavetype().getTypeId() != 3
				|| leave_application.getLeavetype().getTypeId() != 4)) {
			double bal = balance - leavedays;
			leave_applicationRepo.save(leave_application);
			leaveBalanceSer.saveBalanceByStaffAndType(leave_application.getLeavetype(), bal, staff);
			// return "HomePage";

		}
		//Staff from = leave_application.getStaff();
		int sid = leave_application.getStaff().getStaffId();
    	Staff from = staff_typeRepo.findByStaffId(sid);
		mailService.sendApplyLeaveMail(from.getEmail(), from.getStaff().getEmail());

		return "redirect:/leave";
	}

	@RequestMapping(path = "/leave/balance")
	public String viewLeaveBalance(Model model, @ModelAttribute("User") Staff staff) {
		List<LeaveBalance> listBalanceLeave = leaveBalanceSer.findByStaff(staff);
		model.addAttribute("listBalanceLeave", listBalanceLeave);
		return "leaveBalance";
	}

	@RequestMapping(path = "/leave")
	public String viewLeave(Model model, @ModelAttribute("User") Optional<Staff> staff) {
		List<LeaveApplication> listLeave = leave_applicationRepo.findLeaveByStaff(staff);
		model.addAttribute("listLeave", listLeave);
		return "leave";
	}

	@RequestMapping(path = "/leave/viewDetails/{id}", method = RequestMethod.GET)
	public String viewLeaveDetails(Model model, @PathVariable(value = "id") Integer id) {
		LeaveApplication leave = leave_applicationRepo.findById(id).orElse(null);
		System.out.println(leave);
		model.addAttribute("leaveDetails", leave);
		return "details";
	}

	@RequestMapping(path = "/leave/edit/{id}", method = RequestMethod.GET)
	public String editLeave(Model model, @PathVariable(value = "id") Integer id) {
		LeaveApplication leaveEdit = leave_applicationRepo.findById(id).orElse(null);
		System.out.println(leaveEdit);
		model.addAttribute("leave_application", leaveEdit);
		return "edit";
	}

	@RequestMapping(path = "/leave/save", method = RequestMethod.POST)
	public String saveLeaveDetails(@ModelAttribute("leave_application") @Valid LeaveApplication leave_application,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "edit";
		}
		int id = leave_application.getId();
		LeaveApplication leaveAp = leave_applicationRepo.findByid(id);
		Staff s = leaveAp.getStaff();
		LeaveBalance lbal = leaveBalanceSer.findByStaffAndLeavetype(s, leaveAp.getLeavetype());
		double balance1 = lbal.getBalance();
		int leavedaysNo = holidaySer.findLeaveDaysWithoutHoliday(leaveAp.getStartDate(), leaveAp.getEndDate());
		double rollbackBal = balance1 + leavedaysNo;

		int leavedays = holidaySer.findLeaveDaysWithoutHoliday(leave_application.getStartDate(),
				leave_application.getEndDate());
		if (leavedays <= rollbackBal) {
			double bal = rollbackBal - leavedays;
			leave_applicationRepo.save(leave_application);
			leaveBalanceSer.saveBalanceByStaffAndType(leave_application.getLeavetype(), bal,
					leave_application.getStaff());
			return "redirect:/leave";
		} else {
			return "insufficientLeaveBalanceErrorPage";
		}
	}

	@RequestMapping(path = "/leave/delete/{id}", method = RequestMethod.GET)
	public String deleteLeave(@PathVariable(name = "id") Integer id) {
		LeaveApplication la = leave_applicationRepo.findById(id).orElse(null);
		la.setStatus("DELETED");
		Staff s = la.getStaff();
		LeaveBalance lb = leaveBalanceSer.findByStaffAndLeavetype(s, la.getLeavetype());
		double balance = lb.getBalance();
		int leavedays = holidaySer.findLeaveDaysWithoutHoliday(la.getStartDate(), la.getEndDate());
		double bal = 0;
		if (la.getLeavetype().getTypeId() == 3 || la.getLeavetype().getTypeId() == 4) {
			bal = balance + 0.5;
		} else {
			bal = balance + leavedays;
		}
		leave_applicationRepo.save(la);
		leaveBalanceSer.saveBalanceByStaffAndType(la.getLeavetype(), bal, s);
		return "redirect:/leave";
	}

	@RequestMapping(path = "/leave/cancel/{id}", method = RequestMethod.GET)
	public String cancelApprovedLeave(@PathVariable(name = "id") Integer id) {
		LeaveApplication la = leave_applicationRepo.findById(id).orElse(null);
		la.setStatus("CANCELLED");
		Staff s = la.getStaff();
		LeaveBalance lb = leaveBalanceSer.findByStaffAndLeavetype(s, la.getLeavetype());
		double balance = lb.getBalance();
		int leavedays = holidaySer.findLeaveDaysWithoutHoliday(la.getStartDate(), la.getEndDate());
		double bal = 0;
		if (la.getLeavetype().getTypeId() == 3 || la.getLeavetype().getTypeId() == 4) {
			bal = balance + 0.5;
		} else {
			bal = balance + leavedays;
		}
		leave_applicationRepo.save(la);
		leaveBalanceSer.saveBalanceByStaffAndType(la.getLeavetype(), bal, s);
		return "redirect:/leave";
	}
	@RequestMapping(path = "/movementRegister", method = RequestMethod.GET)
	public String movementReg(Model model) {
		LocalDate today = LocalDate.now();
		int month = today.getMonthValue();
		List<LeaveApplication> listLeave = leave_applicationRepo.checkMonthLeave(month);
		model.addAttribute("listLeave", listLeave);
		return "movementReg";
	}
	@RequestMapping(path = "/movementReg/selectedMonth", method = RequestMethod.POST)
	public String movementRegByMonth(@RequestParam("monthDropDown") int month, Model model) {
		if (month != 0) {
			List<LeaveApplication> listLeave = leave_applicationRepo.checkMonthLeave(month);
			model.addAttribute("listLeave", listLeave);
		}
		return "movementReg";
		
	}
}