package com.portshift.scn;

import com.emarsys.escher.EscherRequest;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RequestManager implements EscherRequest {

    private HttpRequestBase httpRequest;
    private String body;


    public RequestManager(HttpRequestBase httpRequest, String body) {
        this.httpRequest = httpRequest;
        this.body = body;
    }


    @Override
    public String getHttpMethod() {
        return httpRequest.getMethod();
    }


    @Override
    public URI getURI() {
        return httpRequest.getURI();
    }


    @Override
    public List<Header> getRequestHeaders() {
        return Arrays.asList(httpRequest.getAllHeaders())
                .stream()
                .map(header -> new EscherRequest.Header(header.getName(), header.getValue()))
                .collect(Collectors.toList());
    }


    @Override
    public void addHeader(String fieldName, String fieldValue) {
        httpRequest.addHeader(fieldName, fieldValue);
    }


    @Override
    public String getBody() {
        return body;
    }


    public HttpRequestBase getHttpRequest() {
        return httpRequest;
    }
}


class RequestSettings {

    private String url;
    private String port;
    private String accessKeyID;
    private String secret;

    public RequestSettings(String url, String port, String accessKeyID, String secret) {
        this.url = url;
        this.port = port;
        this.accessKeyID = accessKeyID;
        this.secret = secret;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setAccessKeyID(String accessKeyID) {
        this.accessKeyID = accessKeyID;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUrl() {
        return url;
    }

    public String getPort() {
        return port;
    }

    public String getAccessKeyID() {
        return accessKeyID;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public String toString() {
        return "RequestSettings{" +
                "url='" + url + '\'' +
                ", port='" + port + '\'' +
                ", accessKeyID='" + accessKeyID + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}