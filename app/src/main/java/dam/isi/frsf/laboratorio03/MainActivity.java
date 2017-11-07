package dam.isi.frsf.laboratorio03;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView listaOfertas;
    private OfertaAdapter adaptadorListaOfertas;
    private List<Trabajo> ofertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarParametros();

        ofertas.addAll(Arrays.asList(Trabajo.TRABAJOS_MOCK));
        listaOfertas.setAdapter(adaptadorListaOfertas);
        registerForContextMenu(listaOfertas);
    }

    private void cargarParametros() {
        ofertas = new ArrayList<>();
        listaOfertas = (ListView) findViewById(R.id.listaOfertas);
        adaptadorListaOfertas = new OfertaAdapter(this, ofertas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCrearOferta:
                Intent intent = new Intent(this, CrearOfertaActivity.class);
                startActivityForResult(intent, 0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        switch (v.getId()) {
            case R.id.listaOfertas:
                menu.setHeaderTitle(getResources().getString(R.string.menu_titulo_acciones));
                inflater.inflate(R.menu.menu_oferta_main, menu);
                break;
            default:
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;

        switch (item.getItemId()) {
            case R.id.menuPostularseOferta:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Toast.makeText(this, getResources().getString(R.string.postulacion_registrada) + ": \"" + ofertas.get(info.position) + "\".", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuCompartirOferta:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Trabajo trabajoSeleccionado = ofertas.get(info.position);

                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");

                String monedaPago = "";
                switch (trabajoSeleccionado.getMonedaPago()) {
                    case 1:
                        monedaPago = "U$D";
                        break;
                    case 2:
                        monedaPago = "€";
                        break;
                    case 3:
                        monedaPago = "$";
                        break;
                    case 4:
                        monedaPago = "£";
                        break;
                    case 5:
                        monedaPago = "R$";
                        break;
                }
                String fecha = SimpleDateFormat.getDateInstance().format(trabajoSeleccionado.getFechaEntrega());

                String oferta = String.format(Locale.getDefault(), getResources().getString(R.string.compartir_oferta_texto), trabajoSeleccionado.getCategoria().getDescripcion(), trabajoSeleccionado.getDescripcion(), trabajoSeleccionado.getHorasPresupuestadas(), trabajoSeleccionado.getPrecioMaximoHora(), monedaPago, fecha);
                if(trabajoSeleccionado.getRequiereIngles()){
                    oferta += "\n"+getResources().getString(R.string.conocimiento_ingles);
                }
                intent.putExtra(Intent.EXTRA_TEXT, oferta);

                oferta = String.format(Locale.getDefault(), getResources().getString(R.string.compartir_oferta_titulo), trabajoSeleccionado.getCategoria().getDescripcion(), trabajoSeleccionado.getDescripcion());
                intent.putExtra(Intent.EXTRA_SUBJECT, oferta);

                startActivity(Intent.createChooser(intent, oferta));
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Trabajo trabajo = (Trabajo) data.getSerializableExtra("resultado");
            ofertas.add(trabajo);
            adaptadorListaOfertas.notifyDataSetChanged();
        }
    }
}
