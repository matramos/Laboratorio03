package dam.isi.frsf.laboratorio03;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CrearOfertaActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    EditText etOferta, etHoras, etMaxPorHora;
    RadioButton rbDolar, rbEuro, rbLibra, rbPesoArg, rbReal;
    DatePicker dpFechaFin;
    CheckBox chkEnIngles;
    Spinner sCategoria;
    List<Categoria> categorias;
    ArrayAdapter<Categoria> adaptadorCategoria;
    RadioGroup radioGroup;
    int opcionSeleccionada;
    Button bGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_oferta);
        setParametros();

        sCategoria.setAdapter(adaptadorCategoria);

        bGuardar.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(this);
        rbDolar.setChecked(true);
        opcionSeleccionada = 1;
    }

    private void setParametros() {
        etOferta = (EditText) findViewById(R.id.etOferta);
        etHoras = (EditText) findViewById(R.id.etHoras);
        etMaxPorHora = (EditText) findViewById(R.id.etMaxPorHora);
        rbDolar = (RadioButton) findViewById(R.id.rbDolar);
        rbEuro = (RadioButton) findViewById(R.id.rbEuro);
        rbLibra = (RadioButton) findViewById(R.id.rbLibra);
        rbPesoArg = (RadioButton) findViewById(R.id.rbPesoArg);
        rbReal = (RadioButton) findViewById(R.id.rbReal);
        dpFechaFin = (DatePicker) findViewById(R.id.dpFechaFin);
        chkEnIngles = (CheckBox) findViewById(R.id.chkEnIngles);

        sCategoria = (Spinner) findViewById(R.id.sCategoria);
        categorias = Arrays.asList(Categoria.CATEGORIAS_MOCK);
        adaptadorCategoria = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, categorias);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        bGuardar = (Button) findViewById(R.id.bGuardar);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()){
            case R.id.radioGroup:
                switch(checkedId){
                    case R.id.rbDolar:
                        opcionSeleccionada = 1;
                        break;
                    case R.id.rbEuro:
                        opcionSeleccionada = 2;
                        break;
                    case R.id.rbPesoArg:
                        opcionSeleccionada = 3;
                        break;
                    case R.id.rbLibra:
                        opcionSeleccionada = 4;
                        break;
                    case R.id.rbReal:
                        opcionSeleccionada = 5;
                        break;
                    default:
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bGuardar:
                guardarOferta();
                break;
            default:
        }
    }

    private void guardarOferta() {
        String error = validarDatosOferta();

        if(error.isEmpty()){
            Trabajo trabajo = new Trabajo();
            trabajo.setId(Trabajo.getAndIncreaseId());
            trabajo.setDescripcion(etOferta.getText().toString().trim());
            trabajo.setCategoria((Categoria) sCategoria.getSelectedItem());
            trabajo.setFechaEntrega(getDateFromDatePicket(dpFechaFin));
            trabajo.setHorasPresupuestadas(Integer.parseInt(etHoras.getText().toString()));
            trabajo.setMonedaPago(opcionSeleccionada);
            trabajo.setPrecioMaximoHora(Double.parseDouble(etMaxPorHora.getText().toString().trim()));
            trabajo.setRequiereIngles(chkEnIngles.isChecked());

            Intent i = getIntent();
            // seteamos el resultado a enviar a la actividad principal.
            i.putExtra("resultado",trabajo);
            // invocamos al método de activity setResult
            setResult(RESULT_OK, i);
            // Finalizamos la Activity para volver a la anterior
            finish();
        }
        else{
            Toast.makeText(this,error,Toast.LENGTH_LONG).show();
        }
    }

    private String validarDatosOferta() {
        String error="";
        if(!validarNombreOferta(etOferta.getText().toString().trim())){
            error += getResources().getString(R.string.error_nombre_oferta);
        }
        if(!validarHoras(etHoras.getText().toString().trim())){
            if(!error.isEmpty()){
                error += "\n";
            }
            error += getResources().getString(R.string.error_horas);
        }
        if(!validarMaxPorHora(etMaxPorHora.getText().toString().trim())){
            if(!error.isEmpty()){
                error += "\n";
            }
            error += getResources().getString(R.string.error_max_por_hora);
        }
        return error;
    }

    private boolean validarNombreOferta(String nombreOferta){
        return !nombreOferta.isEmpty();
    }

    private boolean validarHoras(String horas){
        try {
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(horas);
        } catch(Exception e){
            return false;
        }
        return true;
    }

    private  boolean validarMaxPorHora(String maxPorHora){
        try {
            //noinspection ResultOfMethodCallIgnored
            Double.parseDouble(maxPorHora);
        } catch(Exception e){
            return false;
        }
        return true;
    }

    //Convertir DatePicker a date
    private Date getDateFromDatePicket(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("descripcion_oferta",etOferta.getText().toString());
        outState.putString("horas",etHoras.getText().toString());
        outState.putString("max_por_hora",etMaxPorHora.getText().toString());
        outState.putInt("categoria_posicion",sCategoria.getSelectedItemPosition());
        outState.putInt("fecha_dia",dpFechaFin.getDayOfMonth());
        outState.putInt("fecha_mes",dpFechaFin.getMonth());
        outState.putInt("fecha_anio",dpFechaFin.getYear());
        outState.putBoolean("us_seleccionado",rbDolar.isSelected());
        outState.putBoolean("eu_seleccionado",rbEuro.isSelected());
        outState.putBoolean("ar_seleccionado",rbPesoArg.isSelected());
        outState.putBoolean("uk_seleccionado",rbLibra.isSelected());
        outState.putBoolean("br_seleccionado",rbReal.isSelected());
        outState.putBoolean("en_ingles",chkEnIngles.isSelected());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        etOferta.setText(savedInstanceState.getString("descripcion_oferta"));
        etHoras.setText(savedInstanceState.getString("horas"));
        etMaxPorHora.setText(savedInstanceState.getString("max_por_hora"));
        sCategoria.setSelection(savedInstanceState.getInt("categoria_posicion"));
        dpFechaFin.updateDate(savedInstanceState.getInt("fecha_anio"),savedInstanceState.getInt("fecha_mes"),savedInstanceState.getInt("fecha_dia"));
        rbDolar.setSelected(savedInstanceState.getBoolean("us_seleccionado"));
        rbEuro.setSelected(savedInstanceState.getBoolean("eu_seleccionado"));
        rbPesoArg.setSelected(savedInstanceState.getBoolean("ar_seleccionado"));
        rbLibra.setSelected(savedInstanceState.getBoolean("uk_seleccionado"));
        rbReal.setSelected(savedInstanceState.getBoolean("br_seleccionado"));
        chkEnIngles.setSelected(savedInstanceState.getBoolean("en_ingles"));
    }
}