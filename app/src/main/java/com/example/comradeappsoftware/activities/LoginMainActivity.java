package com.example.comradeappsoftware.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comradeappsoftware.R;
import com.example.comradeappsoftware.activities.Usuario.RegistroUsuarioActivity;
import com.example.comradeappsoftware.models.Login;
import com.example.comradeappsoftware.models.User;
import com.example.comradeappsoftware.services.ApiService;
import com.example.comradeappsoftware.services.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginMainActivity extends AppCompatActivity {

    private static final String TAG = LoginMainActivity.class.getSimpleName();

    private String token;
    private SharedPreferences sharedPreferences;
    private EditText editText_email,editText_pass ;
    private Button btn_ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editText_email = findViewById(R.id.etxt_email_l);
        editText_pass = findViewById(R.id.etxt_password_l);
        btn_ingresar = findViewById(R.id.btn_login);
    }

    public void Ingresar(View view){
     //   btn_ingresar.setEnabled(false);

        if(editText_email.getText().toString().length() < 1){
            editText_email.requestFocus();
            Toast.makeText(this, "Email no ingresado", Toast.LENGTH_SHORT).show();
            btn_ingresar.setEnabled(true);
            btn_ingresar.setTextColor(Color.LTGRAY);
            return;

        }else if(editText_pass.length() < 1){
            editText_pass.requestFocus();
            Toast.makeText(this, "Contraseña no ingresada", Toast.LENGTH_SHORT).show();
            btn_ingresar.setEnabled(true);
            btn_ingresar.setTextColor(Color.LTGRAY);
            return;
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Login login = new Login(
                editText_email.getText().toString(),
                editText_pass.getText().toString()
        );

        Call<User> call = service.login(login);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                int statusCode = response.code();
                Log.i(TAG, "HTTP status code : " + statusCode);
                Log.i(TAG, "Response Message : " + response.message());

                if (response.isSuccessful()) {

                    int id_usuario = response.body().getIdusuario();
                    String nombre = response.body().getNombres();
                    String apellido = response.body().getApellidos();
                    String email =  response.body().getEmail();
                    String fecha = response.body().getFec_registro();

                    Toast.makeText(LoginMainActivity.this, "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();

                    // Save to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor
                            .putString("email", email)
                            .putString("fechareg", fecha)
                            .putString("nombre", nombre)
                            .putString("apellidos", apellido)
                            .putInt("id_user", id_usuario)
                            .apply();


                        //  Toast.makeText(LoginMainActivity.this, "Sin permisos necesarios para ingresar", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginMainActivity.this, MenuMainActivity.class));
                        finish();


                } else {
                    Toast.makeText(LoginMainActivity.this, "Credenciales incorrectos", Toast.LENGTH_SHORT).show();
                }
                activarButtonDelay();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginMainActivity.this, "error", Toast.LENGTH_SHORT).show();
                activarButtonDelay();
            }
        });
    }

    public void activarButtonDelay(){
        try
        {
            Thread.sleep(1000);
            btn_ingresar.setEnabled(true);
            btn_ingresar.setTextColor(getResources().getColor(R.color.colorAltern));
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void GoRegisterUser(View view){
        startActivity(new Intent(LoginMainActivity.this, RegistroUsuarioActivity.class));
    }
}
