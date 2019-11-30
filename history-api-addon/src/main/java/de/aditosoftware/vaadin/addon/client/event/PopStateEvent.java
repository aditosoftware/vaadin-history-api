package de.aditosoftware.vaadin.addon.client.event;

/**
 * Represents an PopState event for the client-side.
 */
public class PopStateEvent {

  private String uri;
  private String state;

  public PopStateEvent() {
  }

  public PopStateEvent(String uri, String state) {
    this.uri = uri;
    this.state = state;
  }

  public String getUri() {
    return uri;
  }

  public String getState() {
    return state;
  }
}
