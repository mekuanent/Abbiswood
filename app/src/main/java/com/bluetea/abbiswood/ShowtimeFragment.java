package com.bluetea.abbiswood;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluetea.abbiswood.adapter.ShowtimeAdapter;
import com.bluetea.abbiswood.helper.Commons;
import com.bluetea.abbiswood.helper.Connectivity;
import com.bluetea.abbiswood.helper.ApiClient;
import com.bluetea.abbiswood.vo.ShowtimeVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowtimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowtimeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Connectivity.BASE_URL).build();
    ApiClient apiClient =
            restAdapter.create(ApiClient.class);

    public static ShowtimeFragment newInstance(String param1, String param2) {
        ShowtimeFragment fragment = new ShowtimeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ShowtimeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    ArrayList<ShowtimeVO> showtimeVOs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_showtime, container, false);
        ButterKnife.inject(this,rootView);

        List<ShowtimeVO> showtimeVOs = new ArrayList<>();

        adapter = new ShowtimeAdapter(getActivity(),showtimeVOs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setAdapter(adapter);

        refreshContent();

        return rootView;
    }
    ShowtimeAdapter adapter;
    public void refreshContent(){

        apiClient.listShowtime(new Callback<List<ShowtimeVO>>() {
            @Override
            public void success(List<ShowtimeVO> cinemaVOs, Response response) {
                adapter.setShowtime(cinemaVOs);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Commons.showErrorAlert(getActivity(), "Something bad happened, please check your internet connection");
            }
        });

    }

}
