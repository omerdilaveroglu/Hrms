package kodlamaio.hrms.emailService;

import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.constants.Messages;

@Service
public class EmailSendManager {
	public void emailSending(String email) {
		System.out.println("'"+email+ Messages.confirmationEmailSent);
	}
}