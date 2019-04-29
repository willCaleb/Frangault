package cursoandroid.com.frangault.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import cursoandroid.com.frangault.R;
import cursoandroid.com.frangault.controller.BancoController;
import cursoandroid.com.frangault.controller.CriaBanco;

public class ListaActivity extends AppCompatActivity {

    //variável da classe ListaActivity
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Toast.makeText(ListaActivity.this, "Pressione um ítem para ver os detalhes ou mantenha pressionado para alterar os dados", Toast.LENGTH_LONG).show();
        BancoController crud = new BancoController(getBaseContext());
        final Cursor cursor= crud.carregaDados();
        //arrays que irão receber a lista de dados recebidas do banco
        String[] nomeCampos =new String[]{CriaBanco.MODELO, CriaBanco.ANO, CriaBanco.MOTOR, CriaBanco.ID};
        int[] idViews = new int[]{R.id.txt_modelo, R.id.txt_ano, R.id.txt_motor, R.id.txt_row_id};

        //define um adaptador para inserir os dados recebidos dentro do layout activity_row
        SimpleCursorAdapter adaptador =new SimpleCursorAdapter(getBaseContext(), R.layout.activity_row, cursor, nomeCampos, idViews, 0);
        lista =(ListView)findViewById(R.id.listView);
        lista.setAdapter(adaptador);

        //quando um elemento da lista é clicado é aberta a CadastroActivity e é enviado o ID do item clicado para que lá seja feita a busca e setados os campos
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ID));
                Intent intent = new Intent(ListaActivity.this, CadastroActivity.class);
                intent.putExtra("codigo", codigo);
                startActivity(intent);
                return true;
            }
        });

        //quando o botão é pressionado por algum tempo é aberta a classe MainActivity e é enviado o ID do item pressionado para que lá seja feita a busca e setados os campos
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ID));
                Intent intent = new Intent(ListaActivity.this, MainActivity.class);
                intent.putExtra("codigo", codigo);
                startActivity(intent);

            }
        });


    }
}
