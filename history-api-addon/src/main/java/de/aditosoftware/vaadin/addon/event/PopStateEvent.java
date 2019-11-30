package de.aditosoftware.vaadin.addon.event;

import de.aditosoftware.vaadin.addon.HistoryAPI;
import java.util.EventObject;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an PopState event.
 */
public class PopStateEvent extends EventObject {

  private String uri;
  private String state;
  private Map<String, String> stateMap;

  public PopStateEvent(HistoryAPI pSource, String uri, String state,
      Map<String, String> pStateMap) {
    super(pSource);
    this.uri = uri;
    this.state = state;
  }

  /**
   * Will return the current URI.
   *
   * @return The current URI.
   */
  @NotNull
  public String getUri() {
    return uri;
  }

  /**
   * Will return the state of the current history as string.
   *
   * @return The state of the history.
   */
  @Nullable
  public String getState() {
    return state;
  }

  /**
   * Will return the state of the current history decoded into an string/string map. This method can
   * be null even if {@link this#getState()} is not null.
   *
   * @return The state of the history decoded as string/string map.
   */
  @Nullable
  public Map<String, String> getStateAsMap() {
    return stateMap;
  }
}
