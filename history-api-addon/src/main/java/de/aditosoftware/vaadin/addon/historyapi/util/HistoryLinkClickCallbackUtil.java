package de.aditosoftware.vaadin.addon.historyapi.util;

import de.aditosoftware.vaadin.addon.historyapi.HistoryAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

public class HistoryLinkClickCallbackUtil {
  private HistoryLinkClickCallbackUtil() {}

  public static void executeCallback(
      @Nullable HistoryLinkClickCallback pCallback,
      @NotNull URI pURI,
      @NotNull HistoryAPI pHistory) {
    if (pCallback == null) return;

    HistoryLinkClickCallback.EResult result;
    try {
      result = pCallback.onClick(pURI);
    } catch (Exception pException) {
      return;
    }

    if (result == null) return;

    if (result == HistoryLinkClickCallback.EResult.PUSH) {
      pHistory.pushState(pURI.toString());
    } else if (result == HistoryLinkClickCallback.EResult.REPLACE) {
      pHistory.replaceState(pURI.toString());
    }
  }
}
