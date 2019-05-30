package sg.nus.iss.mvc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailServiceImpl implements MailService {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendApplyLeaveMail(String from,String to) {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom(from);
		simpleMessage.setTo(to);
		simpleMessage.setSubject("Leave Appilication");
		simpleMessage.setText("You have received a new leave application. Please visit http://localhost:8080/login to check");

		try {
			mailSender.send(simpleMessage);
			logger.info("Your application has been sent. To"+to);
		} catch (Exception e) {
			logger.error("Error occur!", e);
		}
	}
	
	@Override
	public void sendLeaveUpdateMail(String from,String to) {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom(from);
		simpleMessage.setTo(to);
		simpleMessage.setSubject("Leave Appilication status updated");
		simpleMessage.setText("Your leave application status has been updated. Please visit http://localhost:8080/login to check");

		try {
			mailSender.send(simpleMessage);
			logger.info("Your application has been sent.");
		} catch (Exception e) {
			logger.error("Error occur!", e);
		}
	}
}
