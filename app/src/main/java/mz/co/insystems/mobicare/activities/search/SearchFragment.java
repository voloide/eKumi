package mz.co.insystems.mobicare.activities.search;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.activities.farmacia.FarmaciaActivity;
import mz.co.insystems.mobicare.common.RecyclerTouchListener;
import mz.co.insystems.mobicare.common.SearchResultAdaper;
import mz.co.insystems.mobicare.databinding.FragmentSearchBinding;
import mz.co.insystems.mobicare.model.farmacia.Farmacia;
import mz.co.insystems.mobicare.model.farmaco.Farmaco;
import mz.co.insystems.mobicare.model.search.Searchble;
import mz.co.insystems.mobicare.model.servico.Servico;
import mz.co.insystems.mobicare.model.user.User;
import mz.co.insystems.mobicare.util.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private List<Searchble> searchbleList;
    private RecyclerView recyclerView;
    private SearchResultAdaper searchResultAdaper;
    private View view;
    private List<Farmacia> farmaciaList;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSearchBinding fragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment
        this.recyclerView = fragmentSearchBinding.vewFarmacias;

        this.searchbleList = ((SearchActivity)getActivity()).getSearchbleList();
        //displaySearchResults();
        return fragmentSearchBinding.getRoot();
    }

    public void notifyDataHasChanged(){
        this.searchbleList = ((SearchActivity)getActivity()).getSearchbleList();
        displaySearchResults();
    }

    private void displaySearchResults() {
        if (Utilities.listHasElements(this.searchbleList)){

            searchResultAdaper = new SearchResultAdaper(this.searchbleList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(searchResultAdaper);

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Farmaco searchble = (Farmaco) searchbleList.get(position);


                    //Farmacia relatedFarmacia = searchble.getFarmacia();

                    //relatedFarmacia.setContacto(null);

                    //List<Farmaco> farmacList = new ArrayList<>();
                    //farmacList.add(searchble);

                    //relatedFarmacia.setFarmacos(null);

                    Bundle bundle = new Bundle();
                    bundle.putLong(Farmaco.COLUMN_FARMACO_ID, searchble.getId());
                    bundle.putLong(Farmacia.COLUMN_FARMACIA_ID, searchble.getFarmacia().getId());
                    bundle.putSerializable(User.TABLE_NAME, ((SearchActivity)getActivity()).getCurrentUser());


                    Intent intent = new Intent(getContext(), FarmaciaActivity.class);
                    intent.putExtras(bundle);

                    startActivity(intent);
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
