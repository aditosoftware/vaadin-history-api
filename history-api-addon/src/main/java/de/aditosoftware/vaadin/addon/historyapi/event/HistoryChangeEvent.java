package de.aditosoftware.vaadin.addon.historyapi.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.EventObject;
import java.util.Map;

/**
 * Represents the event which will be fired when the history changes. This will be used for PopState
 * events and Anchor clicks.
 */
public class HistoryChangeEvent extends EventObject {
  private URI uri;
  private String state;
  private Map<String, String> stateMap;
  private HistoryChangeOrigin origin;

  public HistoryChangeEvent(
      Object eventSource,
      URI uri,
      String state,
      Map<String, String> stateMap,
      HistoryChangeOrigin origin) {
    super(eventSource);
    this.uri = uri;
    this.state = state;
    this.stateMap = stateMap;
    this.origin = origin;
  }

  @NotNull
  public URI getURI() {
    return uri;
  }

  @Nullable
  public String getState() {
    return state;
  }

  @Nullable
  public Map<String, String> getStateMap() {
    return stateMap;
  }

  @NotNull
  public HistoryChangeOrigin getOrigin() {
    return origin;
  }
}
