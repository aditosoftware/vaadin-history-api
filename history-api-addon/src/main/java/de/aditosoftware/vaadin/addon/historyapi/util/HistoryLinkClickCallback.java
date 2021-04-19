package de.aditosoftware.vaadin.addon.historyapi.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

/** Defines the click callback for a link. This can be implemented as functional interface. */
@FunctionalInterface
public interface HistoryLinkClickCallback {
  /**
   * Will handle the actual click on the link. The return value defines the follow-up action for the
   * click. This may either be PUSH or REPLACE or null if nothing shall be done.
   *
   * @param pURI The URI of the link, which was clicked by the user.
   * @return The result of the callback. May be null.
   */
  @Nullable
  EResult onClick(@NotNull URI pURI);

  /**
   * Defines the result of a click callback. This simply represents the basic operations of the
   * History API.
   */
  enum EResult {
    PUSH,
    REPLACE
  }
}
