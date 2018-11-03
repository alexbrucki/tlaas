package de.chocoquic.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the user_role database table.
 *
 */
@Entity
@Data
@EqualsAndHashCode(of = "userRole")
@Table(name = "user_role")
@NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")
public class UserRole extends DBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_role", nullable = false, length = 255)
    private String userRole;

    //bi-directional many-to-one association to TimelineUserRole
    @OneToMany(mappedBy = "userRole")
    private Set<TimelineUserRole> timelineUserRoles;

    public UserRole() {
    }
}
