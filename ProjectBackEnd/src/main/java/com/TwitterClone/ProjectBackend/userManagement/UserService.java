package com.TwitterClone.ProjectBackend.userManagement;

import com.TwitterClone.ProjectBackend.DTO.RegisteredRequest;
import com.TwitterClone.ProjectBackend.Model.Tweet;
import com.TwitterClone.ProjectBackend.Repository.UserRepository;
import com.TwitterClone.ProjectBackend.Security.EmailValidator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * This class is on charge of implementing all the model part of the signUp process and contains all its logic.
 */
@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;


    /**
     * It takes the parameters of the request, test if they are valid and them registered a new user
     * @param request
     * @return
     */
    @Transactional
    public String signup(RegisteredRequest request) throws MessagingException, IOException {
        User user = new User(request.getUsername(), request.getPassword(), request.getEmail(), "USER");
        if (!emailValidator.test(request.getEmail())) {
            throw new IllegalStateException("Invalid Mail Format");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already taken");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        user.setImages(new String[]{"example_data/Default_profilepic.jpg","example_data/Default_profilebanner.jpg"});
        userRepository.save(user);

        sendVerificationEmail(user, user.getEmail());

        return "It works";
    }

    private void sendVerificationEmail(User user, String userMail)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = userMail;
        String fromAddress = "twitterclone027@gmail.com";
        String senderName = "Twitter clone";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Twitter Clone.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = "https://localhost:8443/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isLoggedIn()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }

    }

    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token).orElseThrow();
    }
    public void updatePassword(User user, String newPassword) {

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public List<User> getRecommendedUsers(Long userId) {
        return userRepository.findRecommendedUsers(userId);
    }

    public List<User> findByUsernameContainingIgnoreCase(String keyword) {
        return this.userRepository.findByUsernameContainingIgnoreCase(keyword);
    }
}