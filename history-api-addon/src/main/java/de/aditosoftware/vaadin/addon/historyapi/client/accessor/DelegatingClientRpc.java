package de.aditosoftware.vaadin.addon.historyapi.client.accessor;

import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryAPIClientRpc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an implementation of the client RPC which delegates the calls to the given native
 * accessor.
 */
public class DelegatingClientRpc implements HistoryAPIClientRpc {
  @Override
  public void go(int pDelta) {
    HistoryAPINativeAccessor.go(pDelta);
  }

  @Override
  public void back() {
    HistoryAPINativeAccessor.back();
  }

  @Override
  public void forward() {
    HistoryAPINativeAccessor.forward();
  }

  @Override
  public void pushState(@Nullable String pState, @Nullable String pTitle, @NotNull String pURL) {
    HistoryAPINativeAccessor.pushState(pState, pTitle, pURL);
  }

  @Override
  public void replaceState(@Nullable String pState, @Nullable String pTitle, @NotNull String pURL) {
    HistoryAPINativeAccessor.replaceState(pState, pTitle, pURL);
  }
}
