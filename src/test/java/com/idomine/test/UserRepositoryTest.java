package com.idomine.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.idomine.domain.User;

@RunWith(Arquillian.class)
public class UserRepositoryTest
{

    @Inject
    EntityManager em;

    @Test
    public void shouldBeAbleTo() throws Exception
    {
        criarUser();
        assertTrue(Long.valueOf(em.createNativeQuery("select count(*) from user").getSingleResult().toString()) > 0);
    }

    
    @Inject
    UserTransaction utx;
    
    private void criarUser() throws Exception
    {
        User u = new User();
        u.setId(1L);
        u.setName("Lyndon");
        utx.begin();
        em.merge(u);
        utx.commit();
    }

    @Deployment
    public static Archive<?> createDeployment()
    {
        WebArchive webAppTest = ShrinkWrap
                .create(WebArchive.class)
                .addPackages(true, "com.idomine.application")
                .addPackages(true, "com.idomine.domain")
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsResource("starter.properties")
                .addAsResource("import.sql")
                .addAsResource("admin-config.properties")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml")
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies()
                        .resolve().withTransitivity().asFile());
        webAppTest.writeTo(System.out, org.jboss.shrinkwrap.api.formatter.Formatters.VERBOSE);
        return webAppTest;
    }

}
