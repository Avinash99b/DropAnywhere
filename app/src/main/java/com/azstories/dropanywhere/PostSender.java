package com.azstories.dropanywhere;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONArrayRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class PostSender extends AppCompatActivity {

    File classfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_sender);
        Button btn = findViewById(R.id.btn);
        EditText edt = findViewById(R.id.editTextTextMultiLine);
        Button filebtn = findViewById(R.id.file);
        Button sendall = findViewById(R.id.btn1);



        sendpost sendpost = new sendpost();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendpost.posttext(edt.getText().toString().replaceAll("\n", "<br>"));

            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    Uri uri = data.getData();
                   String path = RealPathUtil.getRealPath(getApplicationContext() , uri);

                   File file = new File(path);

                  classfile = file;

                }

            }
        });


        filebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {

                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            intent.setType("*/*");

                        }
                        activityResultLauncher.launch(intent);


                    }else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
                                && !Environment.isExternalStorageManager()) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, 1000);
                        }

                    }
                }
            }
        });

        sendall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(classfile!= null){
                    AndroidNetworking.initialize(getApplicationContext());
                    AndroidNetworking.upload("https://dropanywhere.azstories.com/appdefault.php").addMultipartFile("file" , classfile).build().getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
                        @Override
                        public void onResponse(Response response, String s) {
                            Log.e("res from me " ,  s);

                            TextView codeview = findViewById(R.id.codeview);

                            codeview.setText(s);


                        }

                        @Override
                        public void onError(ANError anError) {

                            Log.e("err" , anError.toString());

                        }
                    });
                }
            }
        });
    }

}