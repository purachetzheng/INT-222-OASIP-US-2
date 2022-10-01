package sit.int221.oasipserver.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import sit.int221.oasipserver.dtos.event.EventDto;

@Component
public class EmailServiceImpl  {

    @Autowired
    private JavaMailSender emailSender;

    public EventDto sendSimpleMessage(EventDto eventDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("chickenforregis1@gmail.com");
        message.setTo(eventDto.getBookingEmail());
        message.setSubject(eventDto.getBookingName());
        message.setText(eventDto.getEventNotes()
                + "\n"
                + eventDto.getEventCategoryName()
                + "\n"
                + eventDto.getEventStartTime());
        emailSender.send(message);
        return eventDto;
    }
}
