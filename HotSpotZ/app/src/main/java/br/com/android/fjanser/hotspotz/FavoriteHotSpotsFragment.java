package br.com.android.fjanser.hotspotz;


import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.android.fjanser.hotspotz.database.HotSpotContract;
import br.com.android.fjanser.hotspotz.database.HotSpotProvider;
import br.com.android.fjanser.hotspotz.model.HotSpot;
import br.com.android.fjanser.hotspotz.R;


public class FavoriteHotSpotsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    OnHotSpotClickListener mHotSpotClickListener;
    HotSpotCursorAdapter mAdapter;
    boolean mFirstRun;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Registrando o listener para saber quando movie foi clicado
        // Essa abordagem é a mais usada, e mais rápida
        // entretanto requer um atributo adicional
        if (context instanceof OnHotSpotClickListener) {
            mHotSpotClickListener = (OnHotSpotClickListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirstRun = savedInstanceState == null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite_hotspots, container, false);
        ListView listView = (ListView) view.findViewById(R.id.favorites_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mHotSpotClickListener != null) {
                    // Pegamos o cursor do adapter
                    Cursor cursor = mAdapter.getCursor();
                    // Movemos para a posição correspondente da lista
                    selectHotSpot(view, position, cursor);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }

        // Inicializamos e definimos o adapter da lista
        mAdapter = new HotSpotCursorAdapter(getActivity(), null);
        listView.setAdapter(mAdapter);

        // Definimos a view a ser exibida se a lista estiver vazia
        listView.setEmptyView(view.findViewById(R.id.empty_view_root));

        // Inicializamos o loader para trazer os registros em background
        getLoaderManager().initLoader(0, null, this);

        return view;
    }

    private void selectHotSpot(View view, int position, Cursor cursor) {
        if (cursor.moveToPosition(position)) {
            // Criamos um objeto Movie para passamos para a MainActivity
            // perceba que esse Movie não tem todos os campos. Pois na tela
            // de listagem apenas os campos necessários são utilizados
            HotSpot hotSpot = HotSpotDetailUtils.hotSpotItemFromCursor(cursor);
            mHotSpotClickListener.onHotSpotClick(view, hotSpot, position);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Realizando a query em bacground (ver método query do MovieProvider)
        return new CursorLoader(getActivity(),
                HotSpotProvider.HOTSPOT_URI,
                HotSpotContract.LIST_COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        mAdapter.swapCursor(data);
        if (data != null
                && data.getCount() > 0
                && getResources().getBoolean(R.bool.tablet)
                && mFirstRun){

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    selectHotSpot(null, 0, data);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
