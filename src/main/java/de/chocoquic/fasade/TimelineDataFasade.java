package de.chocoquic.fasade;

import de.chocoquic.entity.TimelineData;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@ApplicationScoped
public class TimelineDataFasade extends GenericFasade<TimelineData> {

    @Inject
    private EntityManager entityManager;



}
