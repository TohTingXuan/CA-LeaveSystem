package sg.nus.iss.mvc.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sg.nus.iss.mvc.javabean.LeaveBalanceBean;
import sg.nus.iss.mvc.javabean.LeaveDetailsBean;
import sg.nus.iss.mvc.model.Designation;
import sg.nus.iss.mvc.model.LeaveBalance;
import sg.nus.iss.mvc.model.LeaveDetails;
import sg.nus.iss.mvc.model.LeaveType;
import sg.nus.iss.mvc.model.Staff;
import sg.nus.iss.mvc.repo.DesignationRepository;
import sg.nus.iss.mvc.repo.LeaveApplicationRepository;
import sg.nus.iss.mvc.repo.LeaveBalanceRepository;
import sg.nus.iss.mvc.repo.LeaveDetailsRepository;
import sg.nus.iss.mvc.repo.LeaveTypeRepository;
import sg.nus.iss.mvc.repo.StaffRepository;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {

	@Resource
	private LeaveDetailsRepository ldRepo;
	@Resource
	private LeaveTypeRepository ltRepo;
	@Resource
	private DesignationRepository dRepo;
	@Resource
	private LeaveBalanceRepository lbRepo;
	@Resource
	private StaffRepository staffRepo;
	@Resource
	private LeaveApplicationRepository laRepo;
	
	public LeaveDetailsBean createDetailsBean(Integer leavetypeid, Integer designationid) {
		LeaveDetailsBean ldbean = new LeaveDetailsBean();
		LeaveType leavetype = ltRepo.findByTypeId(leavetypeid);	
		Designation designation = dRepo.findById(designationid).orElse(null);
		LeaveDetails leavedetail = ldRepo.findByLeaveTypeAndDesignation(leavetype, designation);
		ldbean.setDesignationId(designationid);
		ldbean.setLeaveTypeId(leavetypeid);
		ldbean.setMaximumLeave(leavedetail.getMaximumLeave());
		ldbean.setDesignationName(designation.getDesignationName());
		ldbean.setLeaveTypeName(leavetype.getTypeName());
		return ldbean;
	}
	
	public LeaveBalanceBean createBalanceBean(Integer staffid, Integer leavetypeid) {
		LeaveBalanceBean lbbean = new LeaveBalanceBean();
		LeaveType leavetype = ltRepo.findByTypeId(leavetypeid);	
		Staff staff = staffRepo.findById(staffid).orElse(null);
		LeaveBalance leavebalance = lbRepo.findByStaffAndLeavetype(staff, leavetype);
		lbbean.setLeavetypeId(leavetypeid);
		lbbean.setStaffId(staffid);
		lbbean.setLeaveBalance(leavebalance.getBalance());
		lbbean.setLeavetypeName(leavetype.getTypeName());
		lbbean.setStaffName(staff.getStaffName());
		return lbbean;
	}
	
	
	public void saveAndPopulate(LeaveType leavetype) {
		ltRepo.save(leavetype);
		LeaveType newlycreatedleave = ltRepo.findTopByOrderByTypeIdDesc();
		List<Designation> alldesignations = dRepo.findAll();
		for (Iterator iterator = alldesignations.iterator(); iterator.hasNext();) {
			Designation designation = (Designation) iterator.next();
			int designationid = designation.getId();
			ldRepo.addNewLeaveType(designationid, newlycreatedleave.getTypeId());
		}
		List<Staff> allstaff = staffRepo.findAll();
		for (Iterator iterator = allstaff.iterator(); iterator.hasNext();) {
			Staff staff = (Staff) iterator.next();
			int staffid = staff.getStaffId();
			lbRepo.addStaffNewLeaveBalance(staffid, newlycreatedleave.getTypeId());
		}
		
	}
	
	public void deleteType(Integer leavetypeid) {
		laRepo.deleteApplicationForDeletedLeaveType(leavetypeid);
		
		lbRepo.deleteBalanceForDeletedLeaveType(leavetypeid);
		
		ldRepo.deleteDetailsForDeletedLeaveType(leavetypeid);
		
		ltRepo.deleteById(leavetypeid);
	}
	
	public void updateSaveDetails(LeaveDetailsBean ldbean) {
		ldRepo.updateAndSave(ldbean.getLeaveTypeId(), ldbean.getDesignationId(), ldbean.getMaximumLeave());
	}
	
	public void updateLeaveBalance(LeaveBalanceBean leavebalance) {
		lbRepo.updateAndSave(leavebalance.getLeavetypeId(), leavebalance.getStaffId(), leavebalance.getLeaveBalance());
		
	}
	
	public List<LeaveType> getLeaveTypeList() {
		return ltRepo.findAll();
	}

	
	public List<Staff> getAllStaff() {
		return staffRepo.findAll();
	}

	
	public Staff findStaff(int staffid) {
		return staffRepo.findById(staffid).orElse(null);
	}

	
	public List<LeaveBalance> getLeaveBalanceList() {
		return lbRepo.findAll();
	}

	
	public LeaveBalance getSingleLeaveBalance(Integer staffid, Integer leavetypeid) {
		Staff staff = staffRepo.findById(staffid).orElse(null);
		LeaveType lt = ltRepo.findByTypeId(leavetypeid);
		LeaveBalance lb = lbRepo.findByStaffAndLeavetype(staff, lt);
		return lb;
	}


	public void updateLeaveBalance(LeaveBalance leavebalance) {
		lbRepo.save(leavebalance);
				
	}






	
}
