package cursoandroid.com.frangault.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import cursoandroid.com.frangault.R;

public class LoginActivity extends AppCompatActivity {

    //variáveis da classe LoginActivity
    private TextView login;
    private TextView senha;
    private Button entrar;
    private CheckBox gravarDados;
    private static final String PREFERENCIAS = "Preferencias";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //define as variáveis pelo ID
        login = findViewById(R.id.edit_login);
        senha = findViewById(R.id.edit_senha);
        gravarDados = findViewById(R.id.checkBox);
        entrar = findViewById(R.id.btn_entrar);


        conferirPreferencias();

        /*método para fazer login e senha. Ainda não foi implementado uma tabela e nem uma classe de usuários
          por isso o login e a senha foram definidas diretamente no método. Também insere no arquivo
          "preferencias" o login e a senha do usuário se ele marcou o campo "Salvar dados"
          e por fim se o campo "Salvar dados" foi desmarcado os campos login e senha são apagados do arquivo
        */
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String log = login.getText().toString();
                String sen = senha.getText().toString();

                if(gravarDados.isChecked()) {
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", login.getText().toString());
                    editor.putString("senha", senha.getText().toString());
                    editor.apply();
                }else{     //caso seja desmarcado o checkbox limpa os registros no arquivo ArqPref
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                }

                if(log.equals("login") && sen.equals("senha")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Login ou senha errados", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /*confere se no arquivo "Preferencias" contém os valores de login e senha, se for verdadeiro
      define os valores dos campos login e senha com os dados de preferência e também define como
      checado o campo "Salvar dados"
    */
    public void conferirPreferencias(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS, 0);
        if(sharedPreferences.contains("login") && sharedPreferences.contains("senha")){
            login.setText(sharedPreferences.getString("login", ""));
            senha.setText((sharedPreferences.getString("senha", "")));
            gravarDados.setChecked(true);
        }
    }
}
