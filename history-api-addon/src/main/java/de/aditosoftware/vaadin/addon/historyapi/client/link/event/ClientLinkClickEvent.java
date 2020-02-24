package de.aditosoftware.vaadin.addon.historyapi.client.link.event;

import java.io.Serializable;

public class ClientLinkClickEvent implements Serializable {
  public String linkURI;

  public ClientLinkClickEvent () {
  }

  public ClientLinkClickEvent (String linkURI) {
    this.linkURI = linkURI;
  }

  public String getLinkURI () {
    return linkURI;
  }
}
