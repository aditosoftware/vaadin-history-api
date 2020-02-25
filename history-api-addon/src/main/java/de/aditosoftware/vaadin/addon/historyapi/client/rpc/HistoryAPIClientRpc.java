package de.aditosoftware.vaadin.addon.historyapi.client.rpc;

import com.vaadin.shared.communication.ClientRpc;
import de.aditosoftware.vaadin.addon.historyapi.HistoryAPI;

/**
 * Describes the server-to-client communication for the actual History API.
 */
public interface HistoryAPIClientRpc extends ClientRpc {

  /**
   * @see HistoryAPI#go(int)
   */
  void go (int pDelta);

  /**
   * @see HistoryAPI#back()
   */
  void back ();

  /**
   * @see HistoryAPI#forward()
   */
  void forward ();

  /**
   * @see HistoryAPI#pushState(String, String, String)
   */
  void pushState (String pState, String pTitle, String pURL);

  /**
   * @see HistoryAPI#replaceState(String, String, String)
   */
  void replaceState (String pState, String pTitle, String pURL);
}
