package com.example.aula7.quizdosi028114;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aula7.quizdosi028114.Models.User;
import com.example.aula7.quizdosi028114.Parser.JsonUser;
import com.example.aula7.quizdosi028114.URL.HttpManager;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;

    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.bt_data);
        textView = (TextView) findViewById(R.id.id_data);
    }

    public Boolean isOnLine() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    public void LoadData(View view)
    {
        if (isOnLine()) {
            UserTask userTask = new UserTask();
            userTask.execute("https://jsonplaceholder.typicode.com/users");

        } else {
            Toast.makeText(this, "Sin conexion", Toast.LENGTH_SHORT).show();
        }
    }


    public void proccesData() {
        for (User usr : userList) {
            textView.append((usr.getName() + "\n"));
            textView.append((usr.getUsername() + "\n"));
            textView.append((usr.getEmail() + "\n"));
            textView.append((usr.getStreet() + "\n"));

        }
    }

        public class UserTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    userList = JsonUser.getData(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                proccesData();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {
                String content = null;
                try {
                    content = HttpManager.getDataJson(strings[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return  content;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);

            }
    }
}
