package cursoandroid.com.frangault.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cursoandroid.com.frangault.R;
import cursoandroid.com.frangault.controller.BancoController;
import cursoandroid.com.frangault.model.Carro;
import cursoandroid.com.frangault.controller.CriaBanco;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtModelo;
    private EditText edtAno;
    private EditText edtMotor;
    private TextView txtTitulo;
    private Button btnEnviar;
    private ImageView imgFoto;
    private Button btnAlterar;
    private Button btnExcluir;
    private Carro carro = new Carro();
    private String codigo = "";
    private ImageView imgCamera;
    //private File caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //variáveis
        edtModelo = findViewById(R.id.edit_modelo);
        edtAno = findViewById(R.id.edit_ano);
        edtMotor = findViewById(R.id.edit_motor);
        btnEnviar = findViewById(R.id.btn_enviar);
        btnAlterar = findViewById(R.id.btn_alterar);
        btnExcluir = findViewById(R.id.btn_excluir);
        imgCamera = findViewById(R.id.img_camera);
        txtTitulo = findViewById(R.id.txt_titulo);

        //recebe os extras enviados por outra activity
        Intent intent = getIntent();


            /*se nos extras tiver um com o nome "codigo" executa o método verificaExtras passando por parâmetro o ID, define como visíveis os campos
              que foram definidos como invisíveis por padrão e altera o título da activity para "Alterar registro"
            */
            if (intent.hasExtra("codigo")) {
                codigo = intent.getStringExtra("codigo");
                verificarExtras(codigo);
                btnEnviar.setVisibility(View.INVISIBLE);
                btnExcluir.setVisibility(View.VISIBLE);
                btnAlterar.setVisibility(View.VISIBLE);
                imgCamera.setVisibility(View.INVISIBLE);
                txtTitulo.setText("Alterar registro");


                btnAlterar = findViewById(R.id.btn_alterar);
                final BancoController crud = new BancoController(getBaseContext());
                Cursor cursor = crud.carregarDadoById(Integer.parseInt(codigo));

                //caso entre na condição acima esse botão se torna visível e altera os dados de acordo com o que foi digitado nos campos
                btnAlterar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String modeloAlt = edtModelo.getText().toString();
                        final String motorAlt = edtMotor.getText().toString();
                        final int idAlt = Integer.parseInt(codigo);
                        final int anoAlt = Integer.parseInt(edtAno.getText().toString());

                        //abre uma caixa de diálogo para perguntar se o usuário tem certeza que quer alterar o arquivo e abre a ListaActivity se o registro for alterado
                        new android.support.v7.app.AlertDialog.Builder(CadastroActivity.this)
                                .setTitle("Alterar registro")
                                .setMessage("Deseja realmente alterar esse registro?")
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        crud.alterarRegistro(idAlt, modeloAlt, anoAlt, motorAlt);

                                        Toast.makeText(CadastroActivity.this, "Registro alterado com sucesso", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CadastroActivity.this, ListaActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton("Não", null).show();
                    }
                });

                //caso entre na condição acima esse botão se torna visível e deleta os dados do banco de dados
                btnExcluir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //abre uma caixa de diálogo para perguntar se o usuário quer apagar o arquivo
                        new android.support.v7.app.AlertDialog.Builder(CadastroActivity.this)
                                .setTitle("Excluir registro")
                                .setMessage("Deseja excluir o registro?")
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        /*abre uma outra caixa de diálogo para perguntar se o usuário tem certeza que quer apagar o arquivo
                                          e alerta que os dados serão apagados definitivamente
                                        */
                                        new android.support.v7.app.AlertDialog.Builder(CadastroActivity.this)
                                                .setTitle("Confirmação de exclusão")
                                                .setMessage("Os dados serão excluídos do banco de dados definitivamente, deseja realmente continuar?")
                                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                                                    //caso o usuário responda sim às duas perguntas o arquivo é deletado do banco de dados
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        crud.deletar(Integer.parseInt(codigo));
                                                        Toast.makeText(CadastroActivity.this, "O registro foi excluido com sucesso!", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(CadastroActivity.this, ListaActivity.class);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .setNegativeButton("Não", null).show();
                                    }
                                })
                                .setNegativeButton("Não", null).show();
                    }
                });
            }
            //se o aplicativo não tiver permissão de acessar a câmera do dispositivo é feita uma requisição de permissão ao usuário
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
            }

            //ao clicar na imagem da câmera o método tirarFoto() é executado
            imgCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tirarFoto();
                }
            });

            //botão que salva os dados no banco e redireciona para a activity ListaActivity
            btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //verifica se todos os campos foram preenchidos
                    if (!edtModelo.getText().toString().isEmpty() && !edtAno.getText().toString().isEmpty() && !edtMotor.getText().toString().isEmpty()) {
                        BancoController crud = new BancoController(getBaseContext());
                        carro.setModelo(edtModelo.getText().toString());
                        verificarPrecoCarro(carro.getModelo());

                            //verifica se o campo "Modelo" foi preenchido com um modelo válido
                            if(carro.getModelo().equals("Crio") || carro.getModelo().equals("Mengane") || carro.getModelo().equals("Cu-id")) {
                                carro.setAno(Integer.parseInt(edtAno.getText().toString()));
                                carro.setMotor(edtMotor.getText().toString());
                                String resultado;
                                resultado = crud.insereDado(carro.getModelo(), carro.getAno(), carro.getMotor());

                                Log.i("Teste", resultado);
                                Toast.makeText(getApplicationContext(), "Dados inseridos com sucesso", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(CadastroActivity.this, ListaActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                limparDados(); // se o modelo não for válido esse método é chamado
                            }
                    } else { //se os campos não foram preenchidos dispara uma mensagem ao usuário
                        Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    //método para definir o preço dos carros *Esse método não está sendo útil ainda!!!
    public void verificarPrecoCarro(String c){

        if(c.equals("Crio")){
            carro.setPreco(33000.00);
        }else if(c.equals("Cu-id")){
            carro.setPreco(33500.00);
        }else if(c.equals("Mengane")){
            carro.setPreco(52000.00);
        }
    }

    //método para abrir a câmera *Tentativa sem sucesso de salvar a foto no SD, pegar o caminho e salvar o caminho no banco
    public void tirarFoto(){
        //String nomeFoto = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format( new Date());
        /*caminhoFoto = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nomeFoto
        );*/

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminhoFoto));
        startActivityForResult(intent, 0);
    }

    //Quando a foto é tirada é colocada no ImageView imgFoto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){

            imgFoto = findViewById(R.id.img_foto);

            Bundle extras =data.getExtras();
            Bitmap imagem = (Bitmap)extras.get("data");
            imgFoto.setImageBitmap(imagem);
        }
    }
    /*public void salvarFoto(){
        if(caminhoFoto != null && caminhoFoto.exists()){
            try{
                MediaStore.Images.Media.insertImage(
                    getContentResolver(), caminhoFoto.getAbsolutePath(),
                    caminhoFoto.getName(), "" );
                Toast.makeText(getApplicationContext(), "Foto inserida", Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                Log.i("Teste", "foto não foi carregada");
            }
        }
    }*/

    //método que recebe os extras, define os textos dos campos com os valores recebidos e define o botão "Alterar" como visível se tiver extras
    public void verificarExtras(String codigo){

            BancoController crud =new BancoController(getBaseContext());
            Cursor cursor = crud.carregarDadoById(Integer.parseInt(codigo));
            edtModelo.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.MODELO)));
            edtAno.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ANO)));
            edtMotor.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.MOTOR)));

            btnAlterar.setVisibility(View.VISIBLE);
    }

    //limpa os campos de texto, coloca o foco no campo "Modelo" e dispara uma mensagem para o usuário
    public void limparDados(){
        edtModelo.setText("");
        edtAno.setText("");
        edtMotor.setText("");
        edtModelo.requestFocus();
        Toast.makeText(CadastroActivity.this, "Modelo inválido", Toast.LENGTH_SHORT).show();
    }



    /*public void converterImagem(Bitmap imagem){
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.PNG, 100, saida);
        byte[] img = saida.toByteArray();
        carro.setFoto(img);
    }*/
}
