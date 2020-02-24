package de.aditosoftware.vaadin.addon.historyapi.demo;

import com.sun.tools.javac.util.List;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.renderers.TextRenderer;
import de.aditosoftware.vaadin.addon.historyapi.HistoryAPI;
import de.aditosoftware.vaadin.addon.historyapi.component.HistoryLink;
import de.aditosoftware.vaadin.addon.historyapi.renderer.HistoryLinkData;
import de.aditosoftware.vaadin.addon.historyapi.renderer.HistoryLinkRenderer;

import javax.servlet.annotation.WebServlet;
import java.net.URI;
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

    final VerticalLayout mainLayout = new VerticalLayout(buttonLayout, linkLayout);
    mainLayout.setWidth(100, Unit.PERCENTAGE);
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

    HistoryLink linkWrapper = new HistoryLink(new Label("TEST!"), URI.create("/client"));
    linkWrapper.addLinkClickListener(event -> {
      Notification.show("History changed through HistoryLink", event.getLinkURI().toString(), Type.HUMANIZED_MESSAGE);
    });

    mainLayout.addComponent(linkWrapper);
    mainLayout.addComponent(createTestGrid());
  }

  private Grid<TestGridData> createTestGrid () {
    Grid<TestGridData> grid = new Grid<>();

    HistoryLinkRenderer linkRenderer = new HistoryLinkRenderer();
    linkRenderer.addLinkClickListener(event -> {
      Notification.show("History changed through HistoryLinkRenderer", event.getLinkURI().toString(), Type.HUMANIZED_MESSAGE);
    });

    grid.addColumn(TestGridData::getText, new TextRenderer());
    grid.addColumn(TestGridData::getLinkData, linkRenderer);

    grid.setItems(List.of(
        new TestGridData("first", new HistoryLinkData("first link", URI.create("/client/first"))),
        new TestGridData("second", new HistoryLinkData("second link", URI.create("/client/second")))
    ));

    return grid;
  }

  private class TestGridData {
    public String text;
    public HistoryLinkData linkData;

    public TestGridData (String text, HistoryLinkData linkData) {
      this.text = text;
      this.linkData = linkData;
    }

    public String getText () {
      return text;
    }

    public HistoryLinkData getLinkData () {
      return linkData;
    }
  }
}
