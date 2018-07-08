package de.chocoquic.ltass.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the timeline_story_media database table.
 *
 */
@Entity
@Data
@EqualsAndHashCode(of = "link")
@Table(name = "timeline_story_media")
@NamedQuery(name = "TimelineStoryMedia.findAll", query = "SELECT t FROM TimelineStoryMedia t")
public class TimelineStoryMedia extends DBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Lob
    private byte[] data;

    @Column(nullable = false)
    private Boolean external;

    @Column(length = 255)
    private String link;

    @Column(nullable = false)
    private int type;

    //bi-directional many-to-one association to TimelineStory
    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private TimelineStory timelineStory;

    public TimelineStoryMedia() {
    }

}
