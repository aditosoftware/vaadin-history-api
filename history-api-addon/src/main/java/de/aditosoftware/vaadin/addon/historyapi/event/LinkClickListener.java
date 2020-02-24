package de.aditosoftware.vaadin.addon.historyapi.event;

import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

public interface LinkClickListener {
  Method METHOD = ReflectTools.findMethod(LinkClickListener.class, "linkClick", LinkClickEvent.class);

  void linkClick (LinkClickEvent linkClickEvent);
}
