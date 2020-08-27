package mz.co.insystems.service.ekumi.activities.search;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import mz.co.insystems.service.ekumi.R;
import mz.co.insystems.service.ekumi.databinding.FragmentSavedSearchBinding;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SavedSearch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class SavedSearch extends Fragment {

    public SavedSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSavedSearchBinding fragmentSavedSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_search, container, false);
        // Inflate the layout for this fragment
        return fragmentSavedSearchBinding.getRoot();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
