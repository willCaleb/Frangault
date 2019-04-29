package cursoandroid.com.frangault.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {

    //Construtor da classe CriaBanco
    public CriaBanco(Context context){
        super(context, NOME_BANCO,null,VERSION);
    }

    //Definie as constantes da classe CriaBanco
    private static final String NOME_BANCO = "carros.db";
    public static final String TABELA = "carros";
    public static final String ID = "_id";
    public static final String MODELO = "modelo";
    public static final String VERSAO = "versao";
    public static final String MOTOR = "motor";
    //public static final String FOTO = "foto";
    public static final String ANO = "ano";
    private static final int VERSION = 1;

    //Cria uma tabela se não existir
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+ "(_id integer primary key autoincrement," + MODELO + "," +
                ANO + "," + MOTOR +")";
        db.execSQL(sql);
    }

    //Se a tabela for alterada ela é automaticamente deletada e é criada outra tabela
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}

