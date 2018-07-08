/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chocoquic.itest;

import de.chocoquic.ltass.dao.ExampleDao;
import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.Coordinate;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenDependencies;


import static org.jboss.shrinkwrap.api.Filters.exclude;
import static org.jboss.shrinkwrap.resolver.api.maven.ScopeType.RUNTIME;

/**
 * Extend for Arquillian as deployer.
 *
 * @author jens.papenhagen
 */
public class ArquillianProjectArchive {

    @Deployment
    public static WebArchive createDeployment() {
        // Compile Safe Packages.
        Package projectPackage = ExampleDao.class.getPackage();
        Package itestPackage = ArquillianProjectArchive.class.getPackage();

        File[] libs = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .addDependency(MavenDependencies.createDependency("org.slf4j:slf4j-log4j12", RUNTIME, false)) // Log4J API
                .addDependency(MavenDependencies.createDependency("org.assertj:assertj-core", RUNTIME, false)) // AssertJ Fluent Assertions
                .resolve().withTransitivity().asFile();
        
        WebArchive war = ShrinkWrap.create(WebArchive.class, "tlaas-integrationtest.war")
                .addPackages(true, exclude(itestPackage), projectPackage)
                .addClass(Coordinate.class) // Need this cause of the maven resolver is part of the deployment
                .addClass(ArquillianProjectArchive.class)
                .addClass(Utils.class)
                .addAsResource(new ClassLoaderAsset("META-INF/test-persistence.xml"), "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(libs);
        return war;
    }
}
