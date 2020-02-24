package com.example.seodongmin.expediturerecord;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Iterator;

import static com.example.seodongmin.expediturerecord.init.inc_and_exp;
import static com.example.seodongmin.expediturerecord.init.mCurrentsite;
import static com.example.seodongmin.expediturerecord.init.mLongitude;
import static com.example.seodongmin.expediturerecord.init.mLatitude;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-05-05.
 */

public class incexp_amount extends AppCompatActivity {




    int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 8888;
    int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 7777;

    int RESULT_CALENDAR = 8888;
    int RESULT_INCOMEOK = 1111;
    int RESULT_EXPENDITUREOK = 2222;
    int statType;
    int year;
    int month;
    int day;
    String aaa;
    boolean isNeedRefresh = false;
    boolean isAnimationAllowed = false;
    boolean isAnotherAnimationAllowed = false;
    Thread thread_siteinfo;

    TextView std_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incexp_amount);

        thread_siteinfo = new Thread();
        final SharedPreferences sf_fastadd = getSharedPreferences("FastAdd", MODE_PRIVATE);
        final SharedPreferences.Editor sf_fastadd_editor = sf_fastadd.edit();
        Intent getintent = getIntent();
        statType = getintent.getIntExtra("statType", 0);
        year = getintent.getIntExtra("n_year", 0);
        month = getintent.getIntExtra("n_month", 0);
        day = getintent.getIntExtra("n_day" , 0);
        String s_year = String.valueOf(year);
        String s_month = String.valueOf(month);
        String s_day = String.valueOf(day);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        aaa = setDateAAA(cal);

        std_date = (TextView) findViewById(R.id.incexp_amount_lbl_date);
        std_date.setText(s_month + "월 " + s_day + "일 (" + aaa + ")");

        //빠른 등록
        Switch fastadd = (Switch) findViewById(R.id.incexp_amount_switch_fastadd);
        final TextView siteinfo = (TextView) findViewById(R.id.incexp_amount_lbl_siteinfo);
        fastadd.setChecked(sf_fastadd.getBoolean("isFastAdd", false));

        Switch.OnCheckedChangeListener fastadd_listener = new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //권한 획득
                    if (ContextCompat.checkSelfPermission(incexp_amount.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(incexp_amount.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)) {

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(incexp_amount.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    }

                    if (ContextCompat.checkSelfPermission(incexp_amount.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(incexp_amount.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION)) {

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(incexp_amount.this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    }

                    chkGpsService();
                    siteinfo.setVisibility(View.VISIBLE);
                    sf_fastadd_editor.putBoolean("isFastAdd", true);
                }
                else
                {
                    siteinfo.setVisibility(View.GONE);
                    sf_fastadd_editor.putBoolean("isFastAdd", false);
                }
                sf_fastadd_editor.commit();
            }
        };

        fastadd.setOnCheckedChangeListener(fastadd_listener);


        //빠른등록 도움말
        ImageView img_fastaddhelp = (ImageView) findViewById(R.id.incexp_amount_img_fastaaddhelp);
        ImageView.OnClickListener img_fastaddhelp_listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(incexp_amount.this, incexp_fastaddhelp.class);
                startActivity(intent);
            }
        };
        img_fastaddhelp.setOnClickListener(img_fastaddhelp_listener);


        //빠른등록 - 장소정보 있는지 검사(스레드 사용)

        final Handler mHandler = new Handler();
        thread_siteinfo = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {


                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(mCurrentsite.equals(""))
                            {
                                siteinfo.setBackgroundColor(Color.parseColor("#ff0000"));
                                siteinfo.setText("장소정보 없음");
                            }
                            else
                            {
                                siteinfo.setText("");
                            }
                        }
                    });

                    if(!mCurrentsite.equals("")) return;

                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException e) { return;}

                }


            }
        });
        thread_siteinfo.start();


        //금액 입력 EditText
        final EditText txt_amount = (EditText) findViewById(R.id.incexp_txt_amount);
        txt_amount.setSelection(txt_amount.length());
        txt_amount.setFocusableInTouchMode(true);
        txt_amount.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        /*txt_amount.addTextChangedListener
                (new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        long num = Long.parseLong(txt_amount.getText().toString());
                        String str = String.format("%,d", num);
                        txt_amount.setText(str);
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                }
        );*/

        //날짜 변경 버튼
        Button.OnClickListener modifydate_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAnotherAnimationAllowed = true;
                Intent intent = new Intent(incexp_amount.this, incexp_datepicker.class);
                intent.putExtra("now_year", year);
                intent.putExtra("now_month", month);
                intent.putExtra("now_day", day);
                intent.putExtra("now_aaa", aaa);
                startActivityForResult(intent, RESULT_CALENDAR);
            }
        };
        Button modifydate = (Button) findViewById(R.id.incexp_amount_btn_modifydate);
        modifydate.setOnClickListener(modifydate_listener);



        //수입 버튼
        Button.OnClickListener goinc_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAnimationAllowed = true;
                EditText txt_amount = (EditText) findViewById(R.id.incexp_txt_amount);

                if (txt_amount.getText().toString().length() == 0) {
                    Toast.makeText(incexp_amount.this, "금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        Long.parseLong(txt_amount.getText().toString());
                    } catch (java.lang.NumberFormatException e) {
                        Toast.makeText(incexp_amount.this, "금액 란에 정상적인 값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                if(!sf_fastadd.getBoolean("isFastAdd", false)) {


                    Intent intent = new Intent(incexp_amount.this, incexp_catchoose.class);
                    intent.putExtra("FlowType", 1);
                    intent.putExtra("now_year", year);
                    intent.putExtra("now_month", month);
                    intent.putExtra("now_day", day);
                    intent.putExtra("now_aaa", aaa);
                    intent.putExtra("now_amount", txt_amount.getText().toString());
                    startActivityForResult(intent, RESULT_INCOMEOK);
                }
                else
                {

                    //통계창에서 온 경우, 통계창을 refresh해주기 위한 인텐트 전달
                    SharedPreferences.Editor editor = inc_and_exp.edit();

                    /// addItem(int inex, int year, int month, int day, int waynum, int wherenum, int emonum, int amount,  String memo)
                    int pos = search_adapter.addItem(1,year,month,day,"","(분류없음)",-1,Long.parseLong(txt_amount.getText().toString()),1,mCurrentsite);

                    String concatenation;
                    concatenation = 1 + ":;:" +
                            year + ":;:" +
                            month + ":;:" +
                            day + ":;:" +
                            "" + ":;:" +
                            "(분류없음)" + ":;:" +
                            "-1" + ":;:" +
                            txt_amount.getText().toString() + ":;:" +
                            "1" + ":;:" +
                            mCurrentsite;

                    int i = pos;
                    String tempstr = "";
                    while(!inc_and_exp.getString("incexp_" + i, "").equals("")) // pos 이후로 한칸씩 밀기
                    {
                        tempstr = inc_and_exp.getString("incexp_" + (i+1), "");
                        editor.putString("incexp_" + (i+1), inc_and_exp.getString("incexp_" + i, ""));
                        i++;
                    }

                    editor.putString("incexp_" + pos, concatenation);
                    editor.commit();


                    thread_siteinfo.interrupt();
                    if(statType > 0)
                    {
                        Intent refresh_intent = new Intent(incexp_amount.this, stat_v2.class);
                        refresh_intent.putExtra("statRefresh", true);
                        setResult(RESULT_OK, refresh_intent);
                    }
                    Toast.makeText(incexp_amount.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        Button goinc = (Button) findViewById(R.id.incexp_amount_btn_income);
        goinc.setOnClickListener(goinc_listener);

        //지출 버튼
        Button.OnClickListener goexp_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAnimationAllowed = true;
                EditText txt_amount = (EditText) findViewById(R.id.incexp_txt_amount);

                if(txt_amount.getText().toString().length() == 0)
                {
                    Toast.makeText(incexp_amount.this, "금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    try
                    {
                        Long.parseLong(txt_amount.getText().toString());
                    } catch(java.lang.NumberFormatException e) {
                        Toast.makeText(incexp_amount.this, "금액 란에 정상적인 값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }



                if(!sf_fastadd.getBoolean("isFastAdd", false)) {
                    Intent intent = new Intent(incexp_amount.this, incexp_catchoose.class);
                    intent.putExtra("FlowType", 2);
                    intent.putExtra("now_year", year);
                    intent.putExtra("now_month", month);
                    intent.putExtra("now_day", day);
                    intent.putExtra("now_aaa", aaa);
                    intent.putExtra("now_amount", txt_amount.getText().toString());
                    startActivityForResult(intent, RESULT_EXPENDITUREOK);
                }
                else
                {



                    //통계창에서 온 경우, 통계창을 refresh해주기 위한 인텐트 전달
                    SharedPreferences.Editor editor = inc_and_exp.edit();


                    /// addItem(int inex, int year, int month, int day, int waynum, int wherenum, int emonum, int amount,  String memo)
                    int pos = search_adapter.addItem(2,year,month,day,"(분류없음)","(분류없음)",3,Long.parseLong(txt_amount.getText().toString()),1,mCurrentsite);

                    String concatenation;
                    concatenation = 2 + ":;:" +
                            year + ":;:" +
                            month + ":;:" +
                            day + ":;:" +
                            "(분류없음)" + ":;:" +
                            "(분류없음)" + ":;:" +
                            "3" + ":;:" +
                            txt_amount.getText().toString() + ":;:" +
                            "1" + ":;:" +
                            mCurrentsite;

                    int i = pos;
                    String tempstr = "";
                    while(!inc_and_exp.getString("incexp_" + i, "").equals("")) // pos 이후로 한칸씩 밀기
                    {
                        tempstr = inc_and_exp.getString("incexp_" + (i+1), "");
                        editor.putString("incexp_" + (i+1), inc_and_exp.getString("incexp_" + i, ""));
                        i++;
                    }

                    editor.putString("incexp_" + pos, concatenation);
                    editor.commit();

                    thread_siteinfo.interrupt();
                    if(statType > 0)
                    {
                        Intent refresh_intent = new Intent(incexp_amount.this, stat_v2.class);
                        refresh_intent.putExtra("statRefresh", true);
                        setResult(RESULT_OK, refresh_intent);
                    }
                    Toast.makeText(incexp_amount.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        Button goexp = (Button) findViewById(R.id.incexp_amount_btn_expenditure);
        goexp.setOnClickListener(goexp_listener);


        //뒤로가기 버튼
        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(incexp_amount.this, main.class);
                intent.putExtra("nokeypad", true);
                thread_siteinfo.interrupt();
                setResult(RESULT_OK, intent);
                finish();
            }
        };
        ImageButton back = (ImageButton) findViewById(R.id.incexp_amount_back);
        back.setOnClickListener(back_listener);

    }

    @Override
    public void onStart()
    {
        super.onStart();
    }


    @Override
    public void onResume()
    {
        super.onResume();
        String s_year = String.valueOf(year);
        String s_month = String.valueOf(month);
        String s_day = String.valueOf(day);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        aaa = setDateAAA(cal);
        std_date.setText(s_month + "월 " + s_day + "일 (" + aaa + ")");

        System.out.println("수정된 날짜: " + year + "년 " + month + "월 " + day + "일 !!");
        final TextView siteinfo = (TextView) findViewById(R.id.incexp_amount_lbl_siteinfo);
        final Handler mHandler = new Handler();
        thread_siteinfo = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(mCurrentsite.equals(""))
                            {
                                siteinfo.setBackgroundColor(Color.parseColor("#ff0000"));
                                siteinfo.setText("장소정보 없음");
                            }
                            else
                            {
                                siteinfo.setText("");
                            }
                        }
                    });

                    if(!mCurrentsite.equals("")) return;

                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException e) { return;}
                }
            }
        });
        if(!thread_siteinfo.isAlive())
            thread_siteinfo.start();

        final EditText txt_amount = (EditText) findViewById(R.id.incexp_txt_amount);
        txt_amount.setSelection(txt_amount.length());
        txt_amount.setFocusableInTouchMode(true);
        txt_amount.requestFocus();
        if(isNeedRefresh) {
            Intent intent = new Intent(incexp_amount.this, nowsearching.class);
            startActivity(intent);
            isNeedRefresh = false;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }



    public void onPause()
    {
        super.onPause();
        if(isAnimationAllowed)
        {
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            isAnimationAllowed = false;
        }
        if(isAnotherAnimationAllowed)
        {
            overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
            isAnotherAnimationAllowed = false;
        }

        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        thread_siteinfo.interrupt();
        isNeedRefresh = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_CALENDAR) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                long longdate = data.getLongExtra("ModifiedDate", 0);
                android.text.format.Time t = new android.text.format.Time();
                t.set(longdate);
                year = t.year;
                month = t.month + 1;
                day= t.monthDay;

            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }


        if(requestCode == RESULT_INCOMEOK) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                System.out.println("여기 안와?");
                Intent returnToStat = new Intent(incexp_amount.this, stat_v2.class);
                returnToStat.putExtra("statRefresh", true);
                setResult(RESULT_OK, returnToStat);
                finish();
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }


        if(requestCode == RESULT_EXPENDITUREOK) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {

                System.out.println("여기 안와?");
                Intent returnToStat = new Intent(incexp_amount.this, stat_v2.class);
                returnToStat.putExtra("statRefresh", true);
                setResult(RESULT_OK, returnToStat);

                finish();
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }

    }

    public String setDateAAA(Calendar cal)
    {
        int aaa = cal.get(Calendar.DAY_OF_WEEK);
        switch(aaa)
        {
            case 1:
                return "일";
            case 2:
                return "월";
            case 3:
                return "화";
            case 4:
                return "수";
            case 5:
                return "목";
            case 6:
                return "금";
            case 7:
                return "토";
            default:
                return "-";
        }
    }


    private boolean chkGpsService() {

        String gps = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        Log.d(gps, "aaaa");

        if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {

            // GPS OFF 일때 Dialog 표시
            AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
            gsDialog.setTitle("위치 서비스 설정");
            gsDialog.setMessage("빠른 등록 기능을 이용하기 위해서는 위치 서비스를 활성화하여야합니다. \n위치 서비스 기능을 설정하시겠습니까?");
            gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // GPS설정 화면으로 이동
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    }).create().show();
            return false;

        } else {
            return true;
        }
    }
}
