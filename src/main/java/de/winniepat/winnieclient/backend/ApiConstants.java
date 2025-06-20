package de.winniepat.winnieclient.backend;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public interface ApiConstants {

    String SCHEME = "http";
    String HOST = "127.0.0.1";
    int PORT = 8765;
    String BASE_PATH = "v1";

    default URI buildApiUri(String path) {
        return URI.create("%s://%s:%s/%s/%s".formatted(
                SCHEME, HOST, PORT, BASE_PATH.replaceFirst("^/+", ""),
                path.replaceFirst("^/+", "")
        ));
    }

    default URL buildApiUrl(String path) throws MalformedURLException {
        return buildApiUri(path).toURL();
    }
}
