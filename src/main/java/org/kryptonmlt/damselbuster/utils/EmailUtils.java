package org.kryptonmlt.damselbuster.utils;

import java.util.List;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Class responsible for email transmission
 *
 * @author Kurt
 */
@Component
public class EmailUtils {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EmailUtils.class);

    @Autowired
    private JavaMailSender sender;

    @Value("${emailTo}")
    private String emailTo;

    /**
     * Sends email with included game titles/names to recipient defined in
     * emailTo JVM argument
     *
     * @param titles
     */
    public void sendEmail(List<String> titles) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(emailTo);
            helper.setText("Game Titles: " + titles.stream().collect(Collectors.joining(",")));
            helper.setSubject("IGT: " + titles.size() + " New Games Added");

            sender.send(message);
        } catch (MessagingException ex) {
            LOGGER.error("Unable to send email please check config: ", ex);
        }
    }
}
