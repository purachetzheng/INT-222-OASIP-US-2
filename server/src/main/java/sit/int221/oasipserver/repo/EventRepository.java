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

    @Query(value = "select * from events where DATE(eventStartTime) like concat(:date,'%')",nativeQuery=true)
    Page<Event> findAllByEventStartTimeEquals(Pageable pageable, @Param("date") String date);

    @Query(value = "select * from events where DATE_ADD(eventStartTime, interval eventDuration minute) < now()", nativeQuery=true)
    Page<Event> findAllByEventStartTimePast(Pageable pageable);

    @Query(value = "select * from events where DATE_ADD(eventStartTime, interval eventDuration minute) >= now()",nativeQuery=true)
    Page<Event> findAllByEventStartTimeUpcoming(Pageable pageable);

    @Query(value = "select * from events where DATE(eventStartTime) like concat(:date,'%') and eventCategoryId = :id",nativeQuery=true)
    Page<Event> findAllByEventCategoryIdAndEventStartTime(Pageable pageable, @Param("id") Integer eventCategoryId, @Param("date") String date);

    @Query(value = "select * from events where" +
            "(:id is null or eventCategoryId = :id)" +
            "and (:date is null or DATE(eventStartTime) like concat(:date,'%'))"
            ,nativeQuery=true)
    public Page<Event> findAll(Pageable pageable,
                                     @Param("id") Integer eventCategoryId,
                                     @Param("date") String date);

    @Query(value = "select * from events where DATE_ADD(eventStartTime, interval eventDuration minute) < now()" +
            "and (:id is null or eventCategoryId = :id)" +
            "and (:date is null or DATE(eventStartTime) like concat(:date,'%'))"
            ,nativeQuery=true)
    public Page<Event> findAllEventPast(Pageable pageable,
                                     @Param("id") Integer eventCategoryId,
                                     @Param("date") String date);

    @Query(value = "select * from events where DATE_ADD(eventStartTime, interval eventDuration minute) >= now()" +
            "and (:id is null or eventCategoryId = :id)" +
            "and (:date is null or DATE(eventStartTime) like concat(:date,'%'))"
            ,nativeQuery=true)
    public Page<Event> findAllEventUpcoming(Pageable pageable,
                                         @Param("id") Integer eventCategoryId,
                                         @Param("date") String date);
}