package br.com.android.fjanser.hotspotz;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;
import java.util.Locale;

public class SearchForHotSpotTask extends AsyncTaskLoader<List<Address>> {

    Context mContext;
    String mLocal;
    List<Address> mEnderecosEncontrados;

    public SearchForHotSpotTask(Context ctx, String local){
        super(ctx);
        mContext = ctx;
        mLocal = local;
    }

    @Override
    protected void onStartLoading(){
        if(mEnderecosEncontrados == null){
            forceLoad();
        } else {
            deliverResult(mEnderecosEncontrados);
        }
    }

    @Override
    public List<Address> loadInBackground() {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try{
            mEnderecosEncontrados = geocoder.getFromLocationName(mLocal, 20);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return mEnderecosEncontrados;
    }
}
