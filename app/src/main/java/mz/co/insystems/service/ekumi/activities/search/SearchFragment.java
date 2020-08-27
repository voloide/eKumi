package mz.co.insystems.service.ekumi.activities.search;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import mz.co.insystems.service.ekumi.activities.maps.FarmaciaMapsActivity;
import mz.co.insystems.service.ekumi.model.endereco.Endereco;
import mz.co.insystems.service.ekumi.util.Utilities;
import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.common.RecyclerTouchListener;
import mz.co.insystems.service.ekumi.common.SearchResultAdaper;
import mz.co.insystems.service.ekumi.databinding.FragmentSearchBinding;
import mz.co.insystems.service.ekumi.model.farmacia.Farmacia;
import mz.co.insystems.service.ekumi.model.farmaco.Farmaco;
import mz.co.insystems.service.ekumi.model.search.Searchble;
import mz.co.insystems.service.ekumi.model.user.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment{

    private List<Searchble> currentSearchedData;
    private List<Searchble> displayedData;
    private RecyclerView recyclerView;
    private SearchResultAdaper searchResultAdaper;

    boolean isLoading = false;
    private Utilities utilities;
    private ArrayList<Searchble> newSearchedData;
    private int dyspyQty;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSearchBinding fragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment
        utilities = Utilities.getInstance();

        this.dyspyQty = 6;

        this.recyclerView = fragmentSearchBinding.vewFarmacias;

        this.currentSearchedData = ((SearchActivity)getActivity()).getSearchbleList();

        this.displayedData = new ArrayList<>();

        displaySearchResults();

        return fragmentSearchBinding.getRoot();
    }

    public void notifyDataHasChanged(){
        this.currentSearchedData = getMyActivity().getSearchbleList();
        displayedData.clear();

        displaySearchResults();
    }

    private SearchActivity getMyActivity(){
        return (SearchActivity)getActivity();
    }

    private List<Searchble> getNextToDisplay(){
        List<Searchble> moreSearchbles = new ArrayList<>();
        int end;

        if ((displayedData.size()+dyspyQty) > this.currentSearchedData.size()){
            end = this.currentSearchedData.size() - 1;
        }else {
            end = displayedData.size()+dyspyQty-1;
        }

        for (int i = displayedData.size(); i <= end; i++){
            moreSearchbles.add(this.currentSearchedData.get(i));

        }
        return moreSearchbles;
    }

    private void displaySearchResults() {
        if (Utilities.listHasElements(this.currentSearchedData)){

            dyspyQty = 6;

            if (this.currentSearchedData.size() < dyspyQty){
                dyspyQty = this.currentSearchedData.size();
            }

            for (int i = 0; i <= dyspyQty-1; i++){
                displayedData.add(this.currentSearchedData.get(i));
            }

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            searchResultAdaper = new SearchResultAdaper(recyclerView, displayedData, getMyActivity());
            recyclerView.setAdapter(searchResultAdaper);

            searchResultAdaper.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (displayedData.size() < currentSearchedData.size()) {
                        displayedData.add(null);
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                searchResultAdaper.notifyItemInserted(displayedData.size() - 1);
                            }
                        });


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                displayedData.remove(displayedData.size() - 1);
                                searchResultAdaper.notifyItemRemoved(displayedData.size());

                                displayedData.addAll(getNextToDisplay());

                                searchResultAdaper.notifyDataSetChanged();
                                searchResultAdaper.setLoaded();
                            }
                        }, 5000);
                    }
                }


            });


            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Farmaco searchble = ((Farmaco) currentSearchedData.get(position)).clone();


                    Farmacia relatedFarmacia = searchble.getFarmacia();

                    searchble.setFarmacia(null);

                    Intent intent = new Intent(getActivity(), FarmaciaMapsActivity.class);
                    try {
                        intent.putExtra(Farmacia.TABLE_NAME_FARMACIA, relatedFarmacia.parseToJsonObject().toString());
                        intent.putExtra(Farmaco.TABLE_NAME_FARMACO, searchble.parseToJsonObject().toString());
                        intent.putExtra(User.TABLE_NAME, ((SearchActivity)getActivity()).getCurrentUser().parseToJsonObject().toString());
                        intent.putExtra(Endereco.COLUMN_ENDERECO_LATITUDE, ((SearchActivity)getActivity()).getLatitude());
                        intent.putExtra(Endereco.COLUMN_ENDERECO_LONGITUDE, ((SearchActivity)getActivity()).getLongitude());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    getActivity().getApplicationContext().startActivity(intent);

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

            searchResultAdaper.notifyDataSetChanged();
        }else {

        }
    }






}
