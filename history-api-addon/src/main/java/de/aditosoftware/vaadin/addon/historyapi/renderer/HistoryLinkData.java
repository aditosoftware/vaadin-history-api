package de.aditosoftware.vaadin.addon.historyapi.renderer;

import java.net.URI;

/**
 * Represents a data class which represents the required data for the
 * {@link HistoryLinkRenderer}.
 */
public class HistoryLinkData {
  private String text;
  private URI uri;

  public HistoryLinkData (String text, URI uri) {
    this.text = text;
    this.uri = uri;
  }

  public String getText () {
    return text;
  }

  public URI getURI () {
    return uri;
  }
}
