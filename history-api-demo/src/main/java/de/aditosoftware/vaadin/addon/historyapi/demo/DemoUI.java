package de.aditosoftware.vaadin.addon.historyapi.demo;

import com.sun.tools.javac.util.List;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.renderers.TextRenderer;
import de.aditosoftware.vaadin.addon.historyapi.HistoryAPI;
import de.aditosoftware.vaadin.addon.historyapi.HistoryLink;
import de.aditosoftware.vaadin.addon.historyapi.HistoryLinkRenderer;

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

    historyAPI.addHistoryChangeListener(event -> {
      Notification.show("History changed " + event.getOrigin().name(), event.getURI().toString(), Type.HUMANIZED_MESSAGE);
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

    HistoryLink linkWrapper = new HistoryLink("TEST!", URI.create("/client"), historyAPI);
    linkWrapper.setIcon(VaadinIcons.ANCHOR);

    mainLayout.addComponent(linkWrapper);
    mainLayout.addComponent(createTestGrid(historyAPI));
  }

  private Grid<TestGridData> createTestGrid (HistoryAPI historyAPI) {
    Grid<TestGridData> grid = new Grid<>();

    HistoryLinkRenderer linkRenderer = new HistoryLinkRenderer(historyAPI);

    grid.addColumn(TestGridData::getText, new TextRenderer());
    grid.addColumn(TestGridData::getLinkData, linkRenderer);

    grid.setItems(List.of(
        new TestGridData("first", new HistoryLinkRenderer.Data("first link", URI.create("/client/first"))),
        new TestGridData("second", new HistoryLinkRenderer.Data("second link", URI.create("/client/second"))),
        new TestGridData("third", new HistoryLinkRenderer.Data("second link", null)),
        new TestGridData("fourth", null)
    ));

    return grid;
  }

  private class TestGridData {
    public String text;
    public HistoryLinkRenderer.Data linkData;

    public TestGridData (String text, HistoryLinkRenderer.Data linkData) {
      this.text = text;
      this.linkData = linkData;
    }

    public String getText () {
      return text;
    }

    public HistoryLinkRenderer.Data getLinkData () {
      return linkData;
    }
  }
}
