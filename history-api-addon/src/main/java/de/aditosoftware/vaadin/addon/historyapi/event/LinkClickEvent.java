package de.aditosoftware.vaadin.addon.historyapi.event;

import de.aditosoftware.vaadin.addon.historyapi.component.HistoryLink;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.EventObject;

/**
 * Represents an event which is used when a {@link HistoryLink} or a
 * has been clicked.
 */
public class LinkClickEvent extends EventObject {
  private URI linkURI;

  /**
   * Constructs a prototypical Event.
   *
   * @param source the object on which the Event initially occurred
   * @throws IllegalArgumentException if source is null
   */
  public LinkClickEvent (Object source, @NotNull URI pURI) {
    super(source);
    linkURI = pURI;
  }

  /**
   * Will return the {@link URI} from the link.
   *
   * @return The URI.
   */
  @NotNull
  public URI getLinkURI () {
    return linkURI;
  }
}
