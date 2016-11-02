package br.com.android.fjanser.hotspotz;


import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;

import java.util.Arrays;

import br.com.android.fjanser.hotspotz.database.HotSpotContract;
import br.com.android.fjanser.hotspotz.database.HotSpotDBHelper;
import br.com.android.fjanser.hotspotz.database.HotSpotProvider;
//import br.com.android.fjanser.hotspotz.http.MovieByIdTask;
import br.com.android.fjanser.hotspotz.model.HotSpot;
import br.com.android.fjanser.hotspotz.R;

import static android.R.attr.onClick;

public class DetailHotSpotFragment extends Fragment {

    private static final String EXTRA_HOTSPOT = "hotspot";
    private static final int LOADER_DB = 0;
    private static final int LOADER_WEB = 1;

    ImageView imgPoster;
    TextView  txtTitle;
    TextView  txtYear;
    TextView  txtGenre;
    TextView  txtDirector;
    TextView  txtPlot;
    TextView  txtRuntime;
    TextView  txtActors;
    RatingBar rating;

    TextView  txtEtapa1;
    EditText editTxtEtapa1;

    TextView txtEtapa2;
    EditText editTxtEtapa2;

    TextView txtEtapa3;
    EditText editTxtEtapa3;

    TextView txtEtapa4;
    EditText editTxtEtapa4;

    TextView  txtEtapa5;
    EditText editTxtEtapa5;

    TextView txtEtapa6;
    EditText editTxtEtapa6;

    TextView txtEtapa7;
    EditText editTxtEtapa7;

    Button btnAttPontuacoes;

    HotSpot mHotSpot;
    LocalBroadcastManager mLocalBroadcastManager;
    HotSpotEventReceiver mReceiver;
    ShareActionProvider mShareActionProvider;
    Intent mShareIntent;

    // Para criarmos um DetailMovieFragment precisamos passar um objeto Movie
    public static DetailHotSpotFragment newInstance(HotSpot hotSpot) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_HOTSPOT, hotSpot);

        DetailHotSpotFragment detailHotSpotFragment = new DetailHotSpotFragment();
        detailHotSpotFragment.setArguments(args);
        return detailHotSpotFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inicializando o layout do fragment...
        View view = inflater.inflate(R.layout.fragment_detail_hotspot, container, false);
        //View view = inflater.inflate(R.layout.fragment_detail_hotspot, container, false);
        //imgPoster   = (ImageView)view.findViewById(R.id.detail_image_poster);
        //txtTitle    = (TextView)view.findViewById(R.id.detail_text_title);
//        txtYear     = (TextView)view.findViewById(R.id.detail_text_year);
//        txtGenre    = (TextView)view.findViewById(R.id.detail_text_genre);
//        txtDirector = (TextView)view.findViewById(R.id.detail_text_director);
//        txtPlot     = (TextView)view.findViewById(R.id.detail_text_plot);
//        txtRuntime  = (TextView)view.findViewById(R.id.detail_text_runtime);
//        txtActors   = (TextView)view.findViewById(R.id.detail_text_actors);
//        rating      = (RatingBar)view.findViewById(R.id.detail_rating);
        //editTxtEtapa1 = (EditText) view.findViewById(R.id.detail_text_etapa1);
        //editTxtEtapa2 = (EditText) view.findViewById(R.id.detail_text_etapa2);
        //editTxtEtapa3 = (EditText) view.findViewById(R.id.detail_text_etapa3);
        //editTxtEtapa4 = (EditText) view.findViewById(R.id.detail_text_etapa4);
        //editTxtEtapa5 = (EditText) view.findViewById(R.id.detail_text_etapa5);
        //editTxtEtapa6 = (EditText) view.findViewById(R.id.detail_text_etapa6);
        //editTxtEtapa7 = (EditText) view.findViewById(R.id.detail_text_etapa7);

        //btnAttPontuacoes = (Button) view.findViewById(R.id.btnAttPontuacoes);

        //btnAttPontuacoes.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        atualizarPontuacaoEquipe();
        //    }
        //});
        // Animação de transição de tela
        //ViewCompat.setTransitionName(imgPoster, "capa");
        //ViewCompat.setTransitionName(txtTitle, "titulo");
        //ViewCompat.setTransitionName(txtYear, "ano");

        // Inicializamos mMovie (ver onSaveInsatnceState)
        if (savedInstanceState == null){
            // Se não tem um estado anterior, use o que foi passado no método newInstance.
            mHotSpot = (HotSpot)getArguments().getSerializable(EXTRA_HOTSPOT);
        } else {
            // Se há um estado anterior, use-o
            mHotSpot = (HotSpot)savedInstanceState.getSerializable(EXTRA_HOTSPOT);
        }

        // Se o objeto mMovie possui um ID (no banco local), carregue do banco local,
        // senão carregue do servidor.
