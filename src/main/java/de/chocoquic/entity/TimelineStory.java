package de.chocoquic.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the timeline_story database table.
 *
 */
@Entity
@Data
@EqualsAndHashCode(of = "ownerId")
@Table(name = "timeline_story")
@NamedQueries({
    @NamedQuery(name = "TimelineStory.findAll", query = "SELECT t FROM TimelineStory t")    ,
    @NamedQuery(name = "TimelineStory.findAllByTimelineData", query = "SELECT t FROM TimelineStory t where t.timelineData = :timelineData")
})
public class TimelineStory extends DBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "date_format", length = 65)
    private String dateFormat;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Lob
    @Column(name = "full_text")
    private String fullText;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Lob
    private String text;

    @Column(length = 255)
    private String title;

    //bi-directional many-to-one association to TimelineCategory
    @ManyToOne
    @JoinColumn(name = "timeline_category_id", nullable = false)
    private TimelineCategory timelineCategory;

    //bi-directional many-to-one association to TimelineData
    @ManyToOne
    @JoinColumn(name = "timeline_data_id", nullable = false)
    private TimelineData timelineData;

    //bi-directional many-to-one association to TimelineStoryMedia
    @OneToMany(mappedBy = "timelineStory")
    private Set<TimelineStoryMedia> timelineStoryMedias;

    public TimelineStory() {
    }

    public TimelineStoryMedia addTimelineStoryMedia(TimelineStoryMedia timelineStoryMedia) {
        getTimelineStoryMedias().add(timelineStoryMedia);
        timelineStoryMedia.setTimelineStory(this);

        return timelineStoryMedia;
    }

    public TimelineStoryMedia removeTimelineStoryMedia(TimelineStoryMedia timelineStoryMedia) {
        getTimelineStoryMedias().remove(timelineStoryMedia);
        timelineStoryMedia.setTimelineStory(null);

        return timelineStoryMedia;
    }

}
