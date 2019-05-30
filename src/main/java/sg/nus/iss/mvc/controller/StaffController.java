package sg.nus.iss.mvc.controller;

import java.util.ArrayList;
import java.util.Iterator;
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

import sg.nus.iss.mvc.model.Designation;
import sg.nus.iss.mvc.model.LeaveType;
import sg.nus.iss.mvc.model.Staff;
import sg.nus.iss.mvc.repo.DesignationRepository;
import sg.nus.iss.mvc.repo.LeaveApplicationRepository;
import sg.nus.iss.mvc.repo.LeaveBalanceRepository;
import sg.nus.iss.mvc.repo.LeaveTypeRepository;
import sg.nus.iss.mvc.repo.StaffRepository;

@Controller
public class StaffController {

	private StaffRepository staffRepo;
	@Autowired
	private LeaveApplicationRepository LARepo;
	private LeaveBalanceRepository lbRepo;
	private DesignationRepository dRepo;
	@Autowired
	private LeaveTypeRepository ltRepo;
	
	@Autowired
	public void setLbRepo(LeaveBalanceRepository lbRepo) {
		this.lbRepo = lbRepo;
	}
	
	@Autowired
	public void setdRepo(DesignationRepository dRepo) {
		this.dRepo = dRepo;
	}

	@Autowired
	public void setStaffRepo(StaffRepository staffRepo) {
		this.staffRepo = staffRepo;
	}
	
	//staff management homepage
	@RequestMapping(path="/admin/staff",method=RequestMethod.GET)
	public String HOME() {
		return "managestaff-main";
	}
	
	//create staff page
	@RequestMapping(path="/admin/staff/create",method=RequestMethod.GET)
	public String createStaff(Model model) {
		model.addAttribute("staff", new Staff());
		List<Designation> dlist = dRepo.findAll();
		model.addAttribute("dlist", dlist);
		List<Staff> bosslist = staffRepo.getBossList();
		model.addAttribute("bosslist", bosslist);
		return "managestaff-add";
	}
	
	//save created staff
	@RequestMapping(path="/admin/staffs",method=RequestMethod.POST)
	public String saveStaff(@ModelAttribute @Valid Staff staff, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			List<Designation> dlist = dRepo.findAll();
			model.addAttribute("dlist", dlist);
			List<Staff> bosslist = staffRepo.getBossList();
			model.addAttribute("bosslist", bosslist);
			return "managestaff-add";
		}
		staffRepo.save(staff);
		Staff newlycreatedstaff = staffRepo.findTopByOrderByStaffIdDesc();
		//add staffid to leavebalance table.
		List<LeaveType> ltlistall = ltRepo.findAll();
		for (Iterator iterator = ltlistall.iterator(); iterator.hasNext();) {
			LeaveType leaveType = (LeaveType) iterator.next();
			lbRepo.addStaffNewLeaveBalance(newlycreatedstaff.getStaffId(), leaveType.getTypeId());
		}
	
		return "redirect:/admin/staffs1";
	}
	

	
	//delete staff
	@RequestMapping(path="/admin/staff/delete/{staffId}",method=RequestMethod.GET)
	public String deleteStaff(Model model,@PathVariable(value="staffId") int id) {
		
		//if Staff is a Manager, set boss id of his subordinates to 1 (bigboss) first
		
		//check if Staff is a boss
		List<Staff> bosslist = staffRepo.getBossList();
		for (Iterator iterator = bosslist.iterator(); iterator.hasNext();) {
			Staff staff = (Staff) iterator.next();
			if (staff.getStaffId() == id) {
				// set boss id of subordinates to 1
				staffRepo.removeBossFromSubordinates(id);
			}
		}

//		//delete from leaveApplications, leaveBalance and then from Staff Table
		LARepo.deleteLeaveForDeletedStaff(id);
		lbRepo.deleteBalanceForDeletedStaff(id);
		staffRepo.delete(staffRepo.findById(id).orElse(null));
		
		return "redirect:/admin/staffs1";
		
	}
	
	//modify page for one staff
	@RequestMapping(path="/admin/staff/modify/{staffId}",method=RequestMethod.GET)
	public String modifyStaffById(Model model,@PathVariable(value="staffId") int id) {

		Staff S=staffRepo.findById(id).orElse(null);
		model.addAttribute("staff",S);
		List<Designation> dlist = dRepo.findAll();
		model.addAttribute("dlist", dlist);
		List<Staff> bosslist = staffRepo.getBossList();
		model.addAttribute("bosslist", bosslist);
		return "managestaff-modify";
	
	}
	
	//save modified staff
	@RequestMapping(path="/admin/saveModified",method=RequestMethod.POST)
	public String saveModifiedStaff(@ModelAttribute @Valid Staff staff, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			List<Designation> dlist = dRepo.findAll();
			model.addAttribute("dlist", dlist);
			List<Staff> bosslist = staffRepo.getBossList();
			model.addAttribute("bosslist", bosslist);
			return "managestaff-modify";
		}
		staffRepo.save(staff);
		return "redirect:/admin/staffs1";
	}
	
	//view list of staff to modify or delete
	@RequestMapping(path="/admin/stafflist")
	public String ListMethod(Model model) {
		
		ArrayList<Staff>slist=(ArrayList<Staff>)staffRepo.findAll();
		model.addAttribute("slist", slist);
		return "managestaff-modifydelete";
	}
	
	//view list of staff
	@RequestMapping(path="/admin/staffs1")
	public String ListMethod1(Model model) {
		
		ArrayList<Staff>Slist=(ArrayList<Staff>)staffRepo.findAll();
		model.addAttribute("Slist", Slist);
		return "managestaff-view";
	}
}
