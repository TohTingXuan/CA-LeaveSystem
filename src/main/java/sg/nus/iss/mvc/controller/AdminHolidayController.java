package sg.nus.iss.mvc.controller;

import java.time.LocalDate;
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

import sg.nus.iss.mvc.model.Holiday;
import sg.nus.iss.mvc.repo.HolidayRepository;

@Controller
public class AdminHolidayController {
	
	private HolidayRepository holidayRepo;

	@Autowired
	public void setHolidayRepo(HolidayRepository holidayRepo) {
		this.holidayRepo = holidayRepo;
	}
	
	
	@RequestMapping(path = "/admin/holiday-main", method = RequestMethod.GET)
	public String displayHolidayMainpage(Model model) {
		
		List<Holiday> holidayList = holidayRepo.findAll();
		Integer currentYear = LocalDate.now().getYear();
		model.addAttribute("holidayList", holidayList);
		model.addAttribute("currentYear", currentYear);
		
		return "holiday-main";
		
	}
	
	@RequestMapping(path = "/admin/holiday/edit/{id}", method = RequestMethod.GET)
	public String updateHolidayMainpage(Model model, @PathVariable("id") Integer id) {
		Holiday currentHoliday = holidayRepo.findById(id).orElse(null);
		model.addAttribute("currentHoliday", currentHoliday);
		return "holiday-edit";
	}
	 
	@RequestMapping(path = "/admin/updateholiday", method = RequestMethod.POST)
	public String updateHoliday(@ModelAttribute("currentHoliday") @Valid Holiday currentHoliday, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {

			return "holiday-edit";
		}
		currentHoliday.setYear(currentHoliday.getDate().getYear());
		holidayRepo.save(currentHoliday);
		return "redirect:/admin/holiday-main";
	}
	
	@RequestMapping(path = "/admin/holiday/add", method = RequestMethod.GET)
	public String createHolidayMainpage(Model model) {
		Holiday holiday = new Holiday();
		model.addAttribute("holiday", holiday);
		return "holiday-add";
	}
	
	@RequestMapping(path = "/admin/addHoliday", method = RequestMethod.POST)
	public String createHoliday(@Valid Holiday holiday, BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) {
	            return "holiday-add";
	        }
		holiday.setYear(holiday.getDate().getYear());
		holidayRepo.save(holiday);
		return "redirect:/admin/holiday-main";
	}
	
	@RequestMapping(path = "/admin/holiday/delete/{id}", method = RequestMethod.GET)
	public String deleteHoliday(@PathVariable("id") Integer id) {
		holidayRepo.deleteById(id);
		return "redirect:/admin/holiday-main";
	}
}