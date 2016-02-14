package a.buitress.overall;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Marce on 12/12/2015.
 */
public class IniciarSesionTask extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... args) {

        HttpURLConnection UrlConnection = null;
        try {
            UrlConnection = (HttpURLConnection) new URL("http://overall.esy.es/overall/login.php").openConnection();


        String urlParameters  = "correo="+args[0] + "&" +
                "password="+args[1];

        UrlConnection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter( UrlConnection.getOutputStream());
        wr.write(urlParameters);
        wr.flush();


        InputStreamReader reader = new InputStreamReader(UrlConnection.getInputStream());
        BufferedReader buffer = new BufferedReader(reader);
        StringBuffer sbuffer= new StringBuffer();
        String s;
        while((s = buffer.readLine()) != null) {
            sbuffer.append(s);
        }

        return sbuffer.toString();

    } catch (IOException e) {
        e.printStackTrace();
        return e.getMessage();
    }
    }
}
