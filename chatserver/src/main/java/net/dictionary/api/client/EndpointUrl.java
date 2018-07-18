package net.dictionary.api.client;

public enum EndpointUrl {

    TRANSLATE("/translate");

    String path;

    EndpointUrl(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String addPath(String additionalPath) {
        return path + additionalPath;
    }
}
