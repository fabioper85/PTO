package org.engim.tss2018;

import org.apache.wicket.markup.html.WebPage;

public class PaginaBase extends WebPage
{

  public PaginaBase()
  {
    NavMenu menu = new NavMenu("navmenu");
    add(menu);
  }
  
}
