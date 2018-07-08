package de.chocoquic.ltass.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the timeline_span database table.
 *
 */
@Entity
@Data
@EqualsAndHashCode(of = "color")
@Table(name = "timeline_span")
@NamedQuery(name = "TimelineSpan.findAll", query = "SELECT t FROM TimelineSpan t")
public class TimelineSpan extends DBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(length = 65)
    private String color;

    @Column(length = 255)
    private String credit;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(length = 255)
    private String image;

    private int opacity;

    @Column(length = 65)
    private String overlay;

    private Boolean slider;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(length = 65)
    private String style;

    private Boolean text;

    @Column(length = 255)
    private String title;

    //bi-directional many-to-one association to TimelineData
    @ManyToOne
    @JoinColumn(name = "timeline_data_id", nullable = false)
    private TimelineData timelineData;

    public TimelineSpan() {
    }

}
