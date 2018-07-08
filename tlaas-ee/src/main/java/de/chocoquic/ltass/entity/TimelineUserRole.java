package de.chocoquic.ltass.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the timeline_user_role database table.
 *
 */
@Entity
@Data
@EqualsAndHashCode(of = "serialVersionUID")
@Table(name = "timeline_user_role")
@NamedQuery(name = "TimelineUserRole.findAll", query = "SELECT t FROM TimelineUserRole t")
public class TimelineUserRole extends DBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //bi-directional many-to-one association to TimelineData
    @ManyToOne
    @JoinColumn(name = "timeline_data_id", nullable = false)
    private TimelineData timelineData;

    //bi-directional many-to-one association to TimelineUser
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private TimelineUser timelineUser;

    //bi-directional many-to-one association to UserRole
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private UserRole userRole;

    public TimelineUserRole() {
    }

}
