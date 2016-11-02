package br.com.android.fjanser.hotspotz.model;

//import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//TODO Utilizar Parcelable ou o Parceler
public class HotSpot implements Serializable {
    private long id;
    private String endereco;
    private String pais;
    private float latitude;
    private float longitude;
//    //@SerializedName("Equipe")
//    private int equipe;
//    //@SerializedName("Etapa1")
//    private float etapa1;
//    //@SerializedName("Etapa2")
//    private float etapa2;
//    //@SerializedName("Etapa3")
//    private float etapa3;
//    //@SerializedName("Etapa4")
//    private float etapa4;
//    //@SerializedName("Totals1")
//    private float totals1;
//    //@SerializedName("Etapa5")
//    private float etapa5;
//    //@SerializedName("Totals2")
//    private float totals2;
//    //@SerializedName("Etapa6")
//    private float etapa6;
//    //@SerializedName("Totals3")
//    private float totals3;
//    //@SerializedName("Etapa7")
//    private float etapa7;
//    //@SerializedName("Totals4")
//    private float totals4;
//    //@SerializedName("Final")
//    private String finalResult;

    public HotSpot(){
        this.setEndereco("");
        this.setPais("");
        this.setLatitude(0.0f);
        this.setLongitude(0.0f);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
