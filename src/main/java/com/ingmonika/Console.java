package com.ingmonika;

/**
 * I hate using System.out.print(), so I'll be using my own console to log things.
 * It's still Sout() at its core, but it's way easier to use.
 */
public class Console {
    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String INFO_COLOR = "\u001B[32m";  // Green
    private static final String WARNING_COLOR = "\u001B[33m";  // Yellow
    private static final String ERROR_COLOR = "\u001B[31m";  // Red
    private static final String DEBUG_COLOR = "\u001B[36m";  // Cyan

    // Enum for log types
    /**
     * Enum for log types.
     * LogType changes the color of the output and the name of the message before the actual log.
     */
    public enum LogType {
        /**
         * INFO represents general information messages that highlight the progress of the application.
         * These messages are typically used for providing status updates or confirmations that things are working as expected.
         */
        INFO,

        /**
         * WARNING indicates a potential issue that might not immediately affect the application's flow but warrants attention.
         * These messages are used to highlight something that is not right but is not severe enough to halt the application.
         */
        WARNING,

        /**
         * ERROR represents serious issues that have occurred, likely affecting the application's ability to function correctly.
         * These messages are used for reporting failures that need to be addressed to ensure the application continues to operate.
         */
        ERROR,

        /**
         * DEBUG is used for detailed information primarily intended for diagnosing issues and understanding the internal state of the application.
         * These messages are typically only enabled in a development or debugging environment and provide verbose logging of the application's actions.
         */
        DEBUG
    }

    private static String projectName = null;
    private static LogType defaultType = LogType.INFO;

    // Setter for project name
    /**
     * Sets the name of the project to be used in log messages.
     *
     * @param name the name of the project
     */
    public static void setProjectName(String name) {
        projectName = name;
    }

    // Setter for default log type
    /**
     * Sets the default LogType for logging.
     *
     * @param defaultType the default LogType to set
     */
    public static void setDefaultType(LogType defaultType) {
        Console.defaultType = defaultType;
    }

    // Log message with default log type
    /**
     * Logs a message to the console using the default LogType.
     *
     * @param message the message to log
     */
    public static void log(String message) {
        log(defaultType, message);
    }

    // Log message with specified log type
    /**
     * Logs a message to the console using the specified LogType.
     *
     * @param type the LogType to use
     * @param message the message to log
     */
    public static void log(LogType type, String message) {
        String prefix;
        String color;

        switch (type) {
            case WARNING:
                prefix = "[WARNING]";
                color = WARNING_COLOR;
                break;
            case ERROR:
                prefix = "[ERROR]";
                color = ERROR_COLOR;
                break;
            case DEBUG:
                prefix = "[DEBUG]";
                color = DEBUG_COLOR;
                break;
            case INFO:
            default:
                prefix = "[INFO]";
                color = INFO_COLOR;
                break;
        }

        if (projectName != null) {
            System.out.println(color + "[" + projectName + "] " + prefix + " " + message + RESET);
        } else {
            System.out.println(color + prefix + " " + message + RESET);
        }
    }
}
