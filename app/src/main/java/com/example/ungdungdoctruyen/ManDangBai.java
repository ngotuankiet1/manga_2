package com.example.ungdungdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ungdungdoctruyen.database.database;
import com.example.ungdungdoctruyen.model.Truyen;

public class ManDangBai extends AppCompatActivity {

    EditText edtTenTruyen,edtNoiDung,edtAnh;
    Button btnDangBai;
    database databasetruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_bai);

        edtAnh = findViewById(R.id.dbimg);
        edtTenTruyen = findViewById(R.id.dbTenTruyen);
        edtNoiDung = findViewById(R.id.dbnoidung);
        btnDangBai = findViewById(R.id.dbdangbai);

        databasetruyen = new database(this);

        btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentruyen = edtTenTruyen.getText().toString();
                String noidung = edtNoiDung.getText().toString();
                String img = edtAnh.getText().toString();

                Truyen truyen = CreatTruyen();

                if(tentruyen.equals("")||noidung.equals("")||img.equals("")){
                    Toast.makeText(ManDangBai.this,"Yêu cầu nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                    Log.e("ERR :","Nhập đầy đủ thông tin");
                }
                else{
                    databasetruyen.AddTruyen(truyen);

                    Intent intent = new Intent(ManDangBai.this,ManAdmin.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private Truyen CreatTruyen(){
        String tentruyen = edtTenTruyen.getText().toString();
        String noidung = edtNoiDung.getText().toString();
        String img = edtAnh.getText().toString();

        Intent intent = getIntent();

        int id = intent.getIntExtra("Id",0);

        Truyen truyen = new Truyen(tentruyen,noidung,img,id);
        return  truyen;
    }
}