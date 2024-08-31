package com.example.hotelreservation.service;

import com.example.hotelreservation.domains.vo.UserVO;
import com.example.hotelreservation.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
@PropertySource("classpath:mail/mailMessages.properties")
public class UserMailService {
    private static final String FIND_ID_TEMPLATE_PATH = "template/find-id";

    @Autowired
    private TemplateEngine emailTemplateEngine;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserMapper userMapper;

    // mailMessages.properties에서 값 가져오기 (@PropertySource("classpath:..."))
    @Value("${fromEmailAddr}")
    private String fromEmail;

    public void find_id(String userEmail) throws MessagingException {
        UserVO user = userMapper.get_user_by_email(userEmail);
        if(user == null) {
            final Context ctx = new Context(Locale.KOREA);
            ctx.setVariable("userId", "not found"); // find-id.html에 보낼 값 userId
            send_mail(userEmail, fromEmail, FIND_ID_TEMPLATE_PATH, ctx);
        } else {
            // thymeleaf context
            final Context ctx = new Context(Locale.KOREA);
            ctx.setVariable("userId", user.getEmail()); // find-id.html에 보낼 값 userId
            send_mail(userEmail, fromEmail, FIND_ID_TEMPLATE_PATH, ctx);
        }
    }

    private void send_mail(String to, String from, String templatePath, Context ctx) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        final String htmlContent = emailTemplateEngine.process(templatePath, ctx);

        helper.setFrom(from); // 누가 보내는가?
        helper.setTo(to); // 누구에게 보내는가?
        helper.setText(htmlContent, true); // 내용을 html로 전송
        // 메세지 발송
        mailSender.send(mimeMessage);
    }

    public void send_mail(String email) {
        // Velocity, Freemarker, Thymeleaf

    }
}