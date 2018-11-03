package de.chocoquic.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the timeline_user database table.
 *
 */
@Entity
@Data
@EqualsAndHashCode(of = "account_type")
@Table(name = "timeline_user")
@NamedQuery(name = "TimelineUser.findAll", query = "SELECT t FROM TimelineUser t")
public class TimelineUser extends DBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "account_type", nullable = false, length = 255)
    private String accountType;

    @Column(nullable = false, length = 255)
    private String pass;

    @Column(nullable = false, length = 255)
    private String salt;

    @Column(name = "user_name", nullable = false, length = 255)
    private String userName;

    //bi-directional many-to-many association to TimelineData
    @ManyToMany
    @JoinTable(name = "timeline_user_role", joinColumns = {
        @JoinColumn(name = "user_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "timeline_data_id", nullable = false)})
    private Set<TimelineData> timelineData;

    //bi-directional many-to-one association to TimelineUserRole
    @OneToMany(mappedBy = "timelineUser")
    private Set<TimelineUserRole> timelineUserRoles;

    public TimelineUser() {
    }

    public TimelineUserRole addTimelineUserRole(TimelineUserRole timelineUserRole) {
        getTimelineUserRoles().add(timelineUserRole);
        timelineUserRole.setTimelineUser(this);

        return timelineUserRole;
    }

    public TimelineUserRole removeTimelineUserRole(TimelineUserRole timelineUserRole) {
        getTimelineUserRoles().remove(timelineUserRole);
        timelineUserRole.setTimelineUser(null);

        return timelineUserRole;
    }

}
