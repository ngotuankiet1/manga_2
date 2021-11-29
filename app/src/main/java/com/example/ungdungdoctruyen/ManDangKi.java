package com.example.ungdungdoctruyen;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungdoctruyen.database.database;
import com.example.ungdungdoctruyen.model.TaiKhoan;

public class ManDangKi extends AppCompatActivity {

    EditText edtDKTaiKhoan,edtDKMatKhau,edtDkEmail;
    Button btnDKDangKy,btnDKDangNhap;

    database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_ki);

        database = new database(this);

        AnhXa();

        btnDKDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoan = edtDKTaiKhoan.getText().toString();
                String matkhau = edtDKMatKhau.getText().toString();
                String email = edtDkEmail.getText().toString();

                TaiKhoan taiKhoan1 = CreatetaiKhoan();
                if(taiKhoan1.equals("")||matkhau.equals("")||email.equals("")){
                    Log.e("Thông báo : ","Chưa nhập đầy đủ thông tin");
                }else{
                    database.AddTaiKhoan(taiKhoan1);
                    Toast.makeText(ManDangKi.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDKDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private TaiKhoan CreatetaiKhoan(){
       String taikhoan = edtDKTaiKhoan.getText().toString();
       String matkhau = edtDKMatKhau.getText().toString();
       String email = edtDkEmail.getText().toString();
       int phanquyen = 1;

       TaiKhoan tk = new TaiKhoan(taikhoan,matkhau,email,phanquyen);

       return tk;
    }

    public void AnhXa(){
        edtDkEmail = findViewById(R.id.dkemail);
        edtDKMatKhau = findViewById(R.id.dkmatkhau);
        edtDKTaiKhoan = findViewById(R.id.dktaikhoan);
        btnDKDangKy = findViewById(R.id.dkdangky);
        btnDKDangNhap = findViewById(R.id.dkdangnhap);
    }
}