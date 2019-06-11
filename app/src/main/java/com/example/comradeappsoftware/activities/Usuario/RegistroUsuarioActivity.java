package com.example.comradeappsoftware.activities.Usuario;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comradeappsoftware.R;
import com.example.comradeappsoftware.activities.LoginMainActivity;
import com.example.comradeappsoftware.activities.MenuMainActivity;
import com.example.comradeappsoftware.models.Login;
import com.example.comradeappsoftware.models.User;
import com.example.comradeappsoftware.services.ApiService;
import com.example.comradeappsoftware.services.ApiServiceGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroUsuarioActivity extends AppCompatActivity {

    private static final String TAG = RegistroUsuarioActivity.class.getSimpleName();
    private ApiService service;
    private Activity activity;
    private EditText editText_nombres, editText_apellidos, editText_email,
            editText_password,editText_password_repit, editText_fecregistro, editText_dni, editText_idDistrito;

    private String st_dni,st_nombres,st_apellido, st_email, st_password,st_password_repit, st_fecregistro, st_idDistrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        service = ApiServiceGenerator.createService(ApiService.class);
        editText_nombres    = findViewById(R.id.etxt_nombre);
        editText_apellidos    = findViewById(R.id.etxt_apellido);
        editText_email    = findViewById(R.id.etxt_correo);
        editText_password    = findViewById(R.id.etxt_contraseña);
        editText_password_repit    = findViewById(R.id.etxt_contraseña_repit);
        editText_dni    = findViewById(R.id.etxt_dni);


    }

    public void RegistrarUsuario(View view){

        try {
            st_nombres = editText_nombres.getText().toString();
            st_apellido = editText_apellidos.getText().toString();
            st_dni = editText_dni.getText().toString();
            st_email = editText_email.getText().toString();
            st_password = editText_password.getText().toString();
            st_password_repit = editText_password_repit.getText().toString();

        }catch (Exception e){
            e.printStackTrace();
        }
if (st_password.equals(st_password_repit)){

   String fecha =  new SimpleDateFormat("yyyyMMdd", Locale.US).format(Calendar.getInstance().getTime());
    Call<User> call = service.createUsuario(st_apellido,st_nombres,st_email,st_password,fecha,1,st_dni);


    call.enqueue(new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            try {
                int statusCode = response.code();
                Log.e(TAG, "HTTP status code : " + statusCode);

                //ResponseMessage responseMessage2 = response.body();
                Log.i(TAG, "Response Message : " + response.message());

                if (response.isSuccessful()) {

                    User responseMessage = response.body();
/*
                        int_userId = responseMessage.getId();
                        st_username = responseMessage.getUsername();*/

                    Toast.makeText(RegistroUsuarioActivity.this, "Registro completo...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistroUsuarioActivity.this, LoginMainActivity.class));
                    finish();
                }
            } catch (Exception e) {
                Toast.makeText(RegistroUsuarioActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onThrowable: " + e.toString(), e);
            }
        }

        @Override
        public void onFailure(Call call, Throwable t) {
            Toast.makeText(RegistroUsuarioActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, t.getMessage());
        }
    });
}else{
    Toast.makeText(RegistroUsuarioActivity.this, "Contraseñas no cinciden", Toast.LENGTH_SHORT).show();



}

    }
}
