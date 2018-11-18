package de.chocoquic.fasade;

import de.chocoquic.entity.QTimelineCategory;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import de.chocoquic.entity.TimelineCategory;
import de.chocoquic.entity.TimelineData;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@ApplicationScoped
public class TimelineCategoryFasade extends GenericFasade<QTimelineCategory,TimelineCategory> {
   
    @Inject
    private EntityManager entityManager;

    public ArrayList<TimelineCategory> findByTimelineData(TimelineData timelineData) {
        TypedQuery<TimelineCategory> query = entityManager.createNamedQuery("TimelineCategory.findByTimelineData", TimelineCategory.class);
        query.setParameter("timelineData", timelineData);
        ArrayList<TimelineCategory> result = new ArrayList<>();
        result.addAll(query.getResultList());

        return result;
    }

}
