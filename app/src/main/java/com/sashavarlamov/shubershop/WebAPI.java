package com.sashavarlamov.shubershop;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sashaadmin on 6/20/15.
 */
public class WebAPI {
    private static final String href = "172.30.42.122:3000/api/v1/";
    private static String session = null;

    public JSONObject signinShopper(String em, String pw){
        String addr = href + "shoppers/signin";
        JSONObject pdat = new JSONObject();
        try {
            pdat.put("email", em);
            pdat.put("password", pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject resp = doPost(addr, pdat);
        return resp;
    }

    public JSONObject signinConsumer(String em, String pw) {
        String addr = href + "consumers/signin";
        JSONObject pdat = new JSONObject();
        try {
            pdat.put("email", em);
            pdat.put("password", pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject resp = doPost(addr, pdat);
        return resp;
    }

    public String viewConsumerInfo(String consId) {
        String addr = href + "consumers/" + consId;
        return "";
    }

    public String viewShopperInfo(String shopId) {
        String addr = href + "shoppers/" + shopId;
        return "";
    }

    public String createList() {
        String addr = href + "lists";
        return "";
    }

    public String viewList(String listId) {
        String addr = href + "lists/" + listId;
        return "";
    }

    public String addToList(String listId) {
        String addr = href + "lists/" + listId;
        return "";
    }

    public String viewItem(String listId, String itemId) {
        String addr = href + "lists/" + listId + "/" + itemId;
        return "";
    }

    public String removeItem(String listId, String itemId){
        String addr = href + "lists/" + listId + "/" + itemId;
        return "";
    }

    public String updateItem(String listId, String itemId) {
        String addr = href + "lists/" + listId + "/" + itemId;
        return "";
    }

    public String openJob() {
        String addr = href + "jobs";
        return "";
    }

    public String cancelJob(String jobId) {
        String addr = href + "jobs/" + jobId + "/cancel";
        return "";
    }

    public String acceptJob(String jobId) {
        String addr = href + "jobs/" + jobId + "/accept";
        return "";
    }

    public String expenseJob(String jobId) {
        String addr = href + "jobs/" + jobId + "/expense";
        return "";
    }

    public String finishJob(String jobId) {
        String addr = href + "jobs/" + jobId + "/finish";
        return "";
    }

    public String payForJob(String jobId) {
        String addr = href + "jobs/" + jobId + "/pay";
        return "";
    }

    public String reviewJob(String jobId) {
        String addr = href + "jobs/" + jobId + "/review";
        return "";
    }

    public static JSONObject doPost(String uri, JSONObject json) {
        HttpURLConnection urlConnection;
        uri = appS(uri);
        String data = json.toString();
        String result = null;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(data);
            writer.close();
            outputStream.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jo = new JSONObject(result);
            return jo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject doGet(String uri, JSONObject json) {
        HttpURLConnection urlConnection;
        uri = appS(uri);
        String data = json.toString();
        String result = null;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(data);
            writer.close();
            outputStream.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jo = new JSONObject(result);
            return jo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String appS(String prev) {
        String withSess = prev + "?session=" + session;
        return withSess;
    }
}
