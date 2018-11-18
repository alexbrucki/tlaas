package de.chocoquic.fasade;

import de.chocoquic.entity.QTimelineStory;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import de.chocoquic.entity.TimelineData;
import de.chocoquic.entity.TimelineStory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@ApplicationScoped
public class TimelineStoryFasade extends GenericFasade<QTimelineStory, TimelineStory> {

    @Inject
    private EntityManager entityManager;

    public ArrayList<TimelineStory> findByTimelineData(TimelineData timelineData) {
        TypedQuery<TimelineStory> query = entityManager.createNamedQuery("TimelineStory.findAllByTimelineData", TimelineStory.class);
        query.setParameter("timelineData", timelineData);
        ArrayList<TimelineStory> result = new ArrayList<>();
        result.addAll(query.getResultList());
        return result;
    }
}
