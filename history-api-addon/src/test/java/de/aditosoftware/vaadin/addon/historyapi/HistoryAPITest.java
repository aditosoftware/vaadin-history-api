package de.aditosoftware.vaadin.addon.historyapi;

import com.github.mvysny.kaributesting.v8.MockVaadin;
import com.vaadin.ui.UI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HistoryAPITest {
  @BeforeEach
  public void beforeEach() {
    MockVaadin.setup(TestUI::new);
  }

  @AfterEach
  public void afterEach() {
    MockVaadin.tearDown();
  }

  @Test
  void shouldNotInstantiateMultipleHistoryAPIInstances() {
    HistoryAPI historyAPIOne = HistoryAPI.forUI(UI.getCurrent());

    HistoryAPI historyAPITwo = HistoryAPI.forUI(UI.getCurrent());

    // Assert same to check reference equality.
    Assertions.assertSame(historyAPIOne, historyAPITwo);
  }
}
