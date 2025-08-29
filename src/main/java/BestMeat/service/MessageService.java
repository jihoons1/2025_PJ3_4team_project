package BestMeat.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// 메일 전송용
@Service
public class MessageService {
    @Autowired
    private JavaMailSender mailSender; /* build grald 'implementation 'org.springframework.boot:spring-boot-starter-mail' 라이브러리 적용시에만 사용 가능 Spring 용 */

    public void mailMessage(String a , String b, String c){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(a); // 받는사람 메일 지정
        mailMessage.setSubject(b); // 메일 제목 내용 지정
        mailMessage.setText(c); // 메일 내용

        mailSender.send(mailMessage); // 위 내용 지정 후 메일 전송
        System.out.println("[성공] 이메일 발송");
    }
} // class end