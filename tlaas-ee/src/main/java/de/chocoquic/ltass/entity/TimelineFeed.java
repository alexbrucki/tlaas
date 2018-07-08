package de.chocoquic.ltass.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the timeline_feed database table.
 *
 */
@Entity
@Data
@EqualsAndHashCode(of = "feedType")
@Table(name = "timeline_feed")
@NamedQuery(name = "TimelineFeed.findAll", query = "SELECT t FROM TimelineFeed t")
public class TimelineFeed extends DBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "feed_type", length = 255)
    private String feedType;

    @Column(name = "feed_url", length = 255)
    private String feedUrl;

    @Column(length = 255)
    private String title;

    //bi-directional many-to-one association to TimelineData
    @ManyToOne
    @JoinColumn(name = "timeline_data_id", nullable = false)
    private TimelineData timelineData;

    public TimelineFeed() {
    }

}
