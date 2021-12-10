package com.example.ungdungdoctruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.ungdungdoctruyen.adapter.adapterTruyen;
import com.example.ungdungdoctruyen.adapter.adapterchuyenmuc;
import com.example.ungdungdoctruyen.adapter.adapterthongtin;
import com.example.ungdungdoctruyen.database.database;
import com.example.ungdungdoctruyen.model.TaiKhoan;
import com.example.ungdungdoctruyen.model.Truyen;
import com.example.ungdungdoctruyen.model.chuyenmuc;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//hahahaha
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView,listViewNew,listViewThongTin;
    DrawerLayout drawerLayout;

    String email;
    String tentaikhoan;

    ArrayList<Truyen> TruyenArrayList;

    adapterTruyen adapterTruyen;

    ArrayList<chuyenmuc> chuyenmucArrayList;

    ArrayList<TaiKhoan> taiKhoanArrayList;

    database databasetruyen;

    adapterchuyenmuc adapterchuyenmuc;
    adapterthongtin adapterthongtin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databasetruyen = new database(this);

        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq",0);
        int idd = intentpq.getIntExtra("idd",0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        AnhXa();
        ActionBar();
        ActionViewFlipper();

        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ManNoiDung.class);

                String tent = TruyenArrayList.get(position).getTenTruyen();
                String noidungt = TruyenArrayList.get(position).getNoiDung();
                intent.putExtra("tentruyen",tent);
                intent.putExtra("noidung",noidungt);
                startActivity(intent);
            }
        });

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    if(i == 2){
                        Intent intent = new Intent(MainActivity.this,ManAdmin.class);
                        intent.putExtra("Id",idd);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Bạn không có quyền đăng bài",Toast.LENGTH_SHORT).show();
                        Log.e("Đăng bài","bạn không có quyền");
                    }
                }
                else if(position == 1){
                    Intent intent = new Intent(MainActivity.this,ManThongTin.class);
                    startActivity(intent);
                }else if(position == 2){
                    Intent intent = new Intent(MainActivity.this,ManTatCaTruyen.class);
                    startActivity(intent);
                }else if(position == 3){
                    finish();
                }
            }
        });

    }

    private void ActionBar() {

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://toplist.vn/images/800px/rua-va-tho-230179.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/deo-chuong-cho-meo-230180.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/cu-cai-trang-230181.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/de-den-va-de-trang-230182.jpg");


        for (int i =0;i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());

            Picasso.get().load(mangquangcao.get(i)).into(imageView);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(4000);

        viewFlipper.setAutoStart(true);

        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        listViewNew = findViewById(R.id.listviewNew);
        listView = findViewById(R.id.listviewmanhinhchinh);
        listViewThongTin = findViewById(R.id.listviewthongtin);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);

        TruyenArrayList = new ArrayList<>();
        Cursor cursor1 = databasetruyen.getDatal();
        while (cursor1.moveToNext()){

            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk));

            adapterTruyen = new adapterTruyen(getApplicationContext(), TruyenArrayList);
            listViewNew.setAdapter(adapterTruyen);
        }
        cursor1.moveToFirst();
        cursor1.close();


        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan,email));

        adapterthongtin = new adapterthongtin(this,R.layout.navigation_thongtin,taiKhoanArrayList);
        listViewThongTin.setAdapter(adapterthongtin);

        chuyenmucArrayList = new ArrayList<>();
        chuyenmucArrayList.add(new chuyenmuc("Đăng bài",R.drawable.ic_baseline_post_add_24));
        chuyenmucArrayList.add(new chuyenmuc("Thông tin",R.drawable.ic_baseline_face_24));
        chuyenmucArrayList.add(new chuyenmuc("Tất cả truyện",R.drawable.ic_baseline_post_add_24));
        chuyenmucArrayList.add(new chuyenmuc("Đăng xuất",R.drawable.ic_baseline_login_24));

        adapterchuyenmuc = new adapterchuyenmuc(this,R.layout.chuyenmuc,chuyenmucArrayList);
        listView.setAdapter(adapterchuyenmuc);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
                Intent intent = new Intent(MainActivity.this,ManTimKiem.class);
                startActivity(intent);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}