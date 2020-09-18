package de.aditosoftware.vaadin.addon.historyapi.client.util;

import com.google.gwt.event.dom.client.ClickEvent;
import de.aditosoftware.vaadin.addon.historyapi.client.accessor.HistoryAPINativeAccessor;

/**
 * Represents a utility class which offers some methods which simplify usage
 * with HistoryLinks.
 */
public class HistoryLinkUtil {
  /**
   * Will handle the given {@link ClickEvent} and check if the event is
   * catchable or not. In addition this will prevent the default action and
   * execute the pushState.
   *
   * @param uri   The URI which shall be used for the pushState.
   * @param event The original ClickEvent.
   * @return If the click was catchable or not.
   */
  public static boolean handleAnchorClick (String uri, ClickEvent event) {
    // Check if the event is catchable.
    if (!isCatchableEvent(event))
      return false;

    // Prevent the default action (opening the link in this case).
    event.preventDefault();

    // Stop propagation of the click event to the parents. This is necessary
    // to avoid sending the click event to the ItemClick listener if
    // HandleWidgetEvents is enabled.
    event.stopPropagation();

    // Push a new state using the given uri.
    HistoryAPINativeAccessor.pushState(null, null, uri);

    return true;
  }

  /**
   * Will check if the given {@link ClickEvent} is a catchable event. If this
   * returns true if the event can be prevented and the pushState can be
   * executed.
   *
   * @param event The original ClickEvent.
   * @return If the ClickEvent is catchable or not.
   */
  public static boolean isCatchableEvent (ClickEvent event) {
    if (event.isMetaKeyDown()
        || event.isAltKeyDown()
        || event.isControlKeyDown()
        || event.isShiftKeyDown())
      return false;

    return true;
  }
}
