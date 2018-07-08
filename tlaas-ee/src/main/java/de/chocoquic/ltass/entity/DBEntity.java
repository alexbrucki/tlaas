package de.chocoquic.ltass.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@MappedSuperclass
@EqualsAndHashCode(of = "id")
@Data
public abstract class DBEntity implements Serializable, Cloneable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Column(name = "changed")
    private LocalDateTime changed;

    @Column(name = "changed_by")
    private String username;
    
    @Override
    public DBEntity clone() throws CloneNotSupportedException {
        DBEntity dbEntity = (DBEntity) super.clone();
        dbEntity.setId(null);
        return dbEntity;
    }

}
