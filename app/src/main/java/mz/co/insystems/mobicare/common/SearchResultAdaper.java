package mz.co.insystems.mobicare.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.databinding.SearchebleListRowBinding;
import mz.co.insystems.mobicare.model.farmacia.Farmacia;
import mz.co.insystems.mobicare.model.search.Searchble;

/**
 * Created by Voloide Tamele on 4/17/2018.
 */

public class SearchResultAdaper extends RecyclerView.Adapter<SearchResultAdaper.FarmaciaViewHolder>{
    List<Searchble> farmaciaList;

    public SearchResultAdaper(List<Searchble> farmaciaList) {
        this.farmaciaList = farmaciaList;
    }

    @Override
    public FarmaciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        SearchebleListRowBinding searchebleListRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.searcheble_list_row, parent, false);

        return new FarmaciaViewHolder(searchebleListRowBinding);
    }

    @Override
    public void onBindViewHolder(FarmaciaViewHolder farmaciaViewHolder, int position) {
        Searchble currentFarmacia = farmaciaList.get(position);
        farmaciaViewHolder.searchebleListRowBinding.setSearcheble(currentFarmacia);
    }

    @Override
    public int getItemCount() {
        return farmaciaList.size();
    }

    public void setFarmaciaList(List<Searchble> farmaciaList) {
        this.farmaciaList = farmaciaList;
        notifyDataSetChanged();
    }

    public class FarmaciaViewHolder extends RecyclerView.ViewHolder{

        private SearchebleListRowBinding searchebleListRowBinding;


        public FarmaciaViewHolder(@NonNull SearchebleListRowBinding searchebleListRowBinding) {
            super(searchebleListRowBinding.getRoot());
            this.searchebleListRowBinding = searchebleListRowBinding;
        }
    }
}
