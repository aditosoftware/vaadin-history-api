# History API Add-on for Vaadin 8

This add-on provides the full [History API](https://html.spec.whatwg.org/multipage/history.html#the-history-interface) on the server-side, including  a [PopState event](https://html.spec.whatwg.org/multipage/browsing-the-web.html#the-popstateevent-interface) listener.
This exists because the [Vaadin implementation](https://github.com/vaadin/framework/blob/master/server/src/main/java/com/vaadin/server/Page.java#L365) of the PopState listener provides insufficient information.    

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to https://vaadin.com/addon/history-api


# Getting started
Here is a simple example on how to try out the add-on:

```java
// Register the HistoryAPI on your current UI.
HistoryAPI historyAPI = HistoryAPI.forUI(UI.getCurrent());

// Add a PopState listener.
historyAPI.addPopStateListener(event -> System.out.println(event.getUri().toString()));

// Push a new state, with URI "/push/1".
historyAPI.pushState("/push/1");

// Replace current state, with URI "/replace/1". 
historyAPI.replaceState("/replace/1");
```

For a more comprehensive example, see [DemoUI](src/main/java/de/aditosoftware/vaadin/addon/historyapi/demo/DemoUI.java)

# Building and running demo

git clone https://github.com/aditosoftware/vaadin-history-api
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/
