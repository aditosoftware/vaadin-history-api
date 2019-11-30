package de.aditosoftware.vaadin.addon.event;

import java.util.Map;

/**
 * Represents an PopState event.
 */
public class PopStateEvent {
    private String uri;
    private String state;
    private Map<String, String> stateMap;

    public PopStateEvent(String uri, String state, Map<String, String> pStateMap) {
        this.uri = uri;
        this.state = state;
    }

    public String getUrl() {
        return uri;
    }

    public String getState() {
        return state;
    }

    public Map<String, String> getStateAsMap() {
        return stateMap;
    }
}
