package org.engim.tss2018.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.engim.tss2018.PM;
import org.engim.tss2018.db.ChiavePrimaria;

public class DAOGenerico
{
  public static void inserisci_o_aggiorna(ChiavePrimaria p)
  {
    EntityManager em = PM.db();
    EntityTransaction et = em.getTransaction(); // BEGIN
    try
    {
      et.begin();
      if (p.getId() == null)
      {
        em.persist(p);
      }
      else
      {
        em.merge(p);
      }
      et.commit();
    }
    finally
    {
      if (et.isActive()) 
        et.rollback();
      em.close();
    }
  }
  
  public static void elimina(ChiavePrimaria p)
  {
    EntityManager em = PM.db();
    EntityTransaction et = em.getTransaction(); 
    try
    {
      p = em.find(p.getClass(), p.getId());
      et.begin(); 
      em.remove(p);
      et.commit(); 
    }
    finally
    {
      if (et.isActive()) 
        et.rollback();
      em.close();
    }
  }
}
