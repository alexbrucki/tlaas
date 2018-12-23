package de.chocoquic.fasade;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import de.chocoquic.entity.TimelineCategory;
import de.chocoquic.entity.TimelineData;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Stream;

@Named
@ApplicationScoped
public class TimelineCategoryFasade extends GenericFasade<TimelineCategory> {

    @Inject
    private EntityManager em;

    /**
     * Find all TimelineCategory with this given TimelineData <br/>
     *
     * //TODO Testing the JPAQuery or stay with the namedQuery
     *
     * @param timelineData
     * @return allways A List
     */
    public List<TimelineCategory> findByTimelineData(TimelineData timelineData) {
//        TypedQuery<TimelineCategory> query = entityManager.createNamedQuery("TimelineCategory.findByTimelineData", TimelineCategory.class);
//        query.setParameter("timelineData", timelineData);
//        List<TimelineCategory> result = new ArrayList<>();
//        result.addAll(query.getResultList());
// return result;

        Predicate filter = getBuilder().getSimple("timelineData", TimelineData.class).eq(timelineData);
        return new JPAQuery<TimelineCategory>(em)
                .from(qPath)
                .where(filter).fetch();
}

}
