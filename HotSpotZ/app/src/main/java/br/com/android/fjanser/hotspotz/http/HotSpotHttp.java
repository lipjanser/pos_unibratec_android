package br.com.android.fjanser.hotspotz.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.android.fjanser.hotspotz.model.HotSpot;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotSpotHttp {

    public static final String URL_SEARCH_BY_TITLE = "http://www.omdbapi.com/?s=%s&r=json";
    public static final String URL_SEARCH_BY_IMDB_ID = "http://www.omdbapi.com/?i=%s&plot=full&r=json";

    public static List<HotSpot> searchHotSpots(String query){
        List<HotSpot> hotSpots = new ArrayList<>();

        // Abre a conexão com o servidor
        OkHttpClient client = new OkHttpClient();
        String url = String.format(URL_SEARCH_BY_TITLE, query);
        Request request = new Request.Builder().url(url).build();

        Response response = null;
        try {
            // Realiza a chamada ao servidor
            response = client.newCall(request).execute();

            // response.body retorna o corpo da resposta, que no nosso caso é JSON
            String json = response.body().string();

            // Esse JSON retorna um objeto JSON onde a propriedade "Search" traz
            // a lista dos resultados. Por isso, obtemos o JSONArray com esse resultado
            // e só então passamos para o GSON ler.
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("Search");
            String jsonList = jsonArray.toString();

            Gson gson = new Gson();
            HotSpot[] hotSpotsArray = gson.fromJson(jsonList, HotSpot[].class);
            hotSpots.addAll(Arrays.asList(hotSpotsArray));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hotSpots;
    }

    public static HotSpot loadHotSpotById(String imdbId){

        // Abre a conexão com o servidor
        OkHttpClient client = new OkHttpClient();
        String url = String.format(URL_SEARCH_BY_IMDB_ID, imdbId);
        Request request = new Request.Builder().url(url).build();

        Response response = null;
        try {
            // Realiza a chamada ao servidor
            response = client.newCall(request).execute();

            // response.body retorna o corpo da resposta, que no nosso caso é JSON
            String json = response.body().string();

            // Essa resposta já traz apenas um objeto com todas as informações do filme
            // Então é só passar para o JSON fazer o parser
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(HotSpot.class, new HotSpotDeserializer());
            Gson gson = gsonBuilder.create();
            return gson.fromJson(json, HotSpot.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
