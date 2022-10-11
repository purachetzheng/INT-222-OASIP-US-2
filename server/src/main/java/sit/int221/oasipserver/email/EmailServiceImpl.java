package sit.int221.oasipserver.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import sit.int221.oasipserver.dtos.event.EventDto;
import sit.int221.oasipserver.dtos.event.PostEventDto;
import sit.int221.oasipserver.repo.EventcategoryRepository;
import sit.int221.oasipserver.services.EventcategoryService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Component
public class EmailServiceImpl  {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    EventcategoryRepository eventcategoryRepository;

    @Autowired
    EventcategoryService eventcategoryService;

    public PostEventDto sendSimpleMessage(PostEventDto eventDto) {
        Date testDate = Date.from(eventDto.getEventStartTime());
        LocalDate date = LocalDate.ofInstant(eventDto.getEventStartTime(), ZoneId.of("UTC"));
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Calendar cal = Calendar.getInstance();
        cal.setTime(testDate);
        cal.get(Calendar.MONTH);
        String eventCategoryName = eventcategoryService.getById(eventDto.getEventCategoryId()).getEventCategoryName();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@intproj21.sit.kmutt.ac.th");
        message.setTo(eventDto.getBookingEmail());
        message.setSubject("[OASIP]" + " " + eventCategoryName
        + " " + "@ " + dayOfWeek + " " + date);
        message.setText("Booking Name: " + eventDto.getBookingName()
                + "\n"
                + "Event Category: " + eventCategoryName
                + "\n"
                + "When: " + date
                + "\n"
                + "Event Notes: " + eventDto.getEventNotes());
        emailSender.send(message);
        return eventDto;
    }
}
