package com.ysy.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * User: ysy
 * Date: 2015/8/18
 */
public class HttpUrlConnectionUtil {

    public static HttpURLConnection excute(Request request) throws AppException {
        switch (request.method) {
            case GET:
            case DELETE:
                return get(request);

            case POST:
            case PUT:
                return post(request);
        }
        return null;
    }

    private static HttpURLConnection get(Request request) throws AppException {
        try {
            HttpURLConnection connection = null;

            connection = (HttpURLConnection) new URL(request.url).openConnection();

            connection.setRequestMethod(request.method.name());
            connection.setConnectTimeout(15 * 3000);
            connection.setReadTimeout(15 * 3000);
            return connection;
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        }
    }

    private static HttpURLConnection post(Request request) throws AppException {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(request.url).openConnection();
            connection.setRequestMethod(request.method.name());
            connection.setConnectTimeout(15 * 3000);
            connection.setReadTimeout(15 * 3000);
            connection.setDoOutput(true);

            addHeader(connection, request.headers);
            return connection;
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        }

    }


    private static void addHeader(HttpURLConnection connection, Map<String, String> headers) {
        if (headers == null || headers.size() == 0) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }
}
