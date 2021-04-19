package de.aditosoftware.vaadin.addon.historyapi;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

public class TestUI extends UI {
  @Override
  protected void init(VaadinRequest request) {
    setContent(new CssLayout());
  }
}
