package mz.co.insystems.service.ekumi.common;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.activities.search.OnLoadMoreListener;
import mz.co.insystems.service.ekumi.databinding.ItemLoadingBinding;
import mz.co.insystems.service.ekumi.databinding.SearchebleListRowBinding;
import mz.co.insystems.service.ekumi.model.search.Searchble;

/**
 * Created by Voloide Tamele on 4/17/2018.
 */

public class SearchResultAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Searchble> farmaciaList;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Activity activity;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public SearchResultAdaper(RecyclerView recyclerView, List<Searchble> farmaciaList, Activity activity) {
        this.farmaciaList = farmaciaList;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && linearLayoutManager.findLastCompletelyVisibleItemPosition() == farmaciaList.size() - 1) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return farmaciaList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchebleListRowBinding searchebleListRowBinding;
        ItemLoadingBinding itemLoadingBinding;

        if (viewType == VIEW_TYPE_ITEM) {
            searchebleListRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.searcheble_list_row, parent, false);
            return new FarmaciaViewHolder(searchebleListRowBinding);
        }else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemLoadingBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof FarmaciaViewHolder){
            Searchble currentFarmacia = farmaciaList.get(position);
            ((FarmaciaViewHolder) viewHolder).searchebleListRowBinding.setSearcheble(currentFarmacia);
        }else
        if (viewHolder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemCount() {
        return farmaciaList.size();
    }

    public void setLoaded() {
        isLoading = false;
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

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        ItemLoadingBinding itemLoadingBinding;

        public LoadingViewHolder(@NonNull ItemLoadingBinding itemLoadingBinding) {
            super(itemLoadingBinding.getRoot());
            this.itemLoadingBinding = itemLoadingBinding;
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }
}
