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
//    public List<Event> findAllByEventCategoryIs(Eventcategory eventcategory);
//    public List<Event> findAllByEventCategoryIsAndEventStartTimeBetween(Eventcategory eventcategory, Instant lower, Integer upper);
    public List<Event> findAllByEventCategoryIsAndEventStartTimeBetween(Eventcategory eventcategory, Instant lower, Instant upper);

    public List<Event> findAllByEventCategoryIsAndIdIsNot (Eventcategory eventcategory, Integer id);
    public List<Event> findAllByEventCategoryIsAndIdIsNotAndEventStartTimeBetween (Eventcategory eventcategory, Integer id, Instant lower, Instant upper);
    public List<Event> findAllByEventCategoryEventCategoryName(String name);

    public List<Event> findAllByEventCategoryId(Integer eventCategoryId);
    public Page<Event> findAllByEventCategoryId(Pageable pageable, Integer eventCategoryId);
    @Query(value = "select * from events where DATE(eventStartTime) like concat(:date,'%')",nativeQuery=true)
    public Page<Event> findAllByEventStartTimeEquals(Pageable pageable, @Param("date") String date);
    @Query(value = "select * from events where DATE_ADD(eventStartTime, interval eventDuration minute) < now()",nativeQuery=true)
    public Page<Event> findAllByEventStartTimePast(Pageable pageable);
    @Query(value = "select * from events where DATE_ADD(eventStartTime, interval eventDuration minute) >= now()",nativeQuery=true)
    public Page<Event> findAllByEventStartTimeUpcoming(Pageable pageable);

}