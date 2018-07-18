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
import org.engim.tss2018.db.Articolo;

public class PaginaArticolo extends PaginaBase
{
  public PaginaArticolo()
  {
    List<IColumn<Articolo, String>> colonne = new LinkedList<>();
    
    PropertyColumn<Articolo, String> id = new PropertyColumn<>(Model.of("Id"), "id");
    PropertyColumn<Articolo, String> codice = new PropertyColumn<>(Model.of("Codice"), "codice");
    PropertyColumn<Articolo, String> desc = new PropertyColumn<>(Model.of("Descrizione"), "descrizione");
    PropertyColumn<Articolo, String> peso = new PropertyColumn<>(Model.of("Peso"), "peso");
    AbstractColumn<Articolo, String> azioni = new AbstractColumn<Articolo, String>(Model.of("Azioni"))
    {
      @Override
      public void populateItem(Item<ICellPopulator<Articolo>> item, String wicketId, IModel<Articolo> imodel)
      {
        item.add(new AzioniPanel(wicketId, imodel.getObject()));    
      }
    };
    
    colonne.add(id);
    colonne.add(codice);
    colonne.add(desc);
    colonne.add(peso);
    colonne.add(azioni);
    
    SPDataProvider<Articolo> dataprov = new SPDataProvider<>(Articolo.class);
    
    DefaultDataTable table = new DefaultDataTable("articoli", colonne, dataprov, 10);
    
    add(table);
    
    //add(new FormEditArticolo("editArticolo"));
  }
}
