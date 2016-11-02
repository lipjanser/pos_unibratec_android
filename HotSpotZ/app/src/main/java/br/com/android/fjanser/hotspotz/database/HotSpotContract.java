package br.com.android.fjanser.hotspotz.database;

import android.provider.BaseColumns;

public interface HotSpotContract extends BaseColumns {
    // Nome da tabela no banco de dados
    String TABLE_NAME = "HOTSPOTS";

    // Colunas do banco de dados
    String COL_ENDERECO  = "Endereco";
    String COL_PAIS  = "Pais";
    String COL_LATITUDE  = "Latitude";
    String COL_LONGITUDE  = "Longitude";

    // Colunas utilizadas pelo adapter do fragment de favoritos
    String[] LIST_COLUMNS = new String[]{
            HotSpotContract._ID,
            HotSpotContract.COL_ENDERECO,
            HotSpotContract.COL_PAIS,
            HotSpotContract.COL_LATITUDE,
            HotSpotContract.COL_LONGITUDE
    };
}
