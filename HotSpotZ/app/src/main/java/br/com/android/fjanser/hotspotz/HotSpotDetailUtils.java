package br.com.android.fjanser.hotspotz;

import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;

import br.com.android.fjanser.hotspotz.database.HotSpotContract;
import br.com.android.fjanser.hotspotz.database.HotSpotProvider;
import br.com.android.fjanser.hotspotz.model.HotSpot;
import br.com.android.fjanser.hotspotz.R;

public class HotSpotDetailUtils {

    public static boolean isFavorite(Context ctx, String endereco){
        Cursor cursor = ctx.getContentResolver().query(
                HotSpotProvider.HOTSPOT_URI,
                new String[]{ HotSpotContract._ID },
                HotSpotContract.COL_ENDERECO +" = ?",
                new String[]{ endereco },
                null
        );
        boolean isFavorite = false;
        if (cursor != null) {
            isFavorite = cursor.getCount() > 0;
            cursor.close();
        }
        return isFavorite;
    }

    public static void toggleFavorite(Context ctx, FloatingActionButton fab, String hotSpot){
        if (HotSpotDetailUtils.isFavorite(ctx, hotSpot)){
            fab.setImageResource(R.drawable.ic_favorite);
        } else {
            fab.setImageResource(R.drawable.ic_unfavorite);
        }
    }

    public static HotSpot hotSpotItemFromCursor(Cursor cursor){
        HotSpot hotSpot = new HotSpot();
        hotSpot.setId(cursor.getLong(cursor.getColumnIndex(HotSpotContract._ID)));
        hotSpot.setEndereco(cursor.getString(cursor.getColumnIndex(HotSpotContract.COL_ENDERECO)));
        hotSpot.setPais(cursor.getString(cursor.getColumnIndex(HotSpotContract.COL_PAIS)));
        hotSpot.setLatitude(cursor.getFloat(cursor.getColumnIndex(HotSpotContract.COL_LATITUDE)));
        hotSpot.setLongitude(cursor.getFloat(cursor.getColumnIndex(HotSpotContract.COL_LONGITUDE)));
        return hotSpot;
    }
}
