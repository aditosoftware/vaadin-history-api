package de.aditosoftware.vaadin.addon.client.accessor;

/**
 * Represents an PopState event for the client-side. This holds the state received by the event and
 * the URI at the moment the event occurs.
 */
public class PopStateEvent {

  private String uri;
  private String state;

  /**
   * Required zero-args constructor.
   */
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
