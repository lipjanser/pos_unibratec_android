package br.com.android.fjanser.hotspotz.http;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import br.com.android.fjanser.hotspotz.model.HotSpot;

// Essa tarefa carrega a lista dos filmes baseado nos parâmetros da busca
public class HotSpotSearchTask extends AsyncTaskLoader<List<HotSpot>> {
    List<HotSpot> hotspots;
    String query;

    public HotSpotSearchTask(Context context, String query) {
        super(context);
        this.query = query;
        this.hotspots = new ArrayList<>();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (query != null && (hotspots == null || hotspots.isEmpty())) {
            forceLoad();
        } else {
            deliverResult(hotspots);
        }
    }

    @Override
    public List<HotSpot> loadInBackground() {
        hotspots.addAll(HotSpotHttp.searchHotSpots(query));
        return hotspots;
    }
}