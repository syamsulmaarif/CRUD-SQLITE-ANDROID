package com.caunk94.mycrud2;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by caunk94 on 5/7/2016.
 */
public class CostumAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    private DBHelper db = null;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CostumAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =    mInflater.inflate(R.layout.item_data, parent, false);
        ViewHolder holder  =   new ViewHolder();
        holder.txtId    =   (TextView)  view.findViewById(R.id.textId);
        holder.txtIdPayment    =   (TextView)  view.findViewById(R.id.textIdPaymnt);
        holder.txtTanggal   =   (TextView)  view.findViewById(R.id.textTanggalPymn);
        holder.txtSupplier    =   (TextView)  view.findViewById(R.id.textSuplier);
        holder.txtTotalBayar   =   (TextView)  view.findViewById(R.id.textTotalB);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //If you want to have zebra lines color effect uncomment below code
        if(cursor.getPosition()%2==1) {
            view.setBackgroundResource(R.drawable.item_list_backgroundcolor);
        } else {
            view.setBackgroundResource(R.drawable.item_list_backgroundcolor2);
        }

        ViewHolder holder  =   (ViewHolder)    view.getTag();
        holder.txtId.setText(cursor.getString(cursor.getColumnIndex(db.COLUMN_ID)));
        holder.txtIdPayment.setText(cursor.getString(cursor.getColumnIndex(db.COLUMN_IDPAYMENT)));
        holder.txtTanggal.setText(cursor.getString(cursor.getColumnIndex(db.COLUMN_TANGGALPAYMENT)));
        holder.txtSupplier.setText(cursor.getString(cursor.getColumnIndex(db.COLUMN_SUPPLIER)));
        holder.txtTotalBayar.setText(cursor.getString(cursor.getColumnIndex(db.COLUMN_TOTALBAYAR)));
    }

    static class ViewHolder {
        TextView txtId;
        TextView txtIdPayment;
        TextView txtTanggal;
        TextView txtSupplier;
        TextView txtTotalBayar;
    }
}
