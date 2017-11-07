package dam.isi.frsf.laboratorio03;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class OfertaHolder implements View.OnLongClickListener{

    private static final DateFormat DATE_FORMATTER = SimpleDateFormat.getDateInstance();

    private TextView itemTvCategoria, itemTvNombreOferta, itemTvHoras, itemTvMaxPesoHora, itemTvFechaFin;
    private ImageView itemBandera;
    private CheckBox itemCbIngles;
    private View oferta;
    private Trabajo datos;

    public OfertaHolder(View oferta){
        itemTvCategoria = (TextView) oferta.findViewById(R.id.itemTvCategoria);
        itemTvNombreOferta = (TextView) oferta.findViewById(R.id.itemTvNombreOferta);
        itemTvHoras = (TextView) oferta.findViewById(R.id.itemTvHoras);
        itemTvMaxPesoHora = (TextView) oferta.findViewById(R.id.itemTvMaxPesoHora);
        itemTvFechaFin = (TextView) oferta.findViewById(R.id.itemTvFechaFin);
        itemBandera = (ImageView) oferta.findViewById(R.id.itemBandera);
        itemCbIngles = (CheckBox) oferta.findViewById(R.id.itemCbIngles);
        this.oferta = oferta;
        //Linea mágica
        oferta.setLongClickable(true);
        oferta.setOnLongClickListener(this);
    }

    public void cargarDatos(Trabajo datos) {
        this.datos = datos;
        itemTvCategoria.setText(datos.getCategoria().getDescripcion());
        itemTvNombreOferta.setText(datos.getDescripcion());
        String texto = oferta.getResources().getString(R.string.horas) + ": " + datos.getHorasPresupuestadas();
        itemTvHoras.setText(texto);
        texto = oferta.getResources().getString(R.string.max_pesos_hora) + ": " + String.format(Locale.getDefault(),"%.2f",datos.getPrecioMaximoHora());
        itemTvMaxPesoHora.setText(texto);
        texto = oferta.getResources().getString(R.string.fecha_fin) + ": " + DATE_FORMATTER.format(datos.getFechaEntrega());
        itemTvFechaFin.setText(texto);

        //Obtener moneda y setear bandera correspondiente
        int banderaId = 0;
        switch(datos.getMonedaPago()){
            case 1:
                banderaId = R.mipmap.ic_bandera_us;
                break;
            case 2:
                banderaId = R.mipmap.ic_bandera_eu;
                break;
            case 3:
                banderaId = R.mipmap.ic_bandera_ar;
                break;
            case 4:
                banderaId = R.mipmap.ic_bandera_uk;
                break;
            case 5:
                banderaId = R.mipmap.ic_bandera_br;
                break;
        }
        itemBandera.setImageResource(banderaId);

        itemCbIngles.setChecked(datos.getRequiereIngles());
    }

    @Override
    public boolean onLongClick(View view) {
        Toast.makeText(oferta.getContext(),datos.getDescripcion(),Toast.LENGTH_SHORT).show();
        return false;
    }
}