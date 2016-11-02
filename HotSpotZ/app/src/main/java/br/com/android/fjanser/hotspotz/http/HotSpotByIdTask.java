package br.com.android.fjanser.hotspotz.http;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import br.com.android.fjanser.hotspotz.model.HotSpot;

// Essa tarefa está carregando as informações do filme baseado no ID do imdb
public class HotSpotByIdTask extends AsyncTaskLoader<HotSpot> {

    private HotSpot mHotSpot;
    private String mId;

    public HotSpotByIdTask(Context context, String id) {
        super(context);
        mId = id;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mHotSpot == null || !mHotSpot.getEndereco().equals(mId)) {
            forceLoad();
        } else {
            deliverResult(mHotSpot);
        }
    }

    @Override
    public HotSpot loadInBackground() {
        mHotSpot = HotSpotHttp.loadHotSpotById(mId);
        return mHotSpot;
    }
}
