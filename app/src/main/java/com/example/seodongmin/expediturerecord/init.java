package com.example.seodongmin.expediturerecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.*;
import android.content.*;
import android.util.Log;
import android.widget.*;
import android.app.Activity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class init extends AppCompatActivity {


    static double mLongitude;
    static double mLatitude;
    static String mCurrentsite = "";

    static ArrayList<String> income_category = new ArrayList<>();
    static ArrayList<String> expenditure_category = new ArrayList<>();

    static ArrayList<Drawable> income_category_icon = new ArrayList<>();
    static ArrayList<Drawable> expenditure_category_icon = new ArrayList<>();
    static ArrayList<Drawable> expenditure_way_icon = new ArrayList<>();

    static gridview_addicon_list_adapter adapter_iconlist = new gridview_addicon_list_adapter();


    static ArrayList<String> income_category_forsearch_1 = new ArrayList<>();
    static ArrayList<String> income_category_forsearch_2 = new ArrayList<>();
    static ArrayList<String> expenditure_category_forsearch_1 = new ArrayList<>();
    static ArrayList<String> expenditure_category_forsearch_2 = new ArrayList<>();

    static ArrayList<String> expenditure_way = new ArrayList<>();
    static ArrayList<String> expenditure_way_type = new ArrayList<>();
    static ArrayList<String> expenditure_way_forsearch_1 = new ArrayList<>();
    static ArrayList<String> expenditure_way_forsearch_2 = new ArrayList<>();

    static listview_category_list_adapter adapter_inc = new listview_category_list_adapter(1);
    static listview_category_list_adapter adapter_exc = new listview_category_list_adapter(2);
    static listview_category_list_adapter adapter_exw = new listview_category_list_adapter(3);

    //게임 추가
    static listview_calculateit_ranking_list_adapter adapter_calculateit20 = new listview_calculateit_ranking_list_adapter();
    static listview_calculateit_ranking_list_adapter adapter_calculateit100 = new listview_calculateit_ranking_list_adapter();
    static listview_calculateit_ranking_list_adapter adapter_fastmemorize = new listview_calculateit_ranking_list_adapter();
    static listview_calculateit_ranking_list_adapter adapter_color = new listview_calculateit_ranking_list_adapter();
    static listview_calculateit_ranking_list_adapter adapter_rocksipa = new listview_calculateit_ranking_list_adapter();
    static listview_calculateit_ranking_list_adapter adapter_countpeople = new listview_calculateit_ranking_list_adapter();
    static listview_avoidpoo_ranking_list_adapter adapter_avoidpoo = new listview_avoidpoo_ranking_list_adapter();


    static SharedPreferences inc_and_exp;

    static SharedPreferences inc_category;
    static SharedPreferences inc_category_icon;

    static SharedPreferences exc_category;
    static SharedPreferences exc_category_icon;

    static SharedPreferences exw_category;
    static SharedPreferences exw_category_icon;

    //게임 추가
    static SharedPreferences calculateit_20;
    static SharedPreferences calculateit_100;
    static SharedPreferences fastmemorize;
    static SharedPreferences color;
    static SharedPreferences rocksipa;
    static SharedPreferences countpeople;
    static SharedPreferences avoidpoo;

    static Initializer init;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        final ImageView mainimage = (ImageView) findViewById(R.id.init_img);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.init_pgbar);

        final Handler mHandler = new Handler();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                mHandler.post(new Runnable()
                {
                    public void run()
                    {
                        mainimage.setImageDrawable(ContextCompat.getDrawable(init.this, R.drawable.img_init));
                    }
                });
            }
        }).start();

    }



    public void onResume()
    {

        super.onResume();

        System.out.println("시작");

        init = new Initializer();


        final Handler mHandler = new Handler();

        //위치 정보를 받아오는 스레드를 실행한다(앱 실행시 계속 살아있음)
        new Thread(new Runnable() {
            @Override
            public void run() {

               /* while(true)
                {*/
                    try {

                        final LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

                        final LocationListener mLocationListener = new LocationListener() {
                            public void onLocationChanged(Location location) {
                                    //여기서 위치값이 갱신되면 이벤트가 발생한다.
                                    //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.
                                    Log.d("test", "onLocationChanged, location:" + location);
                                    mLongitude = location.getLongitude(); //경도
                                    mLatitude = location.getLatitude();   //위도
                                    double altitude = location.getAltitude();   //고도
                                    float accuracy = location.getAccuracy();    //정확도
                                    String provider = location.getProvider();   //위치제공자
                                    //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
                                    //Network 위치제공자에 의한 위치변화
                                    //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
                                    System.out.println("위치정보 : " + provider + "\n위도 : " + mLatitude + "\n경도 : " + mLongitude
                                            + "\n고도 : " + altitude + "\n정확도 : "  + accuracy);
                                }
                            public void onProviderDisabled(String provider) {
                                // Disabled시
                                Log.d("test", "onProviderDisabled, provider:" + provider);
                            }

                            public void onProviderEnabled(String provider) {
                                // Enabled시
                                Log.d("test", "onProviderEnabled, provider:" + provider);
                            }

                            public void onStatusChanged(String provider, int status, Bundle extras) {
                                // 변경시
                                Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
                            }
                        };

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try{

                                    // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
                                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                                            100, // 통지사이의 최소 시간간격 (miliSecond)
                                            1, // 통지사이의 최소 변경거리 (m)
                                            mLocationListener);
                                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                                            100, // 통지사이의 최소 시간간격 (miliSecond)
                                            1, // 통지사이의 최소 변경거리 (m)
                                            mLocationListener);

                                }catch(SecurityException ex){
                                }
                            }
                        });

                        Thread.sleep(3000);

                        System.out.println("mLatitude: " + mLatitude + ", mLongitude: " + mLongitude);
                        Geocoder _Geocoder = new Geocoder(init.this);
                        String _Result = "";
                        try {
                            Iterator<Address> _Addresses = _Geocoder.getFromLocation(mLatitude,
                                    mLongitude, 1).iterator();
                            if (_Addresses != null) {
                                while (_Addresses.hasNext()) {
                                    Address namedLoc = _Addresses.next();
                                    String city = namedLoc.getAdminArea();
                                    String placeName = namedLoc.getLocality();
                                    String featureName = namedLoc.getFeatureName();
                                    String country = namedLoc.getCountryName();
                                    String road = namedLoc.getThoroughfare();
                                    _Result += String.format("\n[%s][%s][%s][%s]", placeName,
                                            featureName, road, country);
                                    System.out.println("아무것도 출력되지 않아요???" + _Result);
                                    _Result = "";
                                    int addIdx = namedLoc.getMaxAddressLineIndex();
                                    for (int idx = 0; idx <= addIdx; idx++) {
                                        String addLine = namedLoc.getAddressLine(idx);
                                        _Result += String.format("%s", addLine);
                                    }

                                    mCurrentsite =  city + " " + placeName + " "  + road + " " + featureName;
                                }
                            }
                        } catch (IOException e) {
                        }

                        Thread.sleep(30000);    //30초마다 위치 정보를 갱신한다.
                        System.out.println("나는 건재하다");
                    } catch(InterruptedException e) { return; }
               /* }*/
            }
        }).start();


        //위치 정보를 장소명으로 바꾸어주는 스레드(앱 실행시 계속 살아있음)
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true)
                {
                    try
                    {

                        System.out.println("mLatitude: " + mLatitude + ", mLongitude: " + mLongitude);
                        Geocoder _Geocoder = new Geocoder(init.this);
                        String _Result = "";
                        try {
                            Iterator<Address> _Addresses = _Geocoder.getFromLocation(mLatitude,
                                    mLongitude, 1).iterator();
                            if (_Addresses != null) {
                                while (_Addresses.hasNext()) {
                                    Address namedLoc = _Addresses.next();
                                    String city = namedLoc.getAdminArea();
                                    String placeName = namedLoc.getLocality();
                                    String featureName = namedLoc.getFeatureName();
                                    String country = namedLoc.getCountryName();
                                    String road = namedLoc.getThoroughfare();
                                    _Result += String.format("\n[%s][%s][%s][%s]", placeName,
                                            featureName, road, country);
                                    System.out.println("아무것도 출력되지 않아요???" + _Result);
                                    _Result = "";
                                    int addIdx = namedLoc.getMaxAddressLineIndex();
                                    for (int idx = 0; idx <= addIdx; idx++) {
                                        String addLine = namedLoc.getAddressLine(idx);
                                        _Result += String.format("%s", addLine);
                                    }

                                    mCurrentsite =  city + " " + placeName + " "  + road + " " + featureName;
                                }
                            }
                        } catch (IOException e) {
                        }

                        Thread.sleep(10000);
                    }
                    catch(InterruptedException e) {}
                }


            }
        }).start();


        //설치 후 첫 실행시, 수입, 지출 내역 저장 SharedPreferences 를 생성한다
        final TextView tv = (TextView) findViewById(R.id.init_lbl_status);
        final TextView lbl_proverb = (TextView) findViewById(R.id.init_lbl_proverb);
        final TextView lbl_person = (TextView) findViewById(R.id.init_lbl_person);


        new Thread(new Runnable() {

            @Override
            public void run()
            {
                SharedPreferences chkinit  = getSharedPreferences("Init_OK", Activity.MODE_PRIVATE);
                boolean isinit = chkinit.getBoolean("isInit", false);
                if(!isinit) {

                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            tv.setText("설치 후 최초 실행 환경 설정 중입니다...");
                        }
                    });
                    inc_and_exp = getSharedPreferences("Income_and_Expenditure", MODE_PRIVATE);
                    inc_category = getSharedPreferences("Income_Category", MODE_PRIVATE);
                    exc_category = getSharedPreferences("Expenditure_Category", MODE_PRIVATE);
                    exw_category = getSharedPreferences("Expenditure_Way", MODE_PRIVATE);
                    inc_category_icon = getSharedPreferences("Income_Category_Icon", MODE_PRIVATE);
                    exc_category_icon = getSharedPreferences("Expenditure_Category_Icon", MODE_PRIVATE);
                    exw_category_icon = getSharedPreferences("Expenditure_Way_Icon", MODE_PRIVATE);

                    //게임 추가
                    calculateit_20 = getSharedPreferences("Calculateit_20", MODE_PRIVATE);
                    calculateit_100 = getSharedPreferences("Calculateit_100", MODE_PRIVATE);
                    fastmemorize = getSharedPreferences("Fastmemorize", MODE_PRIVATE);
                    color = getSharedPreferences("Color", MODE_PRIVATE);
                    rocksipa = getSharedPreferences("Rocksipa", MODE_PRIVATE);
                    countpeople = getSharedPreferences("Countpeople", MODE_PRIVATE);
                    avoidpoo = getSharedPreferences("Avoidpoo", MODE_PRIVATE);


                    init.init_categoryPref_Initializer();
                    init.init_categoryIconPref_Initializer(init.this);

                    SharedPreferences initok  = getSharedPreferences("Init_OK", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = initok.edit();
                    editor.putBoolean("isInit", true);
                    editor.commit();
                }

                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        tv.setText("데이터를 로드하고 있습니다...");
                    }
                });

                if(isinit)
                {

                    final String[] proverbs = new String[]
                            {
                                    "돈을 신 모시듯 하면 악마처럼 그대를 괴롭힐 것이다.",
                                    "돈을 빌리러 가는 것은 자유를 팔러 가는 것이다.",
                                    "돈이 돈을 낳는다. 돈이 돈을 번다",
                                    "돈은 최선의 종이요, 최악의 주인이다",
                                    "재산이 많은 사람이 그 재산을 자랑하고 있더라도, 그 돈을 어떻게 쓰는지 알 수 있을 때까지는 그를 칭찬하지 말라.",
                                    "개같이 벌어서 정승같이 써라",
                                    "만족할 줄 아는 사람은 진정한 부자이고, 탐욕스러운 사람은 진실로 가난한 사람이다.",
                                    "돈을 사랑치 말고, 있는 바를 족한 줄로 알라",
                                    "도박하는 사람들은 불확실한 것을 얻기 위해 확실한 것을 건다.",
                                    "부자가 되는 한 가지 방법이 있다. 내일 할 일을 오늘 하고 오늘 먹을 것을 내일 먹어라.",
                                    "정당한 소유는 인간을 자유롭게 하지만, 지나친 소유는 소유 자체가 주인이 되어 소유자를 노예로 만든다.",
                                    "적에게 돈을 꿔 주면 그를 이기게 되고, 친구에게 꿔 주면 그를 잃게 된다.",
                                    "수입을 생각하고 나서 지출 계획을 세우라.",
                                    "지갑이 가벼우면 마음이 무겁다.",
                                    "남의 돈에는 날카로운 이빨이 돋아 있다.",
                                    "달걀을 갖고 싶으면 암탉이 시끄럽게 우는 소리를 참아야 한다.",
                                    "악의 근원을 이루는 것은 돈 자체가 아니라, 돈에 대한 애착인 것이다.",
                                    "돈은 악이 아니며, 저주도 아니다. 돈은 사람을 축복하는 것이다.",
                                    "돈이 있어도 이상이 없는 사람은 몰락의 길을 밟는다.",
                                    "돈을 버는 데 그릇된 방법을 썼다면, 그 만큼 그 마음 속에는 상처가 나 있을 것이다.",
                                    "누구에게도 자금은 무한한 것이 아니다.",
                                    "손에 넣을 수 있는 것은 손에 넣어라. 손에 넣은 것은 계속 지니고 있어라. 이것이 납을 황금으로 만드는 \'현자의 돌\'이다.",
                                    "재산은 가지고 있는 자의 것이 아니고, 그것을 즐기는 자의 것이다.",
                                    "작은 비용을 삼가라. 작은 구멍이 큰 배를 가라앉힌다.",
                                    "두툼한 지갑이 좋다고는 말할 수 없다. 그러나 텅 빈 지갑은 더 나쁘다."
                            };

                    final String[] people = new String[]
                            {
                                    "헨리 필딩",
                                    "벤자민 프랭클린",
                                    "존 레이",
                                    "프랜시스 베이컨",
                                    "소크라테스",
                                    "한국 속담",
                                    "솔론",
                                    "히브리서 13장:5절",
                                    "파스칼",
                                    "유대 속담",
                                    "니체",
                                    "벤자민 프랭클린",
                                    "예기",
                                    "괴테",
                                    "러시아 속담",
                                    "덴마크 속담",
                                    "스마일즈",
                                    "탈무드",
                                    "도스도예프스키",
                                    "빌리 그레엄",
                                    "손자병법",
                                    "벤자민 프랭클린",
                                    "하우얼",
                                    "벤자민 프랭클린",
                                    "유태격언"
                            };


                    Random random = new Random(System.currentTimeMillis());
                    final int pick = random.nextInt(proverbs.length);
                    try
                    {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                lbl_proverb.setText(proverbs[pick]);
                                lbl_person.setText("- " + people[pick]);
                            }
                        });
                        Thread.sleep(3000);
                    } catch(InterruptedException e) {}
                }








                inc_and_exp = getSharedPreferences("Income_and_Expenditure", MODE_PRIVATE);
                inc_category = getSharedPreferences("Income_Category", MODE_PRIVATE);
                exc_category = getSharedPreferences("Expenditure_Category", MODE_PRIVATE);
                exw_category = getSharedPreferences("Expenditure_Way", MODE_PRIVATE);
                inc_category_icon = getSharedPreferences("Income_Category_Icon", MODE_PRIVATE);
                exc_category_icon = getSharedPreferences("Expenditure_Category_Icon", MODE_PRIVATE);
                exw_category_icon = getSharedPreferences("Expenditure_Way_Icon", MODE_PRIVATE);

                //게임 추가
                calculateit_20 = getSharedPreferences("Calculateit_20", MODE_PRIVATE);
                calculateit_100 = getSharedPreferences("Calculateit_100", MODE_PRIVATE);
                fastmemorize = getSharedPreferences("Fastmemorize", MODE_PRIVATE);
                color = getSharedPreferences("Color", MODE_PRIVATE);
                rocksipa = getSharedPreferences("Rocksipa", MODE_PRIVATE);
                countpeople = getSharedPreferences("Countpeople", MODE_PRIVATE);
                avoidpoo = getSharedPreferences("Avoidpoo", MODE_PRIVATE);

                //카테고리, 지출수단 스피너 초기화
                init.init_expenditure_category_1();
                init.init_expenditure_category_2();
                init.init_expenditure_category_3();
                init.init_income_category_1();
                init.init_income_category_2();
                init.init_income_category_3();
                init.init_expenditure_way();
                init.init_expenditure_way_forsearch_1();
                init.init_expenditure_way_forsearch_2();
                //init.init_expenditure_way_type();
                init.init_iconlist_adapter(init.this);
                init.init_expenditure_way();
                init.init_expenditure_category_icon(init.this);
                init.init_income_category_icon(init.this);
                init.init_expenditure_way_icon(init.this);





                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //게임 추가
                        init.init_categoty_adapter(init.this);
                        init.init_calculateit_20_adapter(init.this);
                        init.init_calculateit_100_adapter(init.this);
                        init.init_fastmemorize_adapter(init.this);
                        init.init_color_adapter(init.this);
                        init.init_rocksipa_adapter(init.this);
                        init.init_countpeople_adapter(init.this);
                        init.init_avoidpoo_adapter(init.this);
                        init.init_income_and_expenditure();


                        SharedPreferences sf = getSharedPreferences("File_Settings", MODE_PRIVATE);
                        Boolean is_pw = sf.getBoolean("isPassword", false);

                        if(is_pw)
                        {
                            Intent intent_true = new Intent(init.this, before_password.class);
                            startActivity(intent_true);
                            finish();
                        }
                        else {
                            Intent intent_else = new Intent(init.this, main.class);
                            startActivity(intent_else);
                            finish();
                        }
                    }
                });
            }
        }).start();



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

             /*   }*/
            }
        }, 0);

        System.out.println("종료");
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        FrameLayout fr = (FrameLayout) findViewById(R.id.main_fr_lock);
        fr.setVisibility(View.VISIBLE);
        finish();
    }*/
}
