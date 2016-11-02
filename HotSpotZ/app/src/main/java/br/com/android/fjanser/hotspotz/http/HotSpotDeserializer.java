package br.com.android.fjanser.hotspotz.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import br.com.android.fjanser.hotspotz.model.HotSpot;

// Classe que customiza o GSON para fazer a leitura do JSON vindo do servidor.
// Utilizada na classe MovieHttp
public class HotSpotDeserializer implements JsonDeserializer<HotSpot> {

    @Override
    public HotSpot deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject jsonObject = (JsonObject) json;
            HotSpot hotSpot = new HotSpot();
//            hotSpot.setImdbId(jsonObject.get("imdbID").getAsString());
//            hotSpot.setTitle(jsonObject.get("Title").getAsString());
//            hotSpot.setYear(jsonObject.get("Year").getAsString());
//            hotSpot.setPoster(jsonObject.get("Poster").getAsString());
//            hotSpot.setGenre(jsonObject.get("Genre").getAsString());
//            hotSpot.setDirector(jsonObject.get("Director").getAsString());
//            hotSpot.setPlot(jsonObject.get("Plot").getAsString());
//            hotSpot.setActors(jsonObject.get("Actors").getAsString().split(","));
//            hotSpot.setRuntime(jsonObject.get("Runtime").getAsString());
//            hotSpot.setRating(asFloat(jsonObject.get("imdbRating").getAsString()));

            return hotSpot;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    float asFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
