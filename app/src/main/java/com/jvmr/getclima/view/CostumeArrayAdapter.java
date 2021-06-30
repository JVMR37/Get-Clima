package com.jvmr.getclima.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jvmr.getclima.R;

import java.util.List;

public class CostumeArrayAdapter  extends ArrayAdapter {
    private List<String> cidades;
    private Context context;

    public CostumeArrayAdapter(@NonNull Context context, List<String> cidades) {
        super(context, R.layout.listview_item);
        this.cidades = cidades;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cidades.size();
    }

    @Override
    public Object getItem(int pos) {
        return cidades.get(pos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder vh = new ViewHolder();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

            vh.cidade = (TextView) convertView.findViewById(R.id.txtCidade);

            convertView.setTag(vh);
        }

        else{
            vh = (ViewHolder)convertView.getTag();
        }

        vh.cidade.setText(cidades.get(position));

        return convertView;
    }

    static class ViewHolder{
        TextView cidade;
        ImageView img_trash;
    }

    public static ImageView getIcon(ViewHolder vh, View view){
        return vh.img_trash = (ImageView) view.findViewById(R.id.imgRemove);
    }

}