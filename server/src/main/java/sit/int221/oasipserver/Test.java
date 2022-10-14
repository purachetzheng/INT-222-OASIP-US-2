package sit.int221.oasipserver;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int221.oasipserver.dtos.user.MatchUserDto;
import sit.int221.oasipserver.entities.Event;
import sit.int221.oasipserver.enums.UserRole;
import sit.int221.oasipserver.repo.EventRepository;
import sit.int221.oasipserver.repo.EventcategoryRepository;
import sit.int221.oasipserver.services.EventcategoryService;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Test {
    public static boolean findByName(String name) {
        for (UserRole role : UserRole.values()) {
            if(role.name().equals(name))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ChronoUnit minutes = ChronoUnit.MINUTES;
        LocalDate localDate = LocalDate.parse("2022-01-01");
        LocalDateTime localDateTime = localDate.atStartOfDay();
//        Instant instant = localDateTime.toInstant(ZoneOffset.of("Asia/Bangkok"));
        Instant instant = Instant.parse("2022-12-30T12:00:00.000Z");
        System.out.println(instant);
        Instant testz = instant.plus(40, minutes);
        System.out.println(testz);
        ZoneId systemZone = ZoneId.of("Asia/Bangkok"); // my timezone
        ZoneOffset currentOffsetForMyZone = systemZone.getRules().getOffset(instant);
        instant.atOffset(currentOffsetForMyZone);
        Date myDate = Date.from(instant);
        Date test = Date.from(testz);
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd, yyyy hh:mm");
        System.out.println(formatter.format(myDate));
        System.out.println(formatter.format(test));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.get(Calendar.MONTH);




//        Argon2 argon2 = Argon2Factory.create();
//        String password = "admin";
//        String hash = argon2.hash(22, 65536, 1, password);
//        System.out.println(hash);
//
//        if(argon2.verify(hash, password)) {
//            System.out.println("Matched");
//        } else {
//            System.out.println("Not Matched");
//        }

    }
}
