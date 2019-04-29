package cursoandroid.com.frangault;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cursoandroid.com.frangault.model.BancoController;
import cursoandroid.com.frangault.model.CriaBanco;

public class MainActivity extends AppCompatActivity {

    private Button btnListar;
    private Button btnCadastrar;
    private TextView detalheModelo;
    private TextView detalheAno;
    private TextView detalheMotor;
    private TextView descModelo;
    private TextView descAno;
    private TextView descMotor;
    private TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCadastrar = findViewById(R.id.btn_cadastrar);
        btnListar = findViewById(R.id.btn_listar);
        detalheModelo = findViewById(R.id.txt_detalhe_modelo);
        detalheAno = findViewById(R.id.txt_detalhe_ano);
        detalheMotor = findViewById(R.id.txt_detalhes_motor);
        descModelo = findViewById(R.id.txt_modelo_desc);
        descAno = findViewById(R.id.txt_ano_desc);
        descMotor = findViewById(R.id.txt_motor_desc);
        titulo = findViewById(R.id.txt_titulo_detalhe);

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListaActivity.class);
                startActivity(intent);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();

        if(intent.hasExtra("codigo")){
            String codigo = intent.getStringExtra("codigo");
            verificarExtras(codigo);
        }

    }
    public void verificarExtras(String codigo){

        BancoController crud =new BancoController(getBaseContext());
        Cursor cursor = crud.carregarDadoById(Integer.parseInt(codigo));
        detalheModelo.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.MODELO)));
        detalheAno.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ANO)));
        detalheMotor.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.MOTOR)));

        detalheModelo.setVisibility(View.VISIBLE);
        detalheAno.setVisibility(View.VISIBLE);
        detalheMotor.setVisibility(View.VISIBLE);
        descMotor.setVisibility(View.VISIBLE);
        descAno.setVisibility(View.VISIBLE);
        descModelo.setVisibility(View.VISIBLE);
        titulo.setVisibility(View.VISIBLE);
    }
}
