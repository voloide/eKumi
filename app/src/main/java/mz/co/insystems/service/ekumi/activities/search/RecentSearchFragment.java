package mz.co.insystems.service.ekumi.activities.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.List;

import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.databinding.FragmentFavoritesBinding;
import mz.co.insystems.service.ekumi.model.search.RecentSearch;
import mz.co.insystems.service.ekumi.model.user.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentSearchFragment extends Fragment {

    private List<RecentSearch> recentSearchList;

    public RecentSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        FragmentFavoritesBinding fragmentFavoritesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);
        // Inflate the layout for this fragment
        return fragmentFavoritesBinding.getRoot();
    }

    public void loadUserRecents(User user) {
        //recentSearchList = ((SearchActivity) getActivity()).getRecentRearhDao().getAllOfUser(user);
    }
}
