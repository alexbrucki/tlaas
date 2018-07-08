package de.chocoquic.ltass.dao;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import de.chocoquic.ltass.entity.TimelineCategory;
import de.chocoquic.ltass.entity.TimelineData;

public class TimelineCategoryDao extends GenericDao {

    public TimelineCategory findById(Long id) {
        return em.find(TimelineCategory.class, id);
    }

    public ArrayList<TimelineCategory> findByTimelineData(TimelineData timelineData) {
        TypedQuery<TimelineCategory> query = em.createNamedQuery("TimelineCategory.findByTimelineData", TimelineCategory.class);
        query.setParameter("timelineData", timelineData);
        ArrayList<TimelineCategory> result = new ArrayList<>();
        result.addAll(query.getResultList());

        return result;
    }

}
