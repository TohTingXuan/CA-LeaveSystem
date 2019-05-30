package sg.nus.iss.mvc.service;

public interface MailService {

	void sendApplyLeaveMail(String from, String to);

	void sendLeaveUpdateMail(String from, String to);

}