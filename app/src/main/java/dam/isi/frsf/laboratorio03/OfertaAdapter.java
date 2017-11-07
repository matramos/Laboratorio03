package dam.isi.frsf.laboratorio03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class OfertaAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Trabajo> items;

    public OfertaAdapter(Context context, List<Trabajo> items){
        super();
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Trabajo getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getId();
    }

    @Override
    public View getView(int i, View recicledView, ViewGroup viewGroup) {
        View row = recicledView;
        if(row == null){
            row = inflater.inflate(R.layout.item_oferta, viewGroup, false);
        }
        OfertaHolder holder = (OfertaHolder) row.getTag();
        if(holder == null){
            holder = new OfertaHolder(row);
            row.setTag(holder);
        }
        holder.cargarDatos(getItem(i));
        return row;
    }
}