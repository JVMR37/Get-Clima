package com.jvmr.getclima.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jvmr.getclima.R;
import com.jvmr.getclima.model.UsuarioModel;
import com.jvmr.getclima.service.UsuarioService;

import java.util.List;

public class CostumeArrayAdapter  extends ArrayAdapter {
    private CostumeArrayAdapter adpt;
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
            vh.btnRemove = (ImageButton) convertView.findViewById(R.id.imgRemove);

            convertView.setTag(vh);
        }

        else{
            vh = (ViewHolder)convertView.getTag();
        }

        vh.cidade.setText(cidades.get(position));
        vh.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CidadesFragment.removeCidade(adpt, cidades.get(position), cidades);
            }
        });
        return convertView;
    }

    static class ViewHolder{
        TextView cidade;
        ImageButton btnRemove;
    }

    public void setAdpt(CostumeArrayAdapter apdt){
        this.adpt = apdt;
    }

}