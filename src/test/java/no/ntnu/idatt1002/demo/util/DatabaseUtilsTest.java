package no.ntnu.idatt1002.demo.util;

import no.ntnu.idatt1002.demo.model.util.DatabaseUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for testing the database utilities
 * @version 0.1.0
 * @author Snake727
 */

public class DatabaseUtilsTest {

      @Test
      public void testInitializeDatabase() {
        // Test that the initializeDatabase method returns a connection to the database
        // Call the initializeDatabase method
        // Check that the method returns a connection to the database

        assertNotNull(DatabaseUtils.initializeDatabase());
      }

      // Other test methods for database utilities can be added here
}
