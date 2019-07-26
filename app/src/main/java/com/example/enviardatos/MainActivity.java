package com.example.enviardatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et1,et2,et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText) findViewById(R.id.ET1);
        et2 = (EditText) findViewById(R.id.ET2);
        et3 = (EditText) findViewById(R.id.ET3);

    }
    public void registro(View view){

        AdminSQLite admin = new AdminSQLite( this, "administrador", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String val1 = et1.getText().toString();
        String val2 = et2.getText().toString();
        String val3 = et3.getText().toString();

        if (!val1.isEmpty() && !val2.isEmpty() && !val3.isEmpty()){

        ContentValues registro = new ContentValues();

        registro.put("control",val1);
        registro.put("nombre",val2);
        registro.put("apellido",val3);

        BaseDeDatos.insert("alumno", null,registro);
        BaseDeDatos.close();

        et1.setText("");
        et2.setText("");
        et3.setText("");


        }else {
            if (val1.isEmpty())
                et1.setError("Ingrese campo");
            if (val2.isEmpty())
                et2.setError("Ingrese campo");
            if (val3.isEmpty())
                et3.setError("Ingrese campo");
        }
    }

    public void buscar(View view){

        AdminSQLite admin = new AdminSQLite( this, "administrador", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String control = et1.getText().toString();

        if (!control.isEmpty()){

        Cursor fila = BaseDeDatos.rawQuery("select nombre, apellido from alumno where control =" +control, null);

         if (fila.moveToFirst()){

                et2.setText(fila.getString(0));
                et3.setText(fila.getString(1));

        BaseDeDatos.close();

    }
         else {
             Toast.makeText(this, "Numero de control no encontrado", Toast.LENGTH_LONG).show();
             et1.setText("");

         }

        }

        else {
            et1.setError("Ingresa numero de control");
            Toast.makeText(this, "Ingresa control", Toast.LENGTH_LONG).show();
        }

}

public void borrar(View view){

    AdminSQLite admin = new AdminSQLite( this, "administrador", null, 1);
    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

    String val1 = et1.getText().toString();


    if (BaseDeDatos!=null){
        BaseDeDatos.execSQL("DELETE FROM alumno WHERE control='"+val1+"' ");
        BaseDeDatos.close();
    }
    Toast.makeText(this, "Se elimino correctamente", Toast.LENGTH_LONG).show();

}

public void modificar(View view){

    AdminSQLite admin = new AdminSQLite( this, "administrador", null, 1);
    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

    String val1 = et1.getText().toString();
    String val2 = et2.getText().toString();
    String val3 = et3.getText().toString();

    if (BaseDeDatos!=null){
        BaseDeDatos.execSQL("UPDATE alumno SET nombre='"+val2+"', apellido='"+val3+"' WHERE control='"+val1+"' ");
        BaseDeDatos.close();
    }
    Toast.makeText(this, "Se modifico correctamente", Toast.LENGTH_LONG).show();

}


public void limpia(View view){

    et1.setText("");
    et2.setText("");
    et3.setText("");
}

}