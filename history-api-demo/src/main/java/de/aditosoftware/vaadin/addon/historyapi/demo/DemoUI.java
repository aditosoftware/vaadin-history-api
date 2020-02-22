package de.aditosoftware.vaadin.addon.historyapi.demo;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import de.aditosoftware.vaadin.addon.historyapi.HistoryAPI;
import de.aditosoftware.vaadin.addon.historyapi.HistoryLinkWrapper;

import javax.servlet.annotation.WebServlet;
import java.util.concurrent.atomic.AtomicInteger;

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
  protected void init (VaadinRequest request) {
    HistoryAPI historyAPI = HistoryAPI.forUI(UI.getCurrent());

    final VerticalLayout linkLayout = new VerticalLayout();
    linkLayout.setWidth(25, Unit.PERCENTAGE);
    linkLayout.setMargin(false);
    linkLayout.setSpacing(false);

    // Show it in the middle of the screen
    final VerticalLayout buttonLayout = new VerticalLayout();
    buttonLayout.setWidth(25, Unit.PERCENTAGE);
    buttonLayout.setMargin(false);
    buttonLayout.setSpacing(false);

    final HorizontalLayout mainLayout = new HorizontalLayout(buttonLayout, linkLayout);
    mainLayout.setWidth(50, Unit.PERCENTAGE);
    buttonLayout.setMargin(false);
    buttonLayout.setSpacing(false);

    setContent(mainLayout);

    historyAPI.addPopStateListener(event -> {
      Notification.show("History changed", event.getUri().toString(), Type.HUMANIZED_MESSAGE);
    });

    AtomicInteger counter = new AtomicInteger(1);

    Button pushButton = new Button("Push state");
    pushButton.addClickListener(
        event -> historyAPI.pushState("/push/" + counter.getAndIncrement()));
    buttonLayout.addComponent(pushButton);

    Button replaceButton = new Button("Replace state");
    replaceButton.addClickListener(event -> historyAPI
        .replaceState("/replace/" + counter.getAndIncrement()));
    buttonLayout.addComponent(replaceButton);

    Button backButton = new Button("Back");
    backButton.addClickListener(event -> historyAPI.back());
    buttonLayout.addComponent(backButton);

    Button forwardButton = new Button("Forward");
    forwardButton.addClickListener(event -> historyAPI.forward());
    buttonLayout.addComponent(forwardButton);

    HistoryLinkWrapper linkWrapper = new HistoryLinkWrapper(new ExternalResource("/client/"), new Label("TEST!"), historyAPI);

    mainLayout.addComponent(linkWrapper);
  }
}
