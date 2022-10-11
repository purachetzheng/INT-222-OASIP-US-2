package sit.int221.oasipserver.entities;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EventcategoryownerId implements Serializable {
    private static final long serialVersionUID = -761880645901316397L;
    @Column(name = "userId", nullable = false)
    private Integer userId;
    @Column(name = "eventCategoryId", nullable = false)
    private Integer eventCategoryId;
    @Column(name = "categoryOwnerId", nullable = false)
    private Integer categoryOwnerId;

    public Integer getCategoryOwnerId() {
        return categoryOwnerId;
    }

    public void setCategoryOwnerId(Integer categoryOwnerId) {
        this.categoryOwnerId = categoryOwnerId;
    }

    public Integer getEventCategoryId() {
        return eventCategoryId;
    }

    public void setEventCategoryId(Integer eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryOwnerId, userId, eventCategoryId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EventcategoryownerId entity = (EventcategoryownerId) o;
        return Objects.equals(this.categoryOwnerId, entity.categoryOwnerId) &&
                Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.eventCategoryId, entity.eventCategoryId);
    }
}