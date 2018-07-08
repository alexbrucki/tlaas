/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.chocoquic.itest.dao;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.chocoquic.itest.ArquillianProjectArchive;
import de.chocoquic.itest.Utils;
import de.chocoquic.ltass.dao.ExampleDao;

/**
 *
 * @author jens.papenhagen
 */
@RunWith(Arquillian.class)
public class ExampleEaoIT extends ArquillianProjectArchive {

    @Inject
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    @Inject
    private ExampleDao dao;

    private Date today = new Date();

    @After
    public void teardown() throws Exception {
        utx.begin();
        em.joinTransaction();
        Utils.clearH2Db(em);
        utx.commit();
    }

    @Test
    public void testFindByIdEager() throws Exception {

        utx.begin();
        em.joinTransaction();
        em.persist(null);
        utx.commit();

    }

}
