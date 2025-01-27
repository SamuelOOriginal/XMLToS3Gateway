package com.minio.core.common;

import org.springframework.stereotype.Component;

@Component
public final class LogGenerator {
  public String logMsg(final String fileName, final String message) {
    return "[LOG] " + messageBody(fileName, message);
  }

  public String errorMsg(final String fileName, final String message, final Throwable e) {
    var logMessage = "[ERRO] " + messageBody(fileName, message);

    logMessage = appendError(e, logMessage);

    return logMessage;
  }

  public String generalMessage(final String className, final String message) {
    return "[LOG] " + generalMessageBody(className, message);
  }

  public String generalErrorMessage(final String className, final String message, final Throwable e) {
    var logMessage = "[ERRO] " + generalMessageBody(className, message);

    logMessage = appendError(e, logMessage);

    return logMessage;
  }

  private String appendError(final Throwable e, String logMessage) {
    if (e != null) {
      logMessage += " | Exception Message: " + e.getMessage();
    }

    return logMessage;
  }

  private static String messageBody(final String fileName, final String message) {
    return " Key: " + fileName + " | Message: " + message;
  }

  private static String generalMessageBody(final String className, final String message) {
    return " Class: " + className + " | Message: " + message;
  }
}
