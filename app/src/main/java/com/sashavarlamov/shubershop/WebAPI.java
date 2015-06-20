package com.sashavarlamov.shubershop;

import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sashaadmin on 6/20/15.
 */
public class WebAPI {
    private static final String href = "http://172.30.42.122:3000/api/v1/";
    public static String session = null;
    public static String firstName = null;
    public static String lastName = null;
    public static String mail = null;
    public static String email = null;
    public static boolean isConsumer = true;

    public JSONObject signinShopper(String em, String pw){
        String addr = href + "shoppers/signin";
        JSONObject pdat = new JSONObject();
        try {
            pdat.put("email", em);
            pdat.put("password", pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject resp = doPost(addr, pdat, false);
        System.out.println(resp.toString());
        try {
            if(resp != null && resp.has("session")) {
                session = resp.getString("session");
            } else {
                System.out.println("There wasn't a session in the response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        JSONObject resp = doPost(addr, pdat, true);
        System.out.println(resp.toString());
        try {
            if(resp != null && resp.has("session")) {
                session = resp.getString("session");
            } else {
                System.out.println("There wasn't a session in the response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public HashMap<String, String> indexLists() {
        String addr = href + "lists";
        JSONObject resp = doGet(addr, null);
        HashMap<String, String> dat = new HashMap<String, String>();
        try {
            JSONArray arr = resp.getJSONArray("lists");

            for(int i = 0; i < arr.length(); i++) {
                dat.put(new JSONObject(arr.get(i).toString()).getString("_id"), new JSONObject(arr.get(i).toString()).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dat;
    }

    public String viewConsumerInfo(String consId) {
        String addr = href + "consumers/" + consId;
        return "";
    }

    public String viewShopperInfo(String shopId) {
        String addr = href + "shoppers/" + shopId;
        return "";
    }

    public JSONObject createList(String n, String a) {
        String addr = href + "lists";
        JSONObject jo = new JSONObject();
        try {
            jo.put("name", n);
            jo.put("address", a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject ret = doPost(addr, jo);
        return ret;
    }

    public JSONObject viewList(String listId) {
        String addr = href + "lists/" + listId;
        JSONObject resp = doGet(addr, null);
        return resp;
    }

    public ArrayList<ShoppingItem> indexItems(String listId) {
        String addr = href + "lists/" + listId + "/items";
        JSONObject resp = doGet(addr, null);
        ArrayList<ShoppingItem> dat = new ArrayList<ShoppingItem>();
        try {
            JSONArray arr = resp.getJSONArray("items");

            for(int i = 0; i < arr.length(); i++) {
                ShoppingItem tempItem = new ShoppingItem();
                tempItem.id = new JSONObject(arr.get(i).toString()).getString("_id");
                tempItem.name = new JSONObject(arr.get(i).toString()).getString("name");
                tempItem.notes = new JSONObject(arr.get(i).toString()).getString("notes");
                dat.add(tempItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dat;
    }

    public JSONObject addToList(String listId, String itemName, String itemNotes) {
        String addr = href + "lists/" + listId + "/items";
        JSONObject jo = new JSONObject();
        try {
            jo.put("notes", itemNotes);
            jo.put("name", itemName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject ret = doPost(addr, jo);
        return ret;
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

    public JSONObject indexJobs(String listId) {
        String addr = href + "lists/" + listId + "/jobs";
        JSONObject ret = doGet(addr, null);
        return ret;
    }

    public JSONObject createJob(String listId) {
        String addr = href + "lists/" + listId + "/jobs";
        JSONObject ret = doPost(addr, new JSONObject());
        return ret;
    }

    public String cancelJob(String jobId) {
        String addr = href + "jobs/" + jobId + "/cancel";
        return "";
    }

    public String acceptJob(String jobId) {
        String addr = href + "jobs/" + jobId + "/accept";
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

    private JSONObject doPost(String uri, JSONObject json) {
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


    private JSONObject doPost(String uri, JSONObject json, boolean isConsumer) {
        HttpURLConnection urlConnection;
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
            JSONObject us = jo.getJSONObject("user");
            this.firstName = us.getString("firstName");
            this.lastName = us.getString("lastName");
            this.email = us.getString("email");
            this.mail = us.getString("mail");
            this.isConsumer = us.getBoolean("isConsumer");
            return jo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private JSONObject doGet(String uri, JSONObject json) {
        HttpURLConnection urlConnection;
        uri = appS(uri);
        String data = "";
        if(json != null) {
            data = json.toString();
        }
        String result = null;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
            urlConnection.setRequestMethod("GET");

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
            System.out.println(jo.toString());
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
