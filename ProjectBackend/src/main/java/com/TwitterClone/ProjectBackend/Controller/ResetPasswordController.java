package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.userManagement.User;
import com.TwitterClone.ProjectBackend.userManagement.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.UnsupportedEncodingException;

@Controller
public class ResetPasswordController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @PostMapping("/forgot-password")
    public ModelAndView processForgotPassword(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);


            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink =  "https://localhost:8443/reset-password?token=" + token;
            sendEmail(email, resetPasswordLink);
            ModelAndView modelAndView = new ModelAndView("redirect:/forgot-password-confirmation.html");
            return modelAndView;


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
    public String showResetPasswordForm(Model model, @RequestParam String token) {
        model.addAttribute("token", token);
        return "reset-password-page";
    }



    @PostMapping("/reset-password/{token}")
    public ModelAndView processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.getByResetPasswordToken(token);

        if (user == null) {
            ModelAndView modelAndView = new ModelAndView("redirect:/error");
            return modelAndView;
        } else {
            userService.updatePassword(user, password);
            ModelAndView modelAndView = new ModelAndView("redirect:/password-reset-confirmation.html");
            return modelAndView;
        }

    }
}
