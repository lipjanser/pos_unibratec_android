package br.com.android.fjanser.hotspotz;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import br.com.android.fjanser.hotspotz.database.HotSpotContract;
import br.com.android.fjanser.hotspotz.database.HotSpotDBHelper;
import br.com.android.fjanser.hotspotz.model.HotSpot;
import br.com.android.fjanser.hotspotz.R;

public class MainActivity extends AppCompatActivity implements OnHotSpotClickListener {

    FloatingActionButton fab;
    LocalBroadcastManager mLocalBroadcastManager;
    HotSpotReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializando o PagerAdapter, ViewPager e TabLayout para exibir as abas
        HotSpotsPagerAdapter pagerAdapter = new HotSpotsPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Definimos alguns comportamentos especiais para tablets...
        if (getResources().getBoolean(R.bool.tablet)){
            // As abas ficam alinhadas a esquerda
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

            // Inicializamos esse receiver para saber quando o filme no fragment de detalhe
            // foi carregado (ver método notifyUpdate da DetailMovieFragment)
            mReceiver = new HotSpotReceiver();
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
            mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(HotSpotEvent.HOTSPOT_LOADED));
            mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(HotSpotEvent.HOTSPOT_FAVORITE_UPDATED));

            // O FAB envia a mensagem para o DetailFragment inserir/excluir filme no banco
            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(HotSpotEvent.UPDATE_FAVORITE);
                    mLocalBroadcastManager.sendBroadcast(it);
                }
            });
        }
    }

    @Override
    public void onHotSpotClick(View view, HotSpot hotSpot, int position) {
        // Esse método é chamado pelas telas de listagem quando o usuário
        // clica em um item da lista (ver MovieListFragment e FavoriteMoviesFragment)
        if (getResources().getBoolean(R.bool.phone)) {
            ActivityOptionsCompat optionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,Pair.create(view.findViewById(R.id.hotspot_item_text_title), "endereco"));
                            //Pair.create(view.findViewById(R.id.movie_item_image_poster), "capa"),
                            //Pair.create(view.findViewById(R.id.movie_item_text_year), "ano"));

            // Se for smartphone, abra uma nova activity
            Intent it = new Intent(MainActivity.this, DetailActivity.class);
            it.putExtra(DetailActivity.EXTRA_HOTSPOT, hotSpot);
            ActivityCompat.startActivity(this, it, optionsCompat.toBundle());

        } else {
            // Se for tablet, exiba um fragment a direita
            DetailHotSpotFragment detailHotSpotFragment = DetailHotSpotFragment.newInstance(hotSpot);
            getSupportFragmentManager().beginTransaction().replace(R.id.placeholderDetail, detailHotSpotFragment).commit();
        }
    }

    // Esse receiver será chamado quando o fragment de detalhe carrega os dados do filme
    // (ver método notifyUpdate de DetailMovieFragment)
    class HotSpotReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            HotSpot hotSpot = (HotSpot)intent.getSerializableExtra(HotSpotEvent.EXTRA_HOTSPOT);
            fab.setVisibility(View.VISIBLE);
            HotSpotDetailUtils.toggleFavorite(context, fab, hotSpot.getEndereco());
        }
    }

    // O PagerAdapter é o que determina o que será exibido em cada aba
    class HotSpotsPagerAdapter extends FragmentPagerAdapter {
        public HotSpotsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if (position == 1){
                HotSpotListFragment hotSpotListFragment = new HotSpotListFragment();
                return hotSpotListFragment;
            } else {
                FavoriteHotSpotsFragment favoriteHotSpotsFragment = new FavoriteHotSpotsFragment();
                return favoriteHotSpotsFragment;
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return (position == 1)? getString(R.string.tab_search) : getString(R.string.tab_favorites);
        }
        @Override
        public int getCount() {
            return 2;
        }
    }
}
