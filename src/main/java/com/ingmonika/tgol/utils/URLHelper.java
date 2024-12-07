package com.ingmonika.tgol.utils;

import javafx.application.HostServices;

public class URLHelper {
    private static HostServices hostServices;

    public static void setHostServices(HostServices hostServices) {
        URLHelper.hostServices = hostServices;
    }

    public static void openURL(String url) {
        if (hostServices != null) {
            hostServices.showDocument(url);
        } else {
            throw new IllegalStateException("HostServices has not been set.");
        }
    }
}
