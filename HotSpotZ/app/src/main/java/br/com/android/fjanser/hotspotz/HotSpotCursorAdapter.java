package br.com.android.fjanser.hotspotz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.android.fjanser.hotspotz.database.HotSpotContract;
import br.com.android.fjanser.hotspotz.R;
import br.com.android.fjanser.hotspotz.database.HotSpotDBHelper;

public class HotSpotCursorAdapter extends SimpleCursorAdapter {

    private static final int LAYOUT = R.layout.item_hotspot;
    private Context ctx;

    public HotSpotCursorAdapter(Context context, Cursor c) {
        super(context, LAYOUT, c, HotSpotContract.LIST_COLUMNS, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(LAYOUT, parent, false);

        VH vh = new VH();
        //vh.imageViewPoster = (ImageView) view.findViewById(R.id.movie_item_image_poster);
        vh.textViewEndereco = (TextView) view.findViewById(R.id.hotspot_item_text_title);
        vh.textViewPais = (TextView) view.findViewById(R.id.hotspot_item_text);
        view.setTag(vh);

        //ViewCompat.setTransitionName(vh.imageViewPoster, "capa");
        ViewCompat.setTransitionName(vh.textViewEndereco, "endereco");
        ViewCompat.setTransitionName(vh.textViewPais, "pais");

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        float totalEquipe = 0.0f;
        //String poster = cursor.getString(cursor.getColumnIndex(MovieContract.COL_POSTER));
        String endereco = cursor.getString(cursor.getColumnIndex(HotSpotContract.COL_ENDERECO));
        String pais = cursor.getString(cursor.getColumnIndex(HotSpotContract.COL_PAIS));

        VH vh = (VH)view.getTag();

        //Glide.with(context).load(poster).placeholder(R.drawable.ic_placeholder).into(vh.imageViewPoster);
        vh.textViewEndereco.setText(endereco);
        vh.textViewPais.setText(pais);
    }

    class VH {
        //ImageView imageViewPoster;
        TextView textViewEndereco;
        TextView textViewPais;
    }
}
