package sit.int221.oasipserver.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sit.int221.oasipserver.entities.Event;
import sit.int221.oasipserver.entities.Eventcategory;

import java.time.Instant;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {


    public List<Event> findAllByOrderByEventStartTimeDesc();

    public List<Event> findAllByEventCategoryIsAndEventStartTimeBetween(Eventcategory eventcategory, Instant lower, Instant upper);

    public List<Event> findAllByEventCategoryIsAndIdIsNotAndEventStartTimeBetween (Eventcategory eventcategory, Integer id, Instant lower, Instant upper);

    public boolean existsByBookingEmail(String email);

    public List<Event> findAllByBookingEmail(String email);

    @Query(value = "select * from events where DATE(eventStartTime) like concat(:date,'%')",nativeQuery=true)
    Page<Event> findAllByEventStartTimeEquals(Pageable pageable, @Param("date") String date);

    @Query(value = "select * from events where DATE_ADD(eventStartTime, interval eventDuration minute) < now()", nativeQuery=true)
    Page<Event> findAllByEventStartTimePast(Pageable pageable);

    @Query(value = "select * from events where DATE_ADD(eventStartTime, interval eventDuration minute) >= now()",nativeQuery=true)
    Page<Event> findAllByEventStartTimeUpcoming(Pageable pageable);

    @Query(value = "select * from events where DATE(eventStartTime) like concat(:date,'%') and eventCategoryId = :id",nativeQuery=true)
    Page<Event> findAllByEventCategoryIdAndEventStartTime(Pageable pageable, @Param("id") Integer eventCategoryId, @Param("date") String date);

    String FILTERALL_VALUE ="select * from events where" +
            "(:id is null or eventCategoryId = :id)" +
            "and (:date is null or DATE(eventStartTime) like concat(:date,'%'))";
    String FILTERALL_COUNT = "select count(*) from events where" +
            "(:id is null or eventCategoryId = :id)" +
            "and (:date is null or DATE(eventStartTime) like concat(:date,'%'))";
    @Query(value = FILTERALL_VALUE, countQuery = FILTERALL_COUNT, nativeQuery=true)
    Page<Event> findAllFilter(Pageable pageable,
                                     @Param("id") Integer eventCategoryId,
                                     @Param("date") String date);

    @Query(value = "select * from events where DATE_ADD(eventStartTime, interval eventDuration minute) < now()" +
            "and (:id is null or eventCategoryId = :id)" +
            "and (:date is null or DATE(eventStartTime) like concat(:date,'%'))"
            ,countQuery = "select count(*) from events where DATE_ADD(eventStartTime, interval eventDuration minute) < now()" +
            "and (:id is null or eventCategoryId = :id)" +
            "and (:date is null or DATE(eventStartTime) like concat(:date,'%'))"
            ,nativeQuery=true)
    Page<Event> findAllEventPast(Pageable pageable,
                                     @Param("id") Integer eventCategoryId,
                                     @Param("date") String date);

    Page<Event> findByBookingEmail(Pageable pageable, String email);

    Page<Event> findByEventCategoryId(Pageable pageable, Integer id);

    @Query(value = "select * from events where DATE_ADD(eventStartTime, interval eventDuration minute) >= now()" +
            "and (:id is null or eventCategoryId = :id)" +
            "and (:date is null or DATE(eventStartTime) like concat(:date,'%'))"
            ,countQuery = "select count(*) from events where DATE_ADD(eventStartTime, interval eventDuration minute) >= now()" +
            "and (:id is null or eventCategoryId = :id)" +
            "and (:date is null or DATE(eventStartTime) like concat(:date,'%'))"
            ,nativeQuery=true)
    Page<Event> findAllEventUpcoming(Pageable pageable,
                                         @Param("id") Integer eventCategoryId,
                                         @Param("date") String date);

}