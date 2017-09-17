package org.brewtraption.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {

  private static EntityManagerFactory sessionFactory = null;

  public static EntityManagerFactory getSessionFactory() {
    if (sessionFactory == null) {
      sessionFactory = Persistence.createEntityManagerFactory("org.brewtraption.jpa");
    }
    return sessionFactory;
  }

}
