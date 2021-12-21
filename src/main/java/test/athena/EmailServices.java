package test.athena;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServices {

	@Autowired
	private EmailRepository repos;
	
	
	@Autowired
	private JavaMailSender mailSender;
	
	public List<Email> listAll() {
		return repos.findAll();
	}
	
	public void sending(Email email) 
			throws UnsupportedEncodingException, MessagingException {
		repos.save(email);
		
		sendEmail(email);
	}
	
	
	private void sendEmail(Email email) 
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = email.getSend_to();
		String fromAddress = "manoharravi999@gmail.com";
		String senderName = "BSA Troop 72";
		String subject = email.getSubject();
		String content = email.getMessage();
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(fromAddress, senderName);
		//helper.setTo(toAddress);
		message.addRecipients(RecipientType.TO, toAddress);
		helper.setSubject(subject);
		
//		content = content.replace("[[name]]", user.getFullName());
//		String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
//		
//		content = content.replace("[[URL]]", verifyURL);
//		
		helper.setText(content, true);
		
		mailSender.send(message);
		
		System.out.println("Email has been sent");
		repos.save(email);
	}
	
	
}
