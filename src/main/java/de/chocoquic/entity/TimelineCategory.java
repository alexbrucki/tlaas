package de.chocoquic.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the timeline_category database table.
 *
 */
@Entity
@EqualsAndHashCode(callSuper=true, of = "color")
@Data
@Table(name = "timeline_category")
@NamedQueries({    
    @NamedQuery(name = "TimelineCategory.findAll", query = "SELECT t FROM TimelineCategory t"),
    @NamedQuery(name = "TimelineCategory.findByTimelineData", query = "SELECT t FROM TimelineCategory t where t.timelineData = :timelineData")
})
public class TimelineCategory extends DBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(length = 45)
    private String color;

    @Column(length = 65)
    private String layout;

    @Column(name = "category_order")
    private Long order;

    @Column(name = "category_rows")
    private Long rows;

    private Long size;

    @Column(length = 65)
    private String title;

    //bi-directional many-to-one association to TimelineData
    @ManyToOne
    @JoinColumn(name = "timeline_data_id", nullable = false)
    private TimelineData timelineData;

    //bi-directional many-to-one association to TimelineStory
    @OneToMany(mappedBy = "timelineCategory")
    private Set<TimelineStory> timelineStories;

    public TimelineCategory() {
    }

    public TimelineStory addTimelineStory(TimelineStory timelineStory) {
        getTimelineStories().add(timelineStory);
        timelineStory.setTimelineCategory(this);

        return timelineStory;
    }

    public TimelineStory removeTimelineStory(TimelineStory timelineStory) {
        getTimelineStories().remove(timelineStory);
        timelineStory.setTimelineCategory(null);

        return timelineStory;
    }

}
