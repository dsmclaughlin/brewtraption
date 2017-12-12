package org.brewtraption.util;

import org.brewtraption.control.OverrideState;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BrewProps {

  private static String filePath = "";

  private static Properties properties = new Properties();

  private static final Map<String, Object> DEFAULT_VALUES;

  static {
    final Map<String, Object> defaults = new HashMap<>();
    defaults.put(Constants.HOST, "localhost");
    defaults.put(Constants.PORT, 8080);
    defaults.put(Constants.HTL_OFF_DELTA, 1.0);

    DEFAULT_VALUES = Collections.unmodifiableMap(defaults);
  }

  public static String lookupString(final String key) {
    final Object defaultValue = DEFAULT_VALUES.get(key);

    // for strings we allow empty default

    Object found = getProperty(key, defaultValue);
    // NPE protection
    if (found == null) {
      found = "";
    }
    return String.valueOf(found);
  }

  //TODO hacky this whole class actually needs a good refactor
  public static OverrideState lookUpOverrideState(final String key) {
    String state = lookupString(key);
    return OverrideState.valueOf(state);
  }

  public static int lookupInt(final String key) {
    final Object defaultValue = DEFAULT_VALUES.get(key);

    //TODO this null "waver" doesn't seem right, surely we should default after failing to find a value
    if (!(defaultValue instanceof Integer) && defaultValue != null) {
      final String message = String.format("Default value for configuration key %s not defined or has a wrong type", key);
      throw new RuntimeException(message);
    }

    final Object found = getProperty(key, defaultValue);

    if (found instanceof Number) {
      return ((Number) found).intValue();
    } else {
      final String message = String.format("Value for configuration key %s is not a number (%s), using default.", key,
        String.valueOf(found));
      return (Integer) defaultValue;
    }
  }

  public static long lookupLong(final String key) {
    final Object defaultValue = DEFAULT_VALUES.get(key);

    //TODO this null "waver" doesn't seem right, surely we should default after failing to find a value
    if (!(defaultValue instanceof Long) && defaultValue != null) {
      final String message = String.format("Default value for configuration key %s not defined or has a wrong type", key);
      throw new RuntimeException(message);
    }

    final Object found = getProperty(key, defaultValue);

    if (found instanceof Long) {
      return ((Number) found).longValue();
    } else {
      final String message = String.format("Value for configuration key %s is not a number (%s), using default.", key,
        String.valueOf(found));
      return (Long) defaultValue;
    }
  }

  public static Double lookupDouble(final String key) {
    final Object defaultValue = DEFAULT_VALUES.get(key);

    //TODO this null "waver" doesn't seem right, surely we should default after failing to find a value
    if (!(defaultValue instanceof Double) && defaultValue != null) {
      final String message = String.format("Default value for configuration key %s not defined or has a wrong type", key);
      throw new RuntimeException(message);
    }

    final Object found = getProperty(key, defaultValue);

    if (found instanceof Number) {
      return ((Number) found).doubleValue();
    } else {
      final String message = String.format("Value for configuration key %s is not a number (%s), using default.", key,
        String.valueOf(found));
      return (Double) defaultValue;
    }
  }

  public static Long lookupLongWithoutUsingDefault(final String key) {
    final String found = properties.getProperty(key);
    if (found == null) {
      return null;
    }

    try {
      return Long.valueOf(found);
    } catch (NumberFormatException formatException) {
      final String message = String.format("Value for configuration key %s is not a number (%s).", key, found);
      return null;
    }
  }

  public static boolean lookupBoolean(final String key) {
    final Object defaultValue = DEFAULT_VALUES.get(key);

    //TODO this null "waver" doesn't seem right, surely we should default after failing to find a value
    if (!(defaultValue instanceof Boolean) && defaultValue != null) {
      final String message = String.format("Default value for configuration key %s not defined or has a wrong type", key);
      throw new RuntimeException(message);
    }

    final Object found = getProperty(key, defaultValue);
    if (found instanceof Boolean) {
      return (Boolean) found;
    } else {
      final String message = String
        .format("Value for configuration key %s is not boolean (%s), using default.", key, String.valueOf(found));
      return (Boolean) defaultValue;
    }
  }

  private static Object getProperty(final String key, final Object defaultValue) {
    Object returnValue;
    final String found = properties.getProperty(key);
    if (found == null) {
      return defaultValue;
    }
    // Try and parse as a boolean first
    if (found.equalsIgnoreCase("true") || found.equalsIgnoreCase("false")) {
      returnValue = Boolean.parseBoolean(found);
    } else {
      // Not a boolean, try to parse as a long, integer or double if it doesn't start with '0'
      if (found.startsWith("0")) {
        returnValue = found;
      } else {
        try {
          if (found.toUpperCase().endsWith("L")) {
            final String l = found.substring(0, found.length() - 1);
            returnValue = Long.valueOf(l);
          } else {
            returnValue = Integer.valueOf(found);
          }
        } catch (final NumberFormatException e1) {
          // Not an integer, try and parse as a double
          try {
            returnValue = Double.valueOf(found);
          } catch (final NumberFormatException e2) {
            // Nope, leave it as a string
            returnValue = found;
          }
        }
      }
    }
    return returnValue;
  }

  public static void initialize(final String propertyFilePath) throws IOException {
    properties = new Properties();
    try (FileInputStream fileInputStream = new FileInputStream(propertyFilePath)) {
      properties.load(fileInputStream);
      filePath = propertyFilePath;
    }
  }

  @Deprecated
  public static void setValue(final String key, final String value) {
    properties.setProperty(key, value.trim());
  }

  public static void writeValue(final String key, final String value) {
    setValue(key, value);
    writeFileQuietly();
  }

  public static void removeValueAndWriteFile(final String key) {
    removeValue(key);
    writeFileQuietly();
  }

  public static void removeValue(final String key) {
    properties.remove(key);
  }

  public static void writeFileQuietly() {
    try {
      writeFile();
    } catch (IOException exception) {
      System.out.println("some problem writing properties file" +
        exception.getClass().toString());
    }
  }

  public static void writeFile() throws IOException {
    if ((filePath != null) && !filePath.isEmpty()) {
      try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
        properties.store(fileOutputStream, "UI Properties");
      }
    }
  }
}