//        boolean isFavorite = MovieDetailUtils.isFavorite(getActivity(), mMovie.getImdbId());
//        if (isFavorite){
//            // Faz a requisição em background ao banco de dados (ver mCursorCallback)
//            getLoaderManager().initLoader(LOADER_DB, null, mCursorCallback);
//        } else {
//            // Faz a requisição em background ao servidor (ver mMovieCallback)
//            getLoaderManager().initLoader(LOADER_WEB, null, mMovieCallback);
//        }

        //txtTitle.setText("Equipe " + String.valueOf(mHotSpot.getEquipe()));
        //recuperaPontuacoesEquipe();
        //editTxtEtapa1.setText(String.valueOf(mHotSpot.getEtapa1()));
        //editTxtEtapa2.setText(String.valueOf(mHotSpot.getEtapa2()));
        //editTxtEtapa3.setText(String.valueOf(mHotSpot.getEtapa3()));
        //editTxtEtapa4.setText(String.valueOf(mHotSpot.getEtapa4()));
        //editTxtEtapa5.setText(String.valueOf(mHotSpot.getEtapa5()));
        //editTxtEtapa6.setText(String.valueOf(mHotSpot.getEtapa6()));
        //editTxtEtapa7.setText(String.valueOf(mHotSpot.getEtapa7()));
        //// Registramos o receiver para tratar sabermos quando o botão de favoritos da
        //// activity de detalhes foi chamado.
        //mReceiver = new MovieEventReceiver();
        //mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        //mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(HotSpotEvent.UPDATE_FAVORITE));

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Precisamos manter o objeto mMovie atualizado pois ele pode ter sido
        // incluído e excluído dos favoritos.
        outState.putSerializable(EXTRA_HOTSPOT, mHotSpot);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Desregistramos o receiver ao destruir a View do fragment
        //mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (mShareIntent != null){
            mShareActionProvider.setShareIntent(mShareIntent);
        }
    }

    // --------------- LoaderManager.LoaderCallbacks<Movie>
    // Esse callback trata o retorno da requisição feita ao servidor
    LoaderManager.LoaderCallbacks mHotSpotCallback = new LoaderManager.LoaderCallbacks<HotSpot>() {
        @Override
        public Loader<HotSpot> onCreateLoader(int id, Bundle args) {
            // inicializa a requisição em background para o servidor usando AsyncTaskLoader
            // (veja a classe MovieByIdTask)
            return null;//new MovieByIdTask(getActivity(), mMovie.getImdbId());
        }

        @Override
        public void onLoadFinished(Loader<HotSpot> loader, HotSpot hotSpot) {
            updateUI(hotSpot);
        }

        @Override
        public void onLoaderReset(Loader<HotSpot> loader) {
        }
    };

    // --------------- LoaderManager.LoaderCallbacks<Cursor>
    // Esse callback trata o retorno da requisição feita ao servidor
    LoaderManager.LoaderCallbacks<Cursor> mCursorCallback = new LoaderManager.LoaderCallbacks<Cursor>(){

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // inicializa a requisição em background para o ContentProvider usando CursorLoader
            // perceba que estamos utilizando a Uri específica
            // (veja o método query do MovieProvider)
            return new CursorLoader(getActivity(),
                    HotSpotProvider.HOTSPOT_URI,
                    null,
                    HotSpotContract.COL_ENDERECO +" = ?",
                    new String[]{ String.valueOf(mHotSpot.getEndereco()) }, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            // Ao receber o retorno do cursor, criamos um objeto Movie e preenchemos a tela
            // (ver updateUI)
            if (cursor != null && cursor.moveToFirst()) {
                HotSpot hotSpot = new HotSpot();
                hotSpot.setId(cursor.getLong(cursor.getColumnIndex(HotSpotContract._ID)));
                hotSpot.setEndereco(cursor.getString(cursor.getColumnIndex(HotSpotContract.COL_ENDERECO)));
                hotSpot.setPais(cursor.getString(cursor.getColumnIndex(HotSpotContract.COL_PAIS)));
                hotSpot.setLatitude(cursor.getFloat(cursor.getColumnIndex(HotSpotContract.COL_LATITUDE)));
                hotSpot.setLongitude(cursor.getFloat(cursor.getColumnIndex(HotSpotContract.COL_LONGITUDE)));
                updateUI(hotSpot);
                createShareIntent(hotSpot);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
        }
    };

    private void createShareIntent(HotSpot hotSpot) {
        mShareIntent = new Intent(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_text, hotSpot.getEndereco(), hotSpot.getPais()));
    }

    // --------------- INNER
    // Esse receiver é chamado pelo FAB da DetailActivity para iniciar o processo
    // de inserir/excluir o movie nos favoritos
    class HotSpotEventReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(HotSpotEvent.UPDATE_FAVORITE)) {
                toggleFavorite();
            }
        }
    }

    // --------------- PRIVATE

    private void updateUI(HotSpot hotSpot){
        // Atualiza o objeto mMovie com os dados vindos dos callbacks
        // (ver mCursorCallback e mMovieCallback)
        mHotSpot = hotSpot;
        //txtTitle.setText(hotSpot.getTitle());
        //txtYear.setText(hotSpot.getYear());
        //txtGenre.setText(hotSpot.getGenre());
        //txtDirector.setText(hotSpot.getDirector());
        //txtPlot.setText(hotSpot.getPlot());
        //txtRuntime.setText(hotSpot.getRuntime());
        //rating.setRating(hotSpot.getRating() / 2);

        // Tanto no JSON quanto no Banco, estamos salvando a lista
        // de atores separado por vírgula
        //TODO Criar uma nova tabela e fazer chave estrangeira
//        StringBuffer sb = new StringBuffer();
//        for (String actor : hotSpot.getActors()) {
//            sb.append(actor).append('\n');
//        }
//        txtActors.setText(sb.toString());
//
//        // Enviando mensagem para todos que querem saber que o filme carregou
//        // (ver DetailActivity.MovieReceiver)
//        notifyUpdate(HotSpotEvent.HOTSPOT_LOADED);
//
//        // Quando estiver em tablet, exiba o poster no próprio fragment
//        if (getResources().getBoolean(R.bool.tablet)){
//            imgPoster.setVisibility(View.VISIBLE);
//            //Glide.with(imgPoster.getContext()).load(movie.getPoster()).into(imgPoster);
//        }
    }

    // Método auxiliar que insere/remove o movie no banco de dados
    private void toggleFavorite() {
        if (mHotSpot == null) return; // isso não deve acontecer...

        // Primeiro verificamos se o livro está no banco de dados
        boolean isFavorite = HotSpotDetailUtils.isFavorite(getActivity(), mHotSpot.getEndereco());

        boolean success = false;
        if (isFavorite) {
            // Se já é favorito, exclua
            if (deleteFavorite(mHotSpot.getId())){
                success = true;
                mHotSpot.setId(0);
                getLoaderManager().destroyLoader(LOADER_DB);
            }
            //TODO Mensagem de erro ao excluir

        } else {
            // Se não é favorito, inclua...
            long id = insertFavorite(mHotSpot);
            success = id > 0;
            mHotSpot.setId(id);
        }

        // Se deu tudo certo...
        if (success) {
            // Envia a mensagem para as activities (para atualizar o FAB)
            notifyUpdate(HotSpotEvent.HOTSPOT_FAVORITE_UPDATED);

            // Exibe o snackbar que permite o "desfazer"
            //TODO Internailizar a aplicação
//            Snackbar.make(getView(),
//                    isFavorite ? R.string.msg_removed_favorites : R.string.msg_added_favorites,
//                    Snackbar.LENGTH_LONG)
//                    .setAction(R.string.text_undo, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            toggleFavorite();
//                        }
//                    }).show();
        }
    }

    private void notifyUpdate(String action){
        // Cria a intent e dispara o broadcast
        Intent it = new Intent(action);
        it.putExtra(HotSpotEvent.EXTRA_HOTSPOT, mHotSpot);
        mLocalBroadcastManager.sendBroadcast(it);
    }

    // Método auxiliar para excluir nos favoritos
    //TODO fazer delete em background
    private boolean deleteFavorite(long hotspotId){
        return getActivity().getContentResolver().delete(
                ContentUris.withAppendedId(HotSpotProvider.HOTSPOT_URI, hotspotId),
                null, null) > 0;
    }

    // Método auxiliar para inserir nos favoritos
    //TODO fazer insert em background
    private long insertFavorite(HotSpot hotSpot){
        ContentValues contentValues = new ContentValues();
        contentValues.put(HotSpotContract.COL_ENDERECO, hotSpot.getEndereco());
        contentValues.put(HotSpotContract.COL_PAIS, hotSpot.getPais());
        contentValues.put(HotSpotContract.COL_LATITUDE, hotSpot.getLatitude());
        contentValues.put(HotSpotContract.COL_LONGITUDE, hotSpot.getLongitude());

        Uri uri = getActivity().getContentResolver().insert(HotSpotProvider.HOTSPOT_URI, contentValues);
        //TODO mensagem de erro ao falhar
        return ContentUris.parseId(uri);
    }

    public void recuperaCoordenadas(){
        HotSpotDBHelper mHelper;
        SQLiteDatabase db;
        Cursor cursor;

        mHelper = new HotSpotDBHelper(getContext());
        db = mHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT " + HotSpotContract.COL_ENDERECO + ", " +
                HotSpotContract.COL_PAIS + ", " +
                HotSpotContract.COL_LATITUDE + ", " +
                HotSpotContract.COL_LONGITUDE + " FROM " + HotSpotContract.TABLE_NAME + " " +
                "WHERE " + HotSpotContract.COL_ENDERECO + " Like '%?%' ", new String[]{mHotSpot.getEndereco()});

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            mHotSpot.setPais(cursor.getString(cursor.getColumnIndex(HotSpotContract.COL_PAIS)));
            mHotSpot.setLatitude(cursor.getFloat(cursor.getColumnIndex(HotSpotContract.COL_LATITUDE)));
            mHotSpot.setLongitude(cursor.getFloat(cursor.getColumnIndex(HotSpotContract.COL_LONGITUDE)));
        }
        cursor.close();
    }


}
