package com.care.db.basic.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired	private JavaMailSender mailSender;

	public void sendMail(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();

		try {
			// MimeMessageHelper 객체는 MimeMessage 객체를 UTF-8로 인코딩하기위한 객체이며 
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			// 이메일의 제목, 본문, 수신자를 set해줄 수 있다.
			messageHelper.setSubject(subject); // 이메일의 제목
			messageHelper.setText(content);// 이메일의 본문
			messageHelper.setTo(to); // 수신자

			mailSender.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
