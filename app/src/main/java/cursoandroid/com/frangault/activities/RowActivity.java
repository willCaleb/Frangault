package cursoandroid.com.frangault.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cursoandroid.com.frangault.R;

public class RowActivity extends AppCompatActivity {

    //Essa activity apenas recebe os dados do banco numa lista onde os valores de elementos da interface
    //são definidos pelos valores buscados no banco
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row);
    }
}
