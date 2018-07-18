package org.engim.tss2018.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.engim.tss2018.PM;
import org.engim.tss2018.db.Ordine;
import org.engim.tss2018.db.TariffaCorriere;
import org.engim.tss2018.db.Voce;

public class DAO_Ordini
{
  
  public static Float pesoTotaleOrdine(Ordine ordine)
  {
    EntityManager db = PM.db();
    
    try
    {
      Ordine reloaded = db.find(Ordine.class, ordine.getId());
      Float pesoTot = 0f;
      
      for(Voce voce: reloaded.getVoceCollection())
      {
        pesoTot += voce.getQuantita() * voce.getIdArticolo().getPeso();
      };
      
      return pesoTot;
    }
    
    finally
    {
      db.close();
    }
  }
  
  public static TariffaCorriere tariffaMigliore(Ordine ordine)
  {
    float pesoSped = DAO_Ordini.pesoTotaleOrdine(ordine);
    
    EntityManager db = PM.db();
    
    try
    {
      TypedQuery<TariffaCorriere> allTariffe = db.createNamedQuery("TariffaCorriere.findAll", TariffaCorriere.class);

      List<TariffaCorriere> results = allTariffe.getResultList();

      TariffaCorriere min = null;

      for(TariffaCorriere cmt: results)
      {
        if(cmt.getPesoMassimo() >= pesoSped)
        {
          if(min == null)
          {
            min = cmt;
          }
          else
          {
            if(cmt.getCosto() < min.getCosto())
              min = cmt;
          }
        }
      }
      return min;
    }
    
    finally
    {
      db.close();
    }
  }
}