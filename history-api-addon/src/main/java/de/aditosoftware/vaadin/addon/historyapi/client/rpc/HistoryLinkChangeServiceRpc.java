package de.aditosoftware.vaadin.addon.historyapi.client.rpc;

/**
 * Describes a service rpc which accepts a click callback. This basically just extends {@link
 * HistoryChangeServerRpc}.
 */
public interface HistoryLinkChangeServiceRpc extends HistoryChangeServerRpc {
  /** Will handle the event if a user clicked on the link. */
  void onClick();
}
