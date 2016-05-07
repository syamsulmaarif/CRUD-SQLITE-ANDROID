package com.caunk94.mycrud2;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBManager dm;
    private Context context;
    Cursor cursor;
    CostumAdapter costumAdapter;
    private SimpleCursorAdapter adapter;
    //private CustomAdapter customAdapter;
    private final static String TAG= MainActivity.class.getName().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        //displayData();
        context = this;

        ListView lv= (ListView)findViewById(R.id.listView);
        lv.setOnItemLongClickListener(itemLongClick);
        //lv.setAdapter(costumAdapter);

        //style
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        my_toolbar.setTitle(R.string.my_title_main);
        my_toolbar.setSubtitle(R.string.my_subtitle_main);
        my_toolbar.setLogo(R.mipmap.icandro_bat);
        setSupportActionBar(my_toolbar);

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
        Cursor c= dm.getListSiswaAsCursor();
        adapter = new SimpleCursorAdapter(this, R.layout.item_data, c, from, to);
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);//untuk menampilkan toolbar, kalau tidak ada kode ini toolbar tidak akan terlihat

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.item_search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "onQueryTextSubmit ");
                    cursor=dm.getStudentListByKeyword(s);
                    if (cursor==null){
                        Toast.makeText(MainActivity.this,"No records found!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
                    }


                    adapter.setFilterQueryProvider(new FilterQueryProvider() {
                        public Cursor runQuery(CharSequence constraint) {
                            return dm.getStudentListByKeyword(constraint.toString());
                        }
                    });
                    adapter.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "onQueryTextChange ");
                    cursor=dm.getStudentListByKeyword(s);
                    if (cursor!=null){
                        adapter.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }

       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_setting:
                //startActivity(new Intent(this, SettingsActivity.class));
                Toast.makeText(this, R.string.item_setting, Toast.LENGTH_SHORT).show();
                break;

            case R.id.item_profile:
                //startActivity(new Intent(this, ProfileActivity.class));
                 Toast.makeText(this, R.string.item_profile, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
