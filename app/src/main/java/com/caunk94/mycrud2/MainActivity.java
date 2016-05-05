package com.caunk94.mycrud2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    private DBManager dm;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        displayData();
        context = this;

        ListView lv= (ListView)findViewById(R.id.listView);
        lv.setOnItemLongClickListener(itemLongClick);

        //style
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setLogo(R.mipmap.icandro_bat);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);//untuk menampilkan logo di home page

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayData();//untuk merefresh tampilan data yang ada di listview secara otomatis
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dm.close();
    }

    private void initial(){
        dm = new DBManager(this);
        dm.Open();
    }

    public void OnclickInsert(View v){

        Intent intent = new Intent(this, DataProcurement.class);
        intent.putExtra("STATE", 0);
        startActivity(intent);
    }

    private void displayData(){
        String []from = {
                DBHelper.COLUMN_ID,
                DBHelper.COLUMN_IDPAYMENT,
                DBHelper.COLUMN_TANGGALPAYMENT,
                DBHelper.COLUMN_SUPPLIER,
                DBHelper.COLUMN_TOTALBAYAR
        };
        int []to ={R.id.textId,
                R.id.textIdPaymnt,
                R.id.textTanggalPymn,
                R.id.textSuplier,
                R.id.textTotalB};
        Cursor c = dm.getListSiswaAsCursor();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_data, c, from, to);
        ListView listview = (ListView)findViewById(R.id.listView);
        listview.setAdapter(adapter);
    }

    private AdapterView.OnItemLongClickListener itemLongClick = new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int pos, long id) {
            final long _id = id;
            final Dialog dlg = new Dialog(context);
            dlg.setTitle("Aksi");
            dlg.setContentView(R.layout.optionlayout);
            Button buttonEdit = (Button)dlg.findViewById(R.id.buttonEdit);
            Button buttonDelete = (Button)dlg.findViewById(R.id.buttonHapus);

            android.view.View.OnClickListener editListener = new android.view.View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DataProcurement.class);
                    intent.putExtra("ID", _id);
                    intent.putExtra("STATE", 1);
                    dlg.hide();
                    startActivity(intent);
                }
            };
            buttonEdit.setOnClickListener(editListener);

            android.view.View.OnClickListener deleteListener = new android.view.View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder confirm = new AlertDialog.Builder(context);
                    confirm.setTitle("Konfirmasi Hapus Data");
                    confirm.setMessage("APakah Pan Di Hapus ?");
                    confirm.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dm.delete(_id);
                            displayData();
                        }
                    });
                    confirm.setNegativeButton("Ora", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.cancel();
                        }
                    });
                    dlg.hide();
                    confirm.show();
                }
            };

            buttonDelete.setOnClickListener(deleteListener);

            dlg.show();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
