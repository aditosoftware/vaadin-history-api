package de.aditosoftware.vaadin.addon.historyapi.client.event;

import java.io.Serializable;

/** Describes the history change event on the client-side. */
public class ClientHistoryChangeEvent implements Serializable {
  public String uri;
  public String state;
  public ClientHistoryChangeOrigin origin;

  public ClientHistoryChangeEvent() {}

  public ClientHistoryChangeEvent(String uri, String state, ClientHistoryChangeOrigin origin) {
    this.uri = uri;
    this.state = state;
    this.origin = origin;
  }

  public String getURI() {
    return uri;
  }

  public String getState() {
    return state;
  }

  public ClientHistoryChangeOrigin getOrigin() {
    return origin;
  }
}
