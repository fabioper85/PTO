package org.engim.tss2018;

import java.util.LinkedList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.engim.tss2018.dao.DAO_Ordini;
import org.engim.tss2018.db.Ordine;
import org.engim.tss2018.db.TariffaCorriere;

public class PaginaOrdine extends PaginaBase
{
  public PaginaOrdine()
  {
    List<IColumn<Ordine, String>> colonne = new LinkedList<>();
    
    PropertyColumn<Ordine, String> id = new PropertyColumn<>(Model.of("Id"), "id");
    PropertyColumn<Ordine, String> numeroOrdine = new PropertyColumn<>(Model.of("Numero ordine"), "numero");
    PropertyColumn<Ordine, String> data = new PropertyColumn<>(Model.of("Data"), "data");
    AbstractColumn<Ordine, String> pesoTot = new AbstractColumn<Ordine, String>(Model.of("Peso Tot."))
    {
      @Override
      public void populateItem(Item<ICellPopulator<Ordine>> item, String wicketId, IModel<Ordine> rowModel)
      {
        String pesoTot = String.format("%.2f", DAO_Ordini.pesoTotaleOrdine(rowModel.getObject()));
        Label l_pesoTot = new Label(wicketId, pesoTot);
        item.add(l_pesoTot);
      }
    };
    
    AbstractColumn<Ordine, String> tariffaMigliore = new AbstractColumn<Ordine, String>(Model.of("Tariffa migliore"))
    {
      @Override
      public void populateItem(Item<ICellPopulator<Ordine>> item, String wicketId, IModel<Ordine> rowModel)
      {
        TariffaCorriere bestTC = DAO_Ordini.tariffaMigliore(rowModel.getObject());
        String bestTariffa = "€ " + String.format("%.2f", bestTC.getCosto()) + " (" + bestTC.getNomeCorriere() + " " + bestTC.getNomeTariffa() + ")";
        Label l_bestTariffa = new Label(wicketId, bestTariffa);
        item.add(l_bestTariffa);
      }
    };
    
    AbstractColumn<Ordine, String> azioni = new AbstractColumn<Ordine, String>(Model.of("Azioni"))
    {
      @Override
      public void populateItem(Item<ICellPopulator<Ordine>> item, String wicketId, IModel<Ordine> imodel)
      {
        item.add(new AzioniPanel(wicketId, imodel.getObject()));    
      }
    };
    
    colonne.add(id);
    colonne.add(numeroOrdine);
    colonne.add(data);
    colonne.add(pesoTot);
    colonne.add(tariffaMigliore);
    colonne.add(azioni);
    
    SPDataProvider<Ordine> dataprov = new SPDataProvider<>(Ordine.class);
    
    DefaultDataTable table = new DefaultDataTable("elencoOrdini", colonne, dataprov, 10);
    
    add(table);
  }
}
