package com.portshift.scn;

import com.emarsys.escher.Escher;
import com.emarsys.escher.EscherException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class DashboardManager {

    static Logger LOGGER = Logger.getLogger(DashboardManager.class.getName());

    private static DashboardManager BOOTSTRAP_INSTANCE;

    private String info = "Initializes class for signing requests.";

    private static RequestSettings requestSettings;

    public DashboardManager(RequestSettings rs) {
        this.requestSettings = rs;
    }

    /**
     *
     * @return
     */
    public void run() {
        try {

            LOGGER.log(Level.INFO, requestSettings.toString());

            HttpClient client = HttpClientBuilder.create().build();
            HttpRequestBase request = new HttpGet(requestSettings.getUrl());
            request.addHeader("host", requestSettings.getUrl());
            request.addHeader("Content-Type", ContentType.APPLICATION_JSON.toString());

            request = signRequest(request);

            HttpResponse response = client.execute(request);

            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
            System.out.println(fetchResponse(response));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static HttpRequestBase signRequest(HttpRequestBase request) throws EscherException {
        RequestManager escherRequest = new RequestManager(request, "");

        Escher escher = new Escher("eu/suite/ems_request")
                .setAuthHeaderName("X-Ems-Auth")
                .setDateHeaderName("X-Ems-Date")
                .setAlgoPrefix("EMS");

        escher.signRequest(
                escherRequest,
                requestSettings.getAccessKeyID(),
                requestSettings.getSecret(),
                Arrays.asList("Content-Type", "X-Ems-Date", "host")
        );
        return escherRequest.getHttpRequest();
    }


    private static String fetchResponse(HttpResponse response) throws IOException {
        try (BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()))) {
            return rd.lines().collect(Collectors.joining("\n"));
        }
    }


    public static DashboardManager getInstance(){
        if (BOOTSTRAP_INSTANCE == null){
            /**
             *  Read environment variables
             */
            RequestSettings rc = new RequestSettings(
                    System.getenv("HOST_URL"),
                    System.getenv("HOST_PORT"),
                    System.getenv("ACCESS_KEY_ID"),
                    System.getenv("SECRET"));

            validateConfigSettings(rc);
            BOOTSTRAP_INSTANCE = new DashboardManager(rc);
        }
        return BOOTSTRAP_INSTANCE;
    }

    private static void validateConfigSettings(RequestSettings rc){
        if (System.getenv("HOST_URL") == null ){
            LOGGER.warning("HOST_URL is empty");
        }

        if (System.getenv("HOST_PORT") == null ){
            LOGGER.warning("HOST_PORT is empty");
        }

        if (System.getenv("ACCESS_KEY_ID") == null ){
            LOGGER.warning("ACCESS_KEY_ID is empty");
        }

        if (System.getenv("HOST_URL") == null ){
            LOGGER.warning("HOST_URL is empty");
        }

        if (System.getenv("SECRET") == null ){
            LOGGER.warning("SECRET is empty");
        }
    }
}



