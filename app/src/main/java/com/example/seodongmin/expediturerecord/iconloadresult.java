package com.example.seodongmin.expediturerecord;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.R.attr.data;
import static com.example.seodongmin.expediturerecord.init.adapter_iconlist;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class iconloadresult extends AppCompatActivity {



    private ImageView img;
    final int CAMERA = 1;
    final int GALLERY = 2;
    final int REQ_CODE_SELECT_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iconloadresult);

        img = (ImageView) findViewById(R.id.iconloadresult_img);
        final Intent getintent = getIntent();

        Button.OnClickListener again_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switch(getintent.getIntExtra("RunType", 0))
                {
                    case 1:

                        if (checkSelfPermission(Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {

                            requestPermissions(new String[]{Manifest.permission.CAMERA}, 5);
                        }

                        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent1.putExtra(MediaStore.EXTRA_OUTPUT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());



                        // 이미지 잘라내기 위한 크기
                        intent1.putExtra("crop", "true");
                        intent1.putExtra("aspectX", 0);
                        intent1.putExtra("aspectY", 0);
                        intent1.putExtra("outputX", 90);
                        intent1.putExtra("outputY", 90);

                        try {
                            intent1.putExtra("return-data", true);
                            startActivityForResult(intent1, CAMERA);
                        } catch (ActivityNotFoundException e) {
                            // Do nothing for now
                        }

                        break;
                    case 2:

                        Intent intent2 = new Intent();
                        // Gallery 호출
                        intent2.setType("image/*");
                        intent2.setAction(Intent.ACTION_GET_CONTENT);
                        // 잘라내기 셋팅
                        intent2.putExtra("crop", "true");
                        intent2.putExtra("aspectX", 0);
                        intent2.putExtra("aspectY", 0);
                        intent2.putExtra("outputX", 90);
                        intent2.putExtra("outputY", 90);
                        try {
                            intent2.putExtra("return-data", true);
                            startActivityForResult(Intent.createChooser(intent2,
                                    "Complete action using"), GALLERY);
                        } catch (ActivityNotFoundException e) {
                            // Do nothing for now
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        Button again = (Button) findViewById(R.id.iconloadresult_btn_again);
        again.setOnClickListener(again_listener);


        Button.OnClickListener register_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Drawable d = img.getDrawable();
                adapter_iconlist.addItem(d);
                finish();
            }
        };
        Button register = (Button) findViewById(R.id.iconloadresult_btn_ok);
        register.setOnClickListener(register_listener);

        Button.OnClickListener cancel_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };
        Button cancel = (Button) findViewById(R.id.iconloadresult_btn_cancel);
        cancel.setOnClickListener(cancel_listener);

        switch(getintent.getIntExtra("RunType", 0))
        {
            case 1:

                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 5);
                }

                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());



                // 이미지 잘라내기 위한 크기
                intent1.putExtra("crop", "true");
                intent1.putExtra("aspectX", 0);
                intent1.putExtra("aspectY", 0);
                intent1.putExtra("outputX", 90);
                intent1.putExtra("outputY", 90);

                try {
                    intent1.putExtra("return-data", true);
                    startActivityForResult(intent1, CAMERA);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }

                break;
            case 2:

                Intent intent2 = new Intent();
                // Gallery 호출
                intent2.setType("image/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                // 잘라내기 셋팅
                intent2.putExtra("crop", "true");
                intent2.putExtra("aspectX", 0);
                intent2.putExtra("aspectY", 0);
                intent2.putExtra("outputX", 90);
                intent2.putExtra("outputY", 90);
                try {
                    intent2.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent2,
                            "Complete action using"), GALLERY);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
                break;
            default:
                break;
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA) {

            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    img.setImageBitmap(photo);
                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                finish();
            }
        }
        if (requestCode == GALLERY) {

            if(resultCode == RESULT_OK) {
                Bundle extras2 = data.getExtras();
                if (extras2 != null) {
                    Bitmap photo = extras2.getParcelable("data");
                    img.setImageBitmap(photo);
                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                finish();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 5:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    // 카메라 호출
                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent1.putExtra(MediaStore.EXTRA_OUTPUT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());

                    // 이미지 잘라내기 위한 크기
                    intent1.putExtra("crop", "true");
                    intent1.putExtra("aspectX", 0);
                    intent1.putExtra("aspectY", 0);
                    intent1.putExtra("outputX", 90);
                    intent1.putExtra("outputY", 90);

                    try {
                        intent1.putExtra("return-data", true);
                        startActivityForResult(intent1, CAMERA);
                    } catch (ActivityNotFoundException e) {
                        // Do nothing for now
                    }

                } else {

                    return;

                }
                return;
        }
    }


}
