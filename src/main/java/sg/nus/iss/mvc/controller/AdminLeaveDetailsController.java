package sg.nus.iss.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.nus.iss.mvc.model.LeaveDetails;
import sg.nus.iss.mvc.repo.LeaveDetailsRepository;

@Controller
public class AdminLeaveDetailsController {
	
		private LeaveDetailsRepository ldRepo;


		@Autowired
		public void setLdRepo(LeaveDetailsRepository ldRepo) {
			this.ldRepo = ldRepo;
		}


		@RequestMapping(path="/admin/leavetype-main")
		public String showLeaveTypeMainpage(Model model) {
			
			long leaveD = ldRepo.count();
			List<LeaveDetails> ldlist = ldRepo.findAll();
			
			model.addAttribute("numberofrows", leaveD);
			model.addAttribute("ldlist", ldlist);
			
			return "leavetype-main";
		}
	
}
