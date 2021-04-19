package de.aditosoftware.vaadin.addon.historyapi.client.renderer;

import com.vaadin.shared.ui.grid.renderers.AbstractRendererState;

public class HistoryLinkRendererState extends AbstractRendererState {
  /** Defines if any click shall open a new tab instead of pushing the link on this tab. */
  public boolean openNewTab = false;
}
