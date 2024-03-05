package no.ntnu.idatt1002.demo.util;

import no.ntnu.idatt1002.demo.model.repository.Database;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
      private Database database;

      @BeforeEach
      public void setUp() {
            database = new Database();
      }

      @Test
      public void testInitializeDatabase() {
            assertDoesNotThrow(() -> database.initializeDatabase());
      }
}