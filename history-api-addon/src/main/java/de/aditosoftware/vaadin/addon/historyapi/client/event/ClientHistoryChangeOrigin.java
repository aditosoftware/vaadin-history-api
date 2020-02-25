package de.aditosoftware.vaadin.addon.historyapi.client.event;

/**
 * Describes from where the HistoryChange event originates.
 */
public enum ClientHistoryChangeOrigin {
  /**
   * If the change originates from a PopState event.
   */
  POPSTATE,
  /**
   * If the change originates from a Anchor click.
   */
  ANCHOR
}
