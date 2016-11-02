package br.com.android.fjanser.hotspotz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.bumptech.glide.Glide;

import br.com.android.fjanser.hotspotz.model.HotSpot;
import br.com.android.fjanser.hotspotz.R;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_HOTSPOT = "hotspot"; // vindo dos favoritos

    GoogleMap mGoogleMap;
    LatLng mOrigem;

    HotSpot mHotSpot;
    FloatingActionButton fab;

    LocalBroadcastManager mLocalBroadcastManager;
    HotSpotReceiver mReceiver;

    private void atualizaMapa(String endereco){
        BitmapDescriptor icon;
        CameraPosition cameraposition;

        icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.addMarker(new MarkerOptions().position(mOrigem).icon(icon).title(endereco));
        cameraposition = new CameraPosition.Builder().target(mOrigem).zoom(17).bearing(90).tilt(45).build();

        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraposition));
        //mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mOrigem,17.0f));
        //mGoogleMap.addMarker(new MarkerOptions().position(mOrigem).title("Av. Paulista").snippet("São Paulo"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.hotspotmap);

        mHotSpot = (HotSpot)getIntent().getSerializableExtra(EXTRA_HOTSPOT);
        mGoogleMap = fragment.getMap();
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mOrigem = new LatLng(mHotSpot.getLatitude(),mHotSpot.getLongitude());
        //mOrigem = new LatLng(-23.561706,-46.655981);
        atualizaMapa(mHotSpot.getEndereco());

        // A MainActivity passará um objeto Movie,
        // então criamos o fragment de detalhe com esse objeto
        //mHotSpot = (HotSpot)getIntent().getSerializableExtra(EXTRA_HOTSPOT);
        DetailHotSpotFragment detailHotSpotFragment;
        detailHotSpotFragment = DetailHotSpotFragment.newInstance(mHotSpot);

        // Todas as informações do filme estão no DetailMovieFragment,
        // exceto a capa que já carregamos aqui, uma vez que essa informação já existe no objeto Movie.

        //ImageView imgPoster = (ImageView)findViewById(R.id.detail_image_poster);
        //ViewCompat.setTransitionName(imgPoster, "capa");
        //Glide.with(imgPoster.getContext()).load(mMovie.getPoster()).into(imgPoster);

        // Esse receiver detectará se o Movie foi adicionado ou removido dos favoritos
        // TODO Substituir pelo EventBus?
        mReceiver = new HotSpotReceiver();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(HotSpotEvent.HOTSPOT_LOADED));
        mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(HotSpotEvent.HOTSPOT_FAVORITE_UPDATED));

        // O FAB faz parte do layout da Activity, mas precisa ser atualizado
        // quando o movie é inserido ou removido dos favoritos. mReceiver fará isso ;)
        //fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Quando clicamos no botão, estamos avisando ao fragment de detalhes para
//                // inserir/remover o Movie no banco. Veja DetailMovieFragment.MovieEventReceiver.
//                Intent it = new Intent(HotSpotEvent.UPDATE_FAVORITE);
//                mLocalBroadcastManager.sendBroadcast(it);
//            }
//        });

        if (savedInstanceState == null) {
            // Adicionando o fragment de detalhes na tela
            //getSupportFragmentManager().beginTransaction().replace(R.id.placeholderDetail, detailHotSpotFragment).commit();
        }

        //TODO barra de status transparente?
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Esse receiver atualizará o status do botão de favoritos.
    class HotSpotReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            fab.setVisibility(View.VISIBLE);
            HotSpot hotSpot = (HotSpot)intent.getSerializableExtra(HotSpotEvent.EXTRA_HOTSPOT);
            HotSpotDetailUtils.toggleFavorite(context, fab, hotSpot.getEndereco());
        }
    }
}
