package de.chocoquic.dao;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {

    // Expose an entity manager using the resource producer pattern
    @PersistenceContext(unitName = "mdsat-pu")
    @Produces
    private EntityManager em;

}
