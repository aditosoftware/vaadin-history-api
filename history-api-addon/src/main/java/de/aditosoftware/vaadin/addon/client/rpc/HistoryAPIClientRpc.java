package de.aditosoftware.vaadin.addon.client.rpc;

import com.vaadin.shared.communication.ClientRpc;
import de.aditosoftware.vaadin.addon.HistoryAPI;

/**
 * Describes the server-to-client communication.
 */
public interface HistoryAPIClientRpc extends ClientRpc {

  /**
   * @see de.aditosoftware.vaadin.addon.HistoryAPI#go(int)
   */
  void go(int pDelta);

  /**
   * @see HistoryAPI#back()
   */
  void back();

  /**
   * @see HistoryAPI#forward()
   */
  void forward();

  /**
   * @see HistoryAPI#pushState(String, String, String)
   */
  void pushState(String pState, String pTitle, String pURL);

  /**
   * @see HistoryAPI#replaceState(String, String, String)
   */
  void replaceState(String pState, String pTitle, String pURL);
}
