package de.aditosoftware.vaadin.addon.historyapi.client.link;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.SimplePanel;

public class HistoryLinkWrapperWidget extends SimplePanel {
  public HistoryLinkWrapperWidget () {
    super(DOM.createAnchor());

    setStylePrimaryName("v-history-link-wrapper");


    addDomHandler(new ClickHandler() {
      @Override
      public void onClick (ClickEvent event) {
        if (event.isMetaKeyDown() || event.isAltKeyDown() || event.isControlKeyDown() || event.isShiftKeyDown())
          return;

        event.preventDefault();

        test();
      }
    }, ClickEvent.getType());

  }

  private native void test () /*-{
    $wnd.history.pushState({}, "", "/test")
  }-*/;
}
