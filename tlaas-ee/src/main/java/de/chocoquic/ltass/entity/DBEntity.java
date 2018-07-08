package de.chocoquic.ltass.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@SuppressWarnings("serial")
@MappedSuperclass
@Data
public abstract class DBEntity implements Serializable, Cloneable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", insertable = false, updatable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "changed", insertable = false, updatable = false)
    private Date changed;

    @Override
    public DBEntity clone() throws CloneNotSupportedException {
        DBEntity dbEntity = (DBEntity) super.clone();
        dbEntity.setId(null);
        return dbEntity;
    }

}
