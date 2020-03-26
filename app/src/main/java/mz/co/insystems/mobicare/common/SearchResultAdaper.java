package mz.co.insystems.mobicare.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.model.search.Searchble;
import mz.co.insystems.mobicare.util.Utilities;

/**
 * Created by Voloide Tamele on 4/17/2018.
 */

public class SearchResultAdaper extends RecyclerView.Adapter<SearchResultAdaper.MyViewHolder>{
    List<Searchble> searchbleList;

    public SearchResultAdaper(List<Searchble> searchbleList) {
        this.searchbleList = searchbleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searcheble_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Searchble object = searchbleList.get(position);
        holder.nome.setText(object.getDescricao());
        holder.location.setText(object.getEndereco().toString());
        //holder.available.setText(object.getDisponibilidade());
        if (object.getLogo() != null) holder.logoImage.setImageBitmap(Utilities.getImage(object.getLogo()));

    }

    @Override
    public int getItemCount() {
        return searchbleList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView nome, location, available;
        public ImageView logoImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.txv_nome);
            location = itemView.findViewById(R.id.txv_location);
            available = itemView.findViewById(R.id.txv_available);
            logoImage = itemView.findViewById(R.id.img_logo);
        }
    }
}
