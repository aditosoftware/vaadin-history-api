package de.aditosoftware.vaadin.addon.historyapi.demo;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.aditosoftware.vaadin.addon.historyapi.HistoryAPI;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.annotation.WebServlet;

@Theme("demo")
@Title("MyComponent Add-on Demo")
@Push()
@SuppressWarnings("serial")
public class DemoUI extends UI {

  @WebServlet(value = "/*", asyncSupported = true)
  @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
  public static class Servlet extends VaadinServlet {

  }

  @Override
  protected void init(VaadinRequest request) {
    HistoryAPI historyAPI = HistoryAPI.forUI(UI.getCurrent());

    // Show it in the middle of the screen
    final VerticalLayout layout = new VerticalLayout();
    layout.setWidth(25, Unit.PERCENTAGE);
    layout.setMargin(false);
    layout.setSpacing(false);
    setContent(layout);

    historyAPI.addPopStateListener(event -> {
      Notification.show("History changed", event.getUri().toString(), Type.HUMANIZED_MESSAGE);
    });

    AtomicInteger counter = new AtomicInteger(1);

    Button pushButton = new Button("Push state");
    pushButton.addClickListener(
        event -> historyAPI.pushState((String) null, null, "/push/" + counter.getAndIncrement()));
    layout.addComponent(pushButton);

    Button replaceButton = new Button("Replace state");
    replaceButton.addClickListener(event -> historyAPI
        .replaceState((String) null, null, "/replace/" + counter.getAndIncrement()));
    layout.addComponent(replaceButton);

    Button backButton = new Button("Back");
    backButton.addClickListener(event -> historyAPI.back());
    layout.addComponent(backButton);

    Button forwardButton = new Button("Forward");
    forwardButton.addClickListener(event -> historyAPI.forward());
    layout.addComponent(forwardButton);

  }
}
