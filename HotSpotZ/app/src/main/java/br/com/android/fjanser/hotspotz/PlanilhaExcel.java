package br.com.android.fjanser.hotspotz;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import br.com.android.fjanser.hotspotz.database.HotSpotContract;
import br.com.android.fjanser.hotspotz.database.HotSpotDBHelper;
import br.com.android.fjanser.hotspotz.model.HotSpot;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Created by FELIPE on 22/10/2016.
 */
public class PlanilhaExcel {

    private WritableWorkbook wb;
    private WritableSheet plan;
    private File file;
    private Calendar calendar;
    private String dataAtual = "";
    private Context ctx;
    private HotSpotDBHelper mHelper;
    private Cursor cursor;
    private String caminhoNomeArquivo = "";

    public PlanilhaExcel(Context ctx){
        mHelper = new HotSpotDBHelper(ctx);
        this.ctx = ctx;
    }

    public boolean gerarPlanilhaExcel(){
        Label label = null;
        int qtdeLinhas = 0;
        int qtdeColunas = 0;
        try {
            calendar = Calendar.getInstance();
            dataAtual = String.valueOf(calendar.get(Calendar.YEAR));
            dataAtual += String.valueOf(calendar.get(Calendar.MONTH));
            dataAtual += String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            dataAtual += String.valueOf(calendar.get(Calendar.HOUR));
            dataAtual += String.valueOf(calendar.get(Calendar.MINUTE));
            dataAtual += String.valueOf(calendar.get(Calendar.SECOND));
            file = new File(ctx.getExternalFilesDir(null),"plan_" + dataAtual + ".xls");
            //Log.d("FJFA","Nome do Diretorio e arquivo:" + ctx.getExternalFilesDir(null).toString() + "\\" +"plan_" + dataAtual + ".xls");
            wb = Workbook.createWorkbook(file);
            wb.createSheet("Planilha",0);
            plan = wb.getSheet(0);

            cursor = this.query();

            qtdeLinhas = cursor.getCount() + 1;
            qtdeColunas = cursor.getColumnCount();

            cursor.moveToFirst();
            for(int coluna = 0; coluna < qtdeColunas;coluna++){
                label = new Label(coluna,0,String.valueOf(cursor.getColumnName(coluna)).toUpperCase());
                try {
                    plan.addCell(label);
                } catch (RowsExceededException e1) {
                    e1.printStackTrace();
                    return false;
                } catch (WriteException e1) {
                    e1.printStackTrace();
                    return false;
                }
            }

            for(int linha = 1; linha < qtdeLinhas;linha++){
                for(int coluna = 0; coluna < qtdeColunas;coluna++){
                        if(coluna == 0){
                            label = new Label(coluna,linha,String.valueOf(cursor.getInt(cursor.getColumnIndex(HotSpotContract._ID))));
                        } else if(coluna == 1){
                            label = new Label(coluna,linha,String.valueOf(cursor.getString(cursor.getColumnIndex(HotSpotContract.COL_ENDERECO))));
                        } else if(coluna == 2){
                            label = new Label(coluna,linha,String.valueOf(cursor.getString(cursor.getColumnIndex(HotSpotContract.COL_PAIS))));
                        } else if(coluna == 3){
                            label = new Label(coluna,linha,String.valueOf(cursor.getFloat(cursor.getColumnIndex(HotSpotContract.COL_LATITUDE))));
                        } else if(coluna == 4){
                            label = new Label(coluna,linha,String.valueOf(cursor.getFloat(cursor.getColumnIndex(HotSpotContract.COL_LONGITUDE))));
                        }
                        try {
                            plan.addCell(label);
                        } catch (RowsExceededException e1) {
                            e1.printStackTrace();
                            return false;
                        } catch (WriteException e1) {
                            e1.printStackTrace();
                            return false;
                        }
                }
                cursor.moveToNext();
            }
            wb.write();
            wb.close();
            this.setCaminhoNomeArquivo("Nome do Diretorio e arquivo:" + ctx.getExternalFilesDir(null).toString() + "\\" +"plan_" + dataAtual + ".xls");
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public Cursor query() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return  db.query(HotSpotContract.TABLE_NAME,new String[]{
                HotSpotContract._ID,
                HotSpotContract.COL_ENDERECO,
                HotSpotContract.COL_PAIS,
                HotSpotContract.COL_LATITUDE,
                HotSpotContract.COL_LONGITUDE
        },null,null,null,null,HotSpotContract._ID + " ASC");
    }

    public String getCaminhoNomeArquivo() {
        return caminhoNomeArquivo;
    }

    public void setCaminhoNomeArquivo(String caminhoNomeArquivo) {
        this.caminhoNomeArquivo = caminhoNomeArquivo;
    }
}