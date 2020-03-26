package mz.co.insystems.mobicare.activities.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.co.insystems.mobicare.R;
import mz.co.insystems.mobicare.model.search.RecentSearch;
import mz.co.insystems.mobicare.model.user.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentSearchFragment extends Fragment {

    private List<RecentSearch> recentSearchList;

    public RecentSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    public void loadUserRecents(User user) {
        if (recentSearchList == null) recentSearchList = new ArrayList<>();
        try {
            recentSearchList = ((SearchActivity) getActivity()).getRecentRearhDao().getAllOfUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
