package com.example.ungdungdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ungdungdoctruyen.adapter.adapterTruyen;
import com.example.ungdungdoctruyen.database.database;
import com.example.ungdungdoctruyen.model.Truyen;

import java.util.ArrayList;

public class ManAdmin extends AppCompatActivity {


    ListView listView;
    Button buttonThem;

    ArrayList<Truyen> TruyenArrayList;
    adapterTruyen adapterTruyen;

    database databaseTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_admin);

        listView = findViewById(R.id.listviewAdmin);
        buttonThem = findViewById(R.id.buttonThemTruyen);

        initList();

        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = getIntent();
                int id = intent1.getIntExtra("Id",0);

                Intent intent = new Intent(ManAdmin.this,ManDangBai.class);
                intent.putExtra("Id",id);
                startActivity(intent);
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(ManAdmin.this,ManNoiDung.class);
//
//                String tent = TruyenArrayList.get(position).getTenTruyen();
//                String noidungt = TruyenArrayList.get(position).getNoiDung();
//                intent.putExtra("tentruyen",tent);
//                intent.putExtra("noidung",noidungt);
//                startActivity(intent);
//            }
//        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                DialogDelete(position);

                return false;
            }
        });

    }

    private void DialogDelete(int position){

        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialogdelete);

        dialog.setCanceledOnTouchOutside(false);

        Button btnYes = dialog.findViewById(R.id.buttonYes);
        Button btnNo = dialog.findViewById(R.id.buttonNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idtruyen = TruyenArrayList.get(position).getID();

                databaseTruyen.Delete(idtruyen);

                //cập nhật lai activyty
                Intent intent = new Intent(ManAdmin.this,ManAdmin.class);
                finish();
                startActivity(intent);
                Toast.makeText(ManAdmin.this,"Xóa truyện thành công",Toast.LENGTH_SHORT).show();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void initList() {
        TruyenArrayList = new ArrayList<>();

        databaseTruyen = new database(this);

        Cursor cursor1 = databaseTruyen.getData2();

        while (cursor1.moveToNext()){
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk));

            adapterTruyen = new adapterTruyen(getApplicationContext(),TruyenArrayList);

            listView.setAdapter(adapterTruyen);
        }
        cursor1.moveToFirst();
        cursor1.close();
    }
}