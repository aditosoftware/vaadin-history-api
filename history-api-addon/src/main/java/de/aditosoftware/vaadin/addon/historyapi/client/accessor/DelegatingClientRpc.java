package de.aditosoftware.vaadin.addon.historyapi.client.accessor;

import de.aditosoftware.vaadin.addon.historyapi.client.rpc.HistoryAPIClientRpc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an implementation of the client RPC which delegates the calls to the given native
 * accessor.
 */
public class DelegatingClientRpc implements HistoryAPIClientRpc {

  private final transient HistoryAPINativeAccessor accessor;

  /**
   * Required zero-args constructor.
   */
  public DelegatingClientRpc() {
    accessor = null;
  }

  /**
   * Constructor which accepts the required {@link HistoryAPINativeAccessor}.
   *
   * @param pAccessor The native accessor.
   */
  public DelegatingClientRpc(@NotNull HistoryAPINativeAccessor pAccessor) {
    accessor = pAccessor;
  }

  @Override
  public void go(int pDelta) {
    if (accessor != null) {
      accessor.go(pDelta);
    }
  }

  @Override
  public void back() {
    if (accessor != null) {
      accessor.back();
    }
  }

  @Override
  public void forward() {
    if (accessor != null) {
      accessor.forward();
    }
  }

  @Override
  public void pushState(@Nullable String pState, @Nullable String pTitle, @NotNull String pURL) {
    if (accessor != null) {
      accessor.pushState(pState, pTitle, pURL);
    }
  }

  @Override
  public void replaceState(@Nullable String pState, @Nullable String pTitle, @NotNull String pURL) {
    if (accessor != null) {
      accessor.replaceState(pState, pTitle, pURL);
    }
  }
}
