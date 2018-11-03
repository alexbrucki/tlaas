package de.chocoquic.dao;

import de.chocoquic.entity.TimelineData;

public class TimelineDataDao extends GenericDao {

    public TimelineData findById(Long id) {
        return em.find(TimelineData.class, id);
    }

}
