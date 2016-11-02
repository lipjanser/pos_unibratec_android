package br.com.android.fjanser.hotspotz;

import android.view.View;
import br.com.android.fjanser.hotspotz.model.HotSpot;

public interface OnHotSpotClickListener {
    void onHotSpotClick(View view, HotSpot hotSpot, int position);
}