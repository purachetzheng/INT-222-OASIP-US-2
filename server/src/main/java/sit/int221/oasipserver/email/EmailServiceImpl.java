package sit.int221.oasipserver.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import sit.int221.oasipserver.dtos.event.EventDto;
import sit.int221.oasipserver.dtos.event.PostEventDto;
import sit.int221.oasipserver.repo.EventcategoryRepository;
import sit.int221.oasipserver.services.EventcategoryService;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Component
public class EmailServiceImpl  {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    EventcategoryRepository eventcategoryRepository;

    @Autowired
    EventcategoryService eventcategoryService;

    public PostEventDto sendSimpleMessage(PostEventDto eventDto, TimeZone timeZone) {
        ChronoUnit minutes = ChronoUnit.MINUTES;
        ChronoUnit hours = ChronoUnit.HOURS;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd, yyyy HH:mm", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Bangkok")));
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        timeFormatter.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Bangkok")));
        Date eventDateFromDto = Date.from(eventDto.getEventStartTime().plus(0, hours));
        Date eventDateEndFromDto = Date.from(eventDto.getEventStartTime().plus(eventDto.getEventDuration(), minutes)
                .plus(0, hours));

        String testDatez = formatter.format(eventDateFromDto);
        String startTime = timeFormatter.format(eventDateFromDto);
        String endTime = timeFormatter.format(eventDateEndFromDto);


        String eventCategoryName = eventcategoryService.getById(eventDto.getEventCategoryId()).getEventCategoryName();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("oasip.us2@gmail.com");
        message.setTo(eventDto.getBookingEmail());
        message.setSubject("[OASIP]" + " " + eventCategoryName
        + " " + "@ " + " " + testDatez + " - " + endTime);
        message.setText("Booking Name: " + eventDto.getBookingName()
                + "\n"
                + "Event Category: " + eventCategoryName
                + "\n"
                + "When: " + " " + testDatez + " - " + endTime
                + "\n"
                + "Event Notes: " + eventDto.getEventNotes());
        message.setReplyTo("noreply@intproj21.sit.kmutt.ac.th");
        emailSender.send(message);
        return eventDto;
    }
}
