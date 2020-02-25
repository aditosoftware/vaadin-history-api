package de.aditosoftware.vaadin.addon.historyapi.event;

/**
 * Describes the origin of history change.
 */
public enum HistoryChangeOrigin {
  /**
   * If the change originates form a PopState event.
   */
  POPSTATE,
  /**
   * If the change originates from a Anchor-Element click.
   */
  ANCHOR
}
