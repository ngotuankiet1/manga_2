package com.example.ungdungdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ManNoiDung extends AppCompatActivity {

    TextView txtTenTruyen,txtNoiDung;
    Button btnYeuThich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_noi_dung);

        txtNoiDung = findViewById(R.id.noidung);
        txtTenTruyen = findViewById(R.id.TenTruyen);
        btnYeuThich = findViewById(R.id.buttonYeuthich);

        btnYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManNoiDung.this,"Đã thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
                Log.e("Yêu thích: ","Đã thêm vào danh sách yêu thích");
            }
        });

        Intent intent = getIntent();
        String tentruyen = intent.getStringExtra("tentruyen");
        String noidung = intent.getStringExtra("noidung");

        txtTenTruyen.setText(tentruyen);
        txtNoiDung.setText(noidung);

        //Cho phép cuộn nôi dụng truyện
        txtNoiDung.setMovementMethod(new ScrollingMovementMethod());

    }
}