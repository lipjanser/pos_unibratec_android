package br.com.android.fjanser.hotspotz;

import android.app.Activity;
import android.content.res.Configuration;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//import br.com.android.fjanser.hotspotz.http.MoviesSearchTask;
import br.com.android.fjanser.hotspotz.model.HotSpot;
import br.com.android.fjanser.hotspotz.R;


public class HotSpotListFragment extends Fragment
        implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<List<HotSpot>>
{
    private static final String QUERY_PARAM = "param";
    public static final int LOADER_ID = 0;

    SearchView mSearchView;
    RecyclerView mRecyclerView;
    HotSpotAdapter mAdapter;
    List<HotSpot> mHotSpotList;
    LoaderManager mLoaderManager;
    View mEmptyView;
    Button btnGeraPlanilha;

    String hotspotBusca = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mHotSpotList = new ArrayList<>();
        mAdapter = new HotSpotAdapter(getActivity(), mHotSpotList);
        mAdapter.setHotSpotClickListener(new OnHotSpotClickListener() {
            @Override
            public void onHotSpotClick(View view, HotSpot hotSpot, int position) {
                // Nessa abordagem o click é mais lento,
                // mas não precisamos usar um atributo adicional
                Activity activity = getActivity();
                if (activity instanceof OnHotSpotClickListener){
                    ((OnHotSpotClickListener)activity).onHotSpotClick(view, hotSpot, position);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hotspot_list, container, false);
        mEmptyView = view.findViewById(R.id.empty_view_root);
        btnGeraPlanilha = (Button) view.findViewById(R.id.btnGeraPlanilha);
        btnGeraPlanilha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                geraPlanilhaExcel();
            }
        });
        mRecyclerView = (RecyclerView)view.findViewById(R.id.main_recycler_hotspots);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && getResources().getBoolean(R.bool.phone)) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setAdapter(mAdapter);

        mLoaderManager = getLoaderManager();
        mLoaderManager.initLoader(LOADER_ID, null, this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);
    }

    // ---- OnQueryTextListener
    @Override
    public boolean onQueryTextSubmit(String query) {
        //Bundle params = new Bundle();
        SearchForHotSpotTask searchhotspot = new SearchForHotSpotTask(getContext(), query);
        hotspotBusca = query;
        //params.putString(QUERY_PARAM, query);
        //mLoaderManager.restartLoader(LOADER_ID, params, this);
        //mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    // ---- LoaderManager.LoaderCallbacks
    @Override
    public Loader<List<HotSpot>> onCreateLoader(int id, Bundle args) {
        String s = args != null ? args.getString(QUERY_PARAM) : null;
        return null;// new MoviesSearchTask(getContext(), s);
    }

    @Override
    public void onLoadFinished(Loader<List<HotSpot>> loader, List<HotSpot> data) {
        mHotSpotList.clear();
        if (data != null && data.size() > 0){
            mHotSpotList.addAll(data);
            mEmptyView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<HotSpot>> loader) {
    }

    public void geraPlanilhaExcel(){
        PlanilhaExcel pe;

        pe = new PlanilhaExcel(this.getContext());

        if(pe.gerarPlanilhaExcel()){
            Toast.makeText(this.getContext(), pe.getCaminhoNomeArquivo(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this.getContext(), "Deu ruim clã. =(", Toast.LENGTH_LONG).show();
        }
    }

    LoaderManager.LoaderCallbacks<List<Address>> mBuscaLocalCallback = new LoaderManager.LoaderCallbacks<List<Address>>(){

        @Override
        public Loader<List<Address>> onCreateLoader(int id, Bundle args) {
            return new SearchForHotSpotTask(HotSpotListFragment.this.getContext(),hotspotBusca);
        }

        @Override
        public void onLoadFinished(Loader<List<Address>> loader,final List<Address> data) {
            new Handler().post(new Runnable(){
                @Override
                public void run() {

                }
            });
        }

        @Override
        public void onLoaderReset(Loader<List<Address>> loader) {

        }
    };

}
