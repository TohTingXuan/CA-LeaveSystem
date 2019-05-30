package sg.nus.iss.mvc.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import sg.nus.iss.mvc.javabean.ReportBean;
import sg.nus.iss.mvc.model.LeaveApplication;
import sg.nus.iss.mvc.model.LeaveBalance;
import sg.nus.iss.mvc.model.Staff;
import sg.nus.iss.mvc.repo.LeaveApplicationRepository;
import sg.nus.iss.mvc.repo.StaffRepository;
import sg.nus.iss.mvc.service.HolidayService;
import sg.nus.iss.mvc.service.LeaveApplicationService;
import sg.nus.iss.mvc.service.LeaveBalanceService;
import sg.nus.iss.mvc.service.MailService;

@Controller
@SessionAttributes("User")
public class ManagerController {
    
	@Autowired
	private LeaveApplicationService leaveApplicationService;
	@Autowired
	private HolidayService holidaySer;
	private LeaveApplicationRepository leave_applicationRepo;
	private StaffRepository staff_typeRepo;
	private LeaveBalanceService leaveBalanceSer;
	
	@Autowired
	public void setLeaveApplicationRepo(LeaveApplicationRepository leave_applicationRepo) {
		this.leave_applicationRepo = leave_applicationRepo;
	}
	@Autowired
	public void setLeaveBalanceSer(LeaveBalanceService leaveBalanceSer) {
		this.leaveBalanceSer = leaveBalanceSer;
	}
	@Autowired
	public void setStaff_typeRepo(StaffRepository staff_typeRepo) {
		this.staff_typeRepo = staff_typeRepo;
	}
	@Autowired
	private MailService mailService;
	 @RequestMapping(path = "manager/leaverequest/submit", method = RequestMethod.POST)
	    public String save(Model model, @ModelAttribute("leave_application") @Valid LeaveApplication leave_application, BindingResult bindingResult,@ModelAttribute("User") Staff staff) {
	    	if (bindingResult.hasErrors()) {
	    		LocalDate startdate = leave_application.getStartDate(); 
				LocalDate enddate =   leave_application.getEndDate();
				List<LeaveApplication> leavelist = leave_applicationRepo.checkOverlapLeave(startdate, enddate,staff.getStaffId());
				model.addAttribute("leavefor5", leavelist);
	    		//System.out.println(bindingResult.getFieldError("comment"));
	            return "leaveRequest-edit";
	        }
	    	if (leave_application.getStatus().equals("REJECTED")) {
	    		Staff s = leave_application.getStaff();
	    		LeaveBalance lb = leaveBalanceSer.findByStaffAndLeavetype(s, leave_application.getLeavetype());
	    		double balance = lb.getBalance();
	    		int leavedays = holidaySer.findLeaveDaysWithoutHoliday(leave_application.getStartDate(), leave_application.getEndDate());
	    		double bal = 0;
	    		if (leave_application.getLeavetype().getTypeId() == 3 || leave_application.getLeavetype().getTypeId() == 4) {
	    			bal = balance + 0.5;
	    		} else {
	    			bal = balance + leavedays;
	    		}
	    		leaveBalanceSer.saveBalanceByStaffAndType(leave_application.getLeavetype(), bal, s);
	    	}
	    	leave_applicationRepo.save(leave_application);
	    	int sid = leave_application.getStaff().getStaffId();
	    	Staff from = staff_typeRepo.findByStaffId(sid);
	    	mailService.sendLeaveUpdateMail(from.getStaff().getEmail(), from.getEmail());
	    	System.out.println(from.getStaff().getEmail());
	    	System.out.println(from.getEmail());
	        return "redirect:/manager/leaverequest";
	    }
	    @RequestMapping(path = "manager/leaverequest", method = RequestMethod.GET)
	    public String getAll(Model model,@ModelAttribute("User") Staff staff) {
	    	List<LeaveApplication> listb = leave_applicationRepo.findSubordinateByBossId(staff.getStaffId());
	        model.addAttribute("leave_applications", listb);
	        return "leaveRequest-list";
	    } 
	    @RequestMapping(path = "manager/leaverequest/edit/{id}", method = RequestMethod.GET)
	    public String edit(Model model, @PathVariable(value = "id") Integer id,@ModelAttribute("User") Staff staff) {   	
	    	LeaveApplication leave_application= leave_applicationRepo.findByid(id);
	        model.addAttribute("leave_application", leave_application);
			LocalDate startdate = leave_application.getStartDate(); 
			LocalDate enddate =   leave_application.getEndDate();
			List<LeaveApplication> leavelist = leave_applicationRepo.checkOverlapLeave(startdate, enddate,staff.getStaffId());
			model.addAttribute("leavefor5", leavelist);
	
	        return "leaveRequest-edit";
	    }
	    @RequestMapping(path = "/manager/leavehistory", method = RequestMethod.GET)
	    public String getHistoryOfEmployee(Model model,@ModelAttribute("User") Staff staff) {
	    	List<Staff> s =staff_typeRepo.findHistoryByid(staff.getStaffId());
	    	model.addAttribute("listOfSubordinates", s);   //rename
	        return "subordinatesLeaveHistory";
	    } 
	    @RequestMapping(path = "/manager/leavehistory/view/{id}", method = RequestMethod.GET)
	    public String viewleavehistory(Model model, @PathVariable(value = "id") Integer id) {
	    	Optional<Staff> s = staff_typeRepo.findById(id); 
	        List<LeaveApplication> leaveHistory = leave_applicationRepo.findLeaveByStaff(s);
	        model.addAttribute("leaveHistory", leaveHistory);

	        return "subordinateLeave-list";
	    }
	    @RequestMapping(path = "/manager/subordinatesLeave/viewDetails/{id}", method = RequestMethod.GET)
	    public String viewLeaveDetails(Model model, @PathVariable(value = "id") Integer id) {   	
	    	LeaveApplication leaveDetails = leave_applicationRepo.findById(id).orElse(null);
	    	System.out.println(leaveDetails);
	        model.addAttribute("leaveDetails_subordinate", leaveDetails);
	        return "subordinateLeaveDetails";
	    }
	    @GetMapping(value="/manager/dateselect")
	    public ModelAndView GetDatesView() {
	 	  ModelAndView mav = new ModelAndView();
	 	  mav.addObject("ReportBeans", new ReportBean()); 
	 	  mav.setViewName("dateselectView");
	 	  return mav;
	    }
	    @RequestMapping(path = "/manager/getleave", method = RequestMethod.POST)
		public String getLeaves(ReportBean reportBean
				, Model model) {
		    System.out.println(reportBean.getStartDate());
		    System.out.println(reportBean.getEndDate());
		    LocalDate startDate = reportBean.getStartDate();
		    LocalDate endDate = reportBean.getEndDate();
		    
		    List<LeaveApplication> leaveApplicationList = leaveApplicationService.getleavelist(startDate,endDate);
		    
			//List<LeaveApplication> leaveApplicationList = leaveApplicationService.findAll();
			model.addAttribute("leaveApplicationList", leaveApplicationList);
			return "report";
		}
}
