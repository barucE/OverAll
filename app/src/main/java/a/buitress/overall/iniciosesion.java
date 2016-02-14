package a.buitress.overall;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class iniciosesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(iniciosesion.this, registro.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_iniciosesion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void iniciarSesion(View v) throws ExecutionException, InterruptedException {
        EditText correo = (EditText)findViewById(R.id.correo2);
        EditText contraseña = (EditText)findViewById(R.id.contraseña2);

        /*
        DBHelper baseDatos = new DBHelper(this);
        try {

            if (baseDatos.inicioSesion(correo.getText().toString(), contraseña.getText().toString())) {
                Toast.makeText(iniciosesion.this, "Exito al iniciar", Toast.LENGTH_LONG).show();

                //*********ESCRIBIR AQUÍ EL CÓDIGO PARA LANZAR LA SIGUIENTE PANTALLA (MAPA)***********

            } else {
                //mensaje error
                Toast.makeText(iniciosesion.this, "Datos incorrectos", Toast.LENGTH_LONG).show();
            }
        } catch (SQLiteException e) {
            Toast.makeText(iniciosesion.this, "Error en la base de datos", Toast.LENGTH_LONG).show();
        }*/

        IniciarSesionTask inicio = new IniciarSesionTask();

        inicio.execute(correo.getText().toString(), contraseña.getText().toString());

        String result = inicio.get();

        if(result.equals("1")) {
            startActivity(new Intent(iniciosesion.this, nextlogin.class));
        }
        else {
            Toast.makeText(iniciosesion.this, result, Toast.LENGTH_LONG).show();
        }
    }
}
