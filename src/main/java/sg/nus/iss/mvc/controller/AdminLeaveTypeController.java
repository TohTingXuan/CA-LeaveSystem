package sg.nus.iss.mvc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.nus.iss.mvc.javabean.LeaveBalanceBean;
import sg.nus.iss.mvc.javabean.LeaveDetailsBean;
import sg.nus.iss.mvc.model.LeaveBalance;
import sg.nus.iss.mvc.model.LeaveType;
import sg.nus.iss.mvc.service.LeaveTypeService;

@Controller
public class AdminLeaveTypeController {

	@Autowired
	private LeaveTypeService ltService;
	
	//Update Leave max value
	@RequestMapping(path = "/admin/leavetype/edit/{ltid}/{deid}", method = RequestMethod.GET)
	public String displayUpdateLeaveMainpage(Model model, @PathVariable("ltid") Integer leavetypeid, @PathVariable("deid") Integer designationid) {
		LeaveDetailsBean ldbean = ltService.createDetailsBean(leavetypeid, designationid);
		model.addAttribute("leavedetail", ldbean);
		return "leavetype-edit";
	}
	

	@RequestMapping(path = "/admin/updateleavetype", method = RequestMethod.POST)
	public String updateLeaveDetails(LeaveDetailsBean ldbean) {
		ltService.updateSaveDetails(ldbean);
		return "redirect:/admin/leavetype-main";
	}

	
	//Delete Leave Types
	@RequestMapping(path="/admin/leavetype/delete", method = RequestMethod.GET)
	public ModelAndView displayDeleteLeaveMainpage() {
		ModelAndView mav = new ModelAndView("leavetype-delete");
		List<LeaveType> ltlist = ltService.getLeaveTypeList();
		mav.addObject("ltlist", ltlist);
		return mav;
	}

	
	@RequestMapping(path="/admin/leavetype/delete/{id}", method = RequestMethod.GET)
	public String deleteLeaveType(@PathVariable("id") Integer leavetypeid) {
		///delete from LeaveApplication, LeaveBalance, from LeaveDetails, and finally from LeaveType 
		ltService.deleteType(leavetypeid);
		return "redirect:/admin/leavetype-main";
	}

	
	//Add Leave Type code
	@RequestMapping(path="/admin/leavetype/add", method = RequestMethod.GET)
	public String displayAddLeaveMainpage(Model model) {
		LeaveType leavetype = new LeaveType();
		model.addAttribute("leavetype", leavetype);
		return "leavetype-add";
	}
	
	
	@RequestMapping(path="/admin/addLeaveType", method = RequestMethod.POST)
	public String AddLeaveType(@ModelAttribute("leavetype") @Valid LeaveType leavetype, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "leavetype-add";
		}
		ltService.saveAndPopulate(leavetype);
		return "redirect:/admin/leavetype-main";
	}
	
	//Show update Leave balance mainpage
	@RequestMapping(path="/admin/leavebalance-main", method = RequestMethod.GET)
	public String showUpdateLeaveBalanceMainPage(Model model) {
		List<LeaveBalance> allbalance = ltService.getLeaveBalanceList();
		model.addAttribute("allbalance", allbalance);
		return "leaveentitlement-main";
	}
	
	//show update leavebalance page for one staff and one leavetype
	@RequestMapping(path="/admin/leavebalance/edit/{staffid}/{leavetypeid}", method = RequestMethod.GET)
	public String displayUpdateLeaveMainPage(Model model, @PathVariable("staffid") Integer staffid, 
			@PathVariable("leavetypeid") Integer leavetypeid) {
		//LeaveBalance leavebalance = ltService.getSingleLeaveBalance(staffid, leavetypeid);
		LeaveBalanceBean lbb = ltService.createBalanceBean(staffid, leavetypeid);
		model.addAttribute("leavebalance", lbb);
		return "leaveentitlement-update";
	}
	
	//show update leavebalance page for one staff and one leavetype
		@RequestMapping(path="/admin/updateleavebalance", method = RequestMethod.POST)
		public String updateBalance(LeaveBalanceBean leavebalance) {
			ltService.updateLeaveBalance(leavebalance);
			return "redirect:/admin/leavebalance-main";
		}
}
