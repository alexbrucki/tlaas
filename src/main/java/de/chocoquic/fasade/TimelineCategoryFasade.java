package de.chocoquic.fasade;

import com.querydsl.jpa.impl.JPAQuery;
import de.chocoquic.entity.QTimelineCategory;

import de.chocoquic.entity.TimelineCategory;
import de.chocoquic.entity.TimelineData;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import java.util.List;

@Named
@ApplicationScoped
public class TimelineCategoryFasade extends GenericFasade<QTimelineCategory, TimelineCategory> {

    @Inject
    private EntityManager entityManager;

    public List<TimelineCategory> findByTimelineData(TimelineData timelineData) {
//        TypedQuery<TimelineCategory> query = entityManager.createNamedQuery("TimelineCategory.findByTimelineData", TimelineCategory.class);
//        query.setParameter("timelineData", timelineData);
//        ArrayList<TimelineCategory> result = new ArrayList<>();
//        result.addAll(query.getResultList());
// return result;

        return new JPAQuery<TimelineCategory>(entityManager)
                .from(qPath)
                .where(qPath.timelineData.eq(timelineData))
                .fetch();

    }

}
