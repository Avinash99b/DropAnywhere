package com.azstories.dropanywhere;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class sendpost {

    public String Response;
    public void posttext(String text) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder().add("text", text).build();

        Request request = new Request.Builder()
                .url("https://dropanywhere.azstories.com/default.php")
                .post(requestBody)
                .build();
        // Use the OkHttpClient instance to send the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle errors here
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Handle the response here
                String responseBody = response.body().string();
                Log.d("TAG", responseBody);
            }
        });
    }

    public String gettexxt(String text) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder().add("id", text).build();

        Request request = new Request.Builder()
                .url("https://dropanywhere.azstories.com/recieve.php")
                .post(requestBody)
                .build();

        final CountDownLatch latch = new CountDownLatch(1); // create the countdown latch

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                // Handle errors here
                latch.countDown(); // count down the latch if an error occurs
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Handle the response here
                String responseBody = response.body().string();
                Log.d("TAG", responseBody);

                Response = responseBody;
                latch.countDown(); // count down the latch to signal that the response has arrived
            }
        });

        try {
            latch.await(); // wait for the response to arrive
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Response;
    }

    public void postfileandtext(String text , File file) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder().add("text", text).build();

        Request request = new Request.Builder()
                .url("https://dropanywhere.azstories.com/default.php")
                .post(requestBody)
                .build();
        // Use the OkHttpClient instance to send the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle errors here
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Handle the response here
                String responseBody = response.body().string();
                Log.d("TAG", responseBody);
            }
        });
    }

    public void postfileandtext(File file) {
        OkHttpClient client = new OkHttpClient();

//        MediaType mediaType = MediaType.parse("application/octet-stream");

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file" , file.getName() , RequestBody.create(MediaType.parse("application/octet-stream") , file)).build();

        Request request = new Request.Builder()
                .url("https://dropanywhere.azstories.com/default.php")
                .post(requestBody)
                .build();
        // Use the OkHttpClient instance to send the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle errors here
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Handle the response here
                String responseBody = response.body().string();
                Log.d("TAG", responseBody);
            }
        });
    }

}
