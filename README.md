# History API Add-on for Vaadin 8

This add-on provides the full [History API](https://html.spec.whatwg.org/multipage/history.html#the-history-interface) on the server-side, including  a [PopState event](https://html.spec.whatwg.org/multipage/browsing-the-web.html#the-popstateevent-interface) listener.
This exists because the [Vaadin implementation](https://github.com/vaadin/framework/blob/master/server/src/main/java/com/vaadin/server/Page.java#L365) of the PopState listener provides insufficient information.    

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to https://vaadin.com/addon/history-api

## Building and running demo

git clone https://github.com/aditosoftware/vaadin-history-api
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/


## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

# Developer Guide

## Getting started

Here is a simple example on how to try out the add-on component:

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
