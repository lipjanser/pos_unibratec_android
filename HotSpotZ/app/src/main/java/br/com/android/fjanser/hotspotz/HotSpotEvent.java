package br.com.android.fjanser.hotspotz;

public class HotSpotEvent {
    // Eventos disparados/recebidos pelos Broadcasts da aplicação
    public static final String HOTSPOT_LOADED = "loaded";
    public static final String UPDATE_FAVORITE = "favorite";
    public static final String HOTSPOT_FAVORITE_UPDATED = "updated";

    // Chave para obter o Movie a partir das intents de broadcast
    public static final String EXTRA_HOTSPOT = "hotspot";
}
