package mz.co.insystems.mobicare.activities.farmacia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.databinding.FarmacoCardBinding;
import mz.co.insystems.mobicare.model.farmaco.Farmaco;

public class FarmacoAdapter extends RecyclerView.Adapter<FarmacoAdapter.FarmacoViewHolder> {

    List<Farmaco> farmacos;
    private Context mContext;

    public FarmacoAdapter(Context context, List<Farmaco> farmacos) {
        this.farmacos = farmacos;
        this.mContext = context;
    }

    @NonNull
    @Override
    public FarmacoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FarmacoCardBinding farmacoCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.farmaco_card, parent, false);
        return new FarmacoViewHolder(farmacoCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmacoViewHolder holder, int position) {
        Farmaco currentFarmaco = farmacos.get(position);
        holder.farmacoCardBinding.setFarmaco(currentFarmaco);

        holder.farmacoCardBinding.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.farmacoCardBinding.overflow);
            }
        });
    }

    public void setFarmacos(List<Farmaco> farmacos) {
        this.farmacos = farmacos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class FarmacoViewHolder extends RecyclerView.ViewHolder{
        private FarmacoCardBinding farmacoCardBinding;

        public FarmacoViewHolder(FarmacoCardBinding farmacoCardBinding) {
            super(farmacoCardBinding.getRoot());

            this.farmacoCardBinding = farmacoCardBinding;
        }
    }

    private void showPopupMenu(View view){
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.farmaco_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
