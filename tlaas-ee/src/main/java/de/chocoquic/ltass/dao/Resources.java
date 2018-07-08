package de.chocoquic.ltass.dao;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {

    // Expose an entity manager using the resource producer pattern
    @PersistenceContext(unitName = "tlaas-pu")
    @Produces
    private EntityManager em;

}
