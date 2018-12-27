package com.spectralfergus.practice.tarotapp.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            Scanner sc = new Scanner(httpURLConnection.getInputStream());
            sc.useDelimiter("\\A");

            return sc.hasNext() ? sc.next() : null;
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
