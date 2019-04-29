package cursoandroid.com.frangault.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoController {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context){
        banco = new CriaBanco(context);
    }

    public String insereDado(String modelo, int ano, String motor){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.MODELO, modelo);
        valores.put(CriaBanco.ANO, ano);
        valores.put(CriaBanco.MOTOR, motor);
        //valores.put(CriaBanco.FOTO, foto);

        resultado = db.insert(CriaBanco.TABELA, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else {
            return "Inseriu";
        }
    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {banco.ID,banco.MODELO, banco.ANO, banco.MOTOR};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregarDadoById(int id){
        Cursor cursor;
        String[] campos = {banco.ID, banco.MODELO, banco.ANO, banco.MOTOR};
        String where = CriaBanco.ID + " = " + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.TABELA, campos, where, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void alterarRegistro(int id, String modelo, int ano, String motor){
        ContentValues valores;
        String where;
        where = CriaBanco.ID + "=" + id;

        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put(CriaBanco.MODELO, modelo);
        valores.put(CriaBanco.MOTOR, motor);
        valores.put(CriaBanco.ANO, ano);

        db.update(CriaBanco.TABELA, valores, where, null);
        db.close();
    }

    public void deletar(int id){
        String where = CriaBanco.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.TABELA, where, null);
        db.close();
    }
}

