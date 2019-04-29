package cursoandroid.com.frangault.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoController {
    //variáveis da classe BancoController
    private SQLiteDatabase db;
    private CriaBanco banco;

    //construtor da classe BancoController
    public BancoController(Context context){
        banco = new CriaBanco(context);
    }

    //método para fazer os Inserts na tabela
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

        //teste para saber se inseriu
        if (resultado == -1)
            return "Erro ao inserir registro";
        else {
            return "Inseriu";
        }
    }

    //método para carregar todos os dados da tabela
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

    //método para carregar um elemento da tabela definido pelo ID
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

    //método para alterar um elemento da tabela definido pelo ID
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

    //método para deletar um elemento da tabela definido pelo ID
    public void deletar(int id){
        String where = CriaBanco.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.TABELA, where, null);
        db.close();
    }
}

