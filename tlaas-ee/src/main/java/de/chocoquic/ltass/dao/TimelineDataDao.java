package de.chocoquic.ltass.dao;

import de.chocoquic.ltass.entity.TimelineData;

public class TimelineDataDao extends GenericDao {

    public TimelineData findById(Long id) {
        return em.find(TimelineData.class, id);
    }

}
