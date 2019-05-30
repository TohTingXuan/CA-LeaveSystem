package sg.nus.iss.mvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.nus.iss.mvc.model.LeaveApplication;
import sg.nus.iss.mvc.model.Staff;
import sg.nus.iss.mvc.model.User;
import sg.nus.iss.mvc.repo.StaffRepository;

@Controller
@SessionAttributes("User")
public class LoginController {

	private StaffRepository staffRepo;

	@Autowired
	public void setStaffRepo(StaffRepository staffRepo) {
		this.staffRepo = staffRepo;
	}

	@RequestMapping(value = {"/","/login"}, method = RequestMethod.GET)
	public String loginPage(Model model) {
		model.addAttribute("User", new User());
		return "loginForm";
	}

	@RequestMapping(path = "homepage", method = RequestMethod.POST)
	public String checkid(User User, Model model) {
		Staff staff = staffRepo.findByStaffName(User.getUsername());
		if (staff.getPassword().equalsIgnoreCase(User.getPassword())) {
			model.addAttribute("User", staff);
			return "redirect:/leave";
		} else {
			return "loginForm";
		}
	}
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";

	}
		
}
