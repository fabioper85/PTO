package org.engim.tss2018;

import java.util.LinkedList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.engim.tss2018.db.TariffaCorriere;

public class PaginaTariffaCorriere extends PaginaBase
{
  public PaginaTariffaCorriere()
  {
    List<IColumn<TariffaCorriere, String>> colonne = new LinkedList<>();
    
    PropertyColumn<TariffaCorriere, String> id = new PropertyColumn<>(Model.of("Id"), "id");
    PropertyColumn<TariffaCorriere, String> nomeCorriere = new PropertyColumn<>(Model.of("Nome corriere"), "nomeCorriere");
    PropertyColumn<TariffaCorriere, String> nomeTariffa = new PropertyColumn<>(Model.of("Nome Tariffa"), "nomeTariffa");
    PropertyColumn<TariffaCorriere, String> pesoMax = new PropertyColumn<>(Model.of("Peso massimo consentito"), "pesoMassimo");
    AbstractColumn<TariffaCorriere, String> azioni = new AbstractColumn<TariffaCorriere, String>(Model.of("Azioni"))
    {
      @Override
      public void populateItem(Item<ICellPopulator<TariffaCorriere>> item, String wicketId, IModel<TariffaCorriere> imodel)
      {
        item.add(new AzioniPanel(wicketId, imodel.getObject()));    
      }
    };
    
    colonne.add(id);
    colonne.add(nomeCorriere);
    colonne.add(nomeTariffa);
    colonne.add(pesoMax);
    colonne.add(azioni);
    
    SPDataProvider<TariffaCorriere> dataprov = new SPDataProvider<>(TariffaCorriere.class);
    
    DefaultDataTable table = new DefaultDataTable("elencoTariffe", colonne, dataprov, 10);
    
    add(table);
    
    //add(new FormEditTariffaCorriere("editTariffaCorriere"));
  }
}
