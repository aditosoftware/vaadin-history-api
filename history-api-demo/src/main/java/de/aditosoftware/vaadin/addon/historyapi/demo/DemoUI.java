package de.aditosoftware.vaadin.addon.historyapi.demo;

import com.sun.tools.javac.util.List;
import com.vaadin.annotations.*;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.renderers.TextRenderer;
import de.aditosoftware.vaadin.addon.historyapi.*;

import javax.servlet.annotation.WebServlet;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Theme("demo")
@Title("History-API Demo")
@Push()
@SuppressWarnings("serial")
public class DemoUI extends UI
{

  @WebServlet(value = "/*", asyncSupported = true)
  @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
  public static class Servlet extends VaadinServlet
  {

  }

  @Override
  protected void init(VaadinRequest request)
  {
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
      System.out.println(event.getState());
    });

    AtomicInteger counter = new AtomicInteger(1);

    Button pushButton = new Button("Push state");
    pushButton.setId("test-mod-button-push-1");
    pushButton.addClickListener(
        event -> historyAPI.pushState("/push/" + counter.getAndIncrement()));
    buttonLayout.addComponent(pushButton);

    Button replaceButton = new Button("Replace state");
    replaceButton.setId("test-mod-button-replace-1");
    replaceButton.addClickListener(event -> historyAPI
        .replaceState("/replace/" + counter.getAndIncrement()));
    buttonLayout.addComponent(replaceButton);

    Button backButton = new Button("Back");
    backButton.setId("test-mod-button-back-1");
    backButton.addClickListener(event -> historyAPI.back());
    buttonLayout.addComponent(backButton);

    Button forwardButton = new Button("Forward");
    forwardButton.setId("test-mod-button-forward-1");
    forwardButton.addClickListener(event -> historyAPI.forward());
    buttonLayout.addComponent(forwardButton);

    Button pushWithStateButton = new Button("Push state (with state object)");
    pushWithStateButton.setId("test-mod-button-push-state-1");
    pushWithStateButton.addClickListener(event -> {
      int incremented = counter.getAndIncrement();

      Map<String, String> state = new HashMap<>();
      state.put("counter", "" + incremented);

      historyAPI.pushState("/push/" + incremented, state);
    });
    buttonLayout.addComponent(pushWithStateButton);

    HistoryLink linkWrapper = new HistoryLink("TEST!", URI.create("/client"), historyAPI);
    linkWrapper.setId("test-history-link-1");
    linkWrapper.setIcon(VaadinIcons.ANCHOR);

    Button focusButton = new Button("Focus link above");
    focusButton.addClickListener(event -> linkWrapper.focus());
    focusButton.setId("test-focus-button-1");

    mainLayout.addComponents(linkWrapper, focusButton);
    mainLayout.addComponent(createTestGrid(historyAPI));
  }

  private Grid<TestGridData> createTestGrid(HistoryAPI historyAPI)
  {
    Grid<TestGridData> grid = new Grid<>();
    grid.setId("test-grid-1");

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

  private class TestGridData
  {
    public String text;
    public HistoryLinkRenderer.Data linkData;

    public TestGridData(String text, HistoryLinkRenderer.Data linkData)
    {
      this.text = text;
      this.linkData = linkData;
    }

    public String getText()
    {
      return text;
    }

    public HistoryLinkRenderer.Data getLinkData()
    {
      return linkData;
    }
  }
}
