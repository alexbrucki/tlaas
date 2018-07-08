package de.chocoquic.ltass.dao;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import de.chocoquic.ltass.entity.TimelineData;
import de.chocoquic.ltass.entity.TimelineStory;

public class TimelineStoryDao extends GenericDao {

    public TimelineStory findById(Long id) {
        return em.find(TimelineStory.class, id);

    }

    public ArrayList<TimelineStory> findByTimelineData(TimelineData timelineData) {
        TypedQuery<TimelineStory> query = em.createNamedQuery("TimelineStory.findAllByTimelineData", TimelineStory.class);
        query.setParameter("timelineData", timelineData);
        ArrayList<TimelineStory> result = new ArrayList<>();
        result.addAll(query.getResultList());
        return result;
    }
}
