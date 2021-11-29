package com.example.ungdungdoctruyen;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungdoctruyen.database.database;

public class ManDangNhap extends AppCompatActivity {

    EditText edtTaiKhoan,edtMatKhau;
    Button btnDangNhap,btnDangKi;
    database databasedoctruyen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_nhap);

        AnhXa();

        databasedoctruyen = new database(this);

        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManDangNhap.this,ManDangKi.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentaikhoang = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();

                Cursor cursor = databasedoctruyen.getData();

                while (cursor.moveToNext()){
                    String datatentaikhoang = cursor.getString(1);
                    String datamatkhau = cursor.getString(2);

                    if (datatentaikhoang.equals(tentaikhoang) && datamatkhau.equals(matkhau)){
                        int phanquyen = cursor.getInt(4);
                        int idd = cursor.getInt(0);
                        String email = cursor.getString(3);
                        String tentk = cursor.getString(1);

                        Intent intent = new Intent(ManDangNhap.this, MainActivity.class);

                        intent.putExtra("phanq",phanquyen);
                        intent.putExtra("idd",idd);
                        intent.putExtra("email",email);
                        intent.putExtra("tentaikhoan",tentk);

                        startActivity(intent);
                    }
                }
                cursor.moveToFirst();
                cursor.close();
            }
        });
    }

    private  void AnhXa(){
        edtMatKhau = findViewById(R.id.matkhau);
        edtTaiKhoan = findViewById(R.id.taikhoan);
        btnDangKi = findViewById(R.id.dangki);
        btnDangNhap = findViewById(R.id.dangnhap);
    }
}