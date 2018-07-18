package org.engim.tss2018;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;
import org.engim.tss2018.dao.DAOGenerico;
import org.engim.tss2018.db.Articolo;

public class FormEditArticolo extends Form<Articolo>
{
  private Articolo m;
  
  public FormEditArticolo(String id)
  {
    super(id);
    
    add(new FeedbackPanel("feedback"));
    m = new Articolo();
    
    setDefaultModel(new CompoundPropertyModel<Articolo>(m));
    
    TextField codice = new TextField("codice");
    codice.setRequired(true);
    add(codice);
    
    TextField desc = new TextField("descrizione");
    desc.setRequired(true);
    add(desc);
    
    NumberTextField peso = new NumberTextField("peso");
    peso.setRequired(true);
    peso.setStep(0.01);
    peso.add(new RangeValidator<Float>(Float.valueOf(0.01f), Float.valueOf(100000)));
    add(peso);
  }

  @Override
  protected void onBeforeRender()
  {
    m = new Articolo();
    setDefaultModel(new CompoundPropertyModel<Articolo>(m));
    super.onBeforeRender();
  }
  
  @Override
  protected void onSubmit()
  {
    DAOGenerico.inserisci_o_aggiorna(m);
  }
}
