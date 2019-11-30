package de.aditosoftware.vaadin.addon.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.aditosoftware.vaadin.addon.HistoryAPI;
import javax.servlet.annotation.WebServlet;

@Theme("demo")
@Title("MyComponent Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

  @WebServlet(value = "/*", asyncSupported = true)
  @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
  public static class Servlet extends VaadinServlet {

  }

  @Override
  protected void init(VaadinRequest request) {
    // Show it in the middle of the screen
    final VerticalLayout layout = new VerticalLayout();
    layout.setStyleName("demoContentLayout");
    layout.setSizeFull();
    layout.setMargin(false);
    layout.setSpacing(false);
    setContent(layout);

    HistoryAPI historyAPI = HistoryAPI.forUI(UI.getCurrent());

    historyAPI.addPopStateListener(System.out::println);

    //historyAPI.pushState(new TestPOJO(), "", "/test");

    System.out.println(historyAPI);
  }

  private static class TestPOJO {

    private boolean test = true;
    private String ttt = "asdf";
  }
}
