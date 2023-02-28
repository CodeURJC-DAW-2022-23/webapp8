package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.userManagement.User;
import com.TwitterClone.ProjectBackend.userManagement.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ResetPasswordController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;


    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String passwordToken = RandomString.make(30);

        userService.updateResetPasswordToken(passwordToken, email);
        String resetPasswordLink = "https://localhost:8443/reset-password?passwordToken=" + passwordToken;
        sendEmail(email, resetPasswordLink);

        return "forgot-password-confirmation";
    }

    @GetMapping("/forgot-password-confirmation")
    public String forgotPasswordConfirmation(){
        return "forgot-password-confirmation";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("twitterclone027@gmail.com\n", "Twitter Clone Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@Param("passwordToken")  String passwordToken, Model model, HttpServletRequest request){
        CsrfToken token_csrf = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("passwordToken", passwordToken);
        model.addAttribute("_csrf", token_csrf.getToken());
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String passwordToken,
                                       @RequestParam String password) {

        User user = userService.getByResetPasswordToken(passwordToken);

        if (user != null) {
            userService.updatePassword(user, password);
            return "redirect:/password-reset-confirmation";
        }

        return "error";
    }


    @GetMapping("/password-reset-confirmation")
    public String passwordResetConfirmation(){
        return "password-reset-confirmation";
    }

}
