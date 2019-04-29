package cursoandroid.com.frangault;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import cursoandroid.com.frangault.model.BancoController;
import cursoandroid.com.frangault.model.CriaBanco;

public class ListaActivity extends AppCompatActivity {

    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Toast.makeText(ListaActivity.this, "Pressione um ítem para ver os detalhes ou mantenha pressionado para alterar os dados", Toast.LENGTH_LONG).show();
        BancoController crud = new BancoController(getBaseContext());
        final Cursor cursor= crud.carregaDados();

        String[] nomeCampos =new String[]{CriaBanco.MODELO, CriaBanco.ANO, CriaBanco.MOTOR};
        int[] idViews = new int[]{R.id.txt_modelo, R.id.txt_ano, R.id.txt_motor};

        SimpleCursorAdapter adaptador =new SimpleCursorAdapter(getBaseContext(), R.layout.activity_row, cursor, nomeCampos, idViews, 0);
        lista =(ListView)findViewById(R.id.listView);
        lista.setAdapter(adaptador);

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
