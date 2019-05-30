package sg.nus.iss.mvc.javabean;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ReportBean {
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate StartDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate EndDate;
	
	public ReportBean(LocalDate startDate, LocalDate endDate) {
		super();
		StartDate = startDate;
		EndDate = endDate;
	}
	public ReportBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocalDate getStartDate() {
		return StartDate;
	}
	public void setStartDate(LocalDate startDate) {
		StartDate = startDate;
	}
	public LocalDate getEndDate() {
		return EndDate;
	}
	public void setEndDate(LocalDate endDate) {
		EndDate = endDate;
	}

}