package com.basic.GADI.service;

import com.basic.GADI.dto.request.RegisterRequestDto;
import com.basic.GADI.dto.response.RegisterResponseDto;
import com.basic.GADI.entity.User;
import com.basic.GADI.exception.BusinessException;
import com.basic.GADI.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Value("{spring.mail.username}")
    private static String senderEmail;


    @Transactional
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto)  {
        if (userRepository.existsByUserEmail(registerRequestDto.getUserEmail())) {
            throw new BusinessException("이미 존재하는 아이디입니다.", HttpStatus.NOT_FOUND);
        }

        User registerUser = User.builder()
                .userEmail(registerRequestDto.getUserEmail())
                .userName(registerRequestDto.getUserName())
                .userPw(passwordEncoder.encode(registerRequestDto.getUserPw()))
                .build();

        userRepository.save(registerUser);

        return new RegisterResponseDto(registerRequestDto.getUserEmail(), registerRequestDto.getUserName());
    }

    public String createAuthCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(2);

            switch (number) {
                case 0: key.append((char) (random.nextInt(26) + 65));
                case 1: key.append(random.nextInt(10));
            }
        }
        return key.toString();
    }

    public MimeMessage createAuthMail(String mail, String authCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        mimeMessage.setFrom(senderEmail);
        mimeMessage.setRecipients(MimeMessage.RecipientType.TO, mail);
        mimeMessage.setSubject("[Gadi-Eat-It] 이메일 인증");
        String body = "";
        body += "<h3>안녕하세요. Gadi Eat IT! 입니다.</h3> ";
        body += "<h3><span>요청하신 인증번호는 </span><span>" + authCode + "</span><span>입니다.</span></h3>";
        body += "<h3>감사합니다.</h3>";
        mimeMessage.setText(body, "UTF-8", "html");

        return mimeMessage;
    }


    public boolean sendAuthMail(String sendEmail) throws MessagingException {
        String authCode = createAuthCode();

        MimeMessage createMail = createAuthMail(sendEmail, authCode);
        try {
            javaMailSender.send(createMail);
            return true;
        } catch (MailException e) {
            return false;
        }
    }





}
