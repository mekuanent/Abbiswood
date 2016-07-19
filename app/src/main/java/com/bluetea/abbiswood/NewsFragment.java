package com.bluetea.abbiswood;


import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluetea.abbiswood.adapter.NewsAdapter;
import com.bluetea.abbiswood.helper.ApiClient;
import com.bluetea.abbiswood.helper.Commons;
import com.bluetea.abbiswood.helper.Connectivity;
import com.bluetea.abbiswood.vo.ListingVO;
import com.bluetea.abbiswood.vo.NewsVO;

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
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Connectivity.BASE_URL).build();
    ApiClient apiClient =
            restAdapter.create(ApiClient.class);

    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsFragment() {
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

    NewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.inject(this,rootView);

        ArrayList<NewsVO> newsVOs = new ArrayList<>();

        adapter = new NewsAdapter(getActivity(), newsVOs);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        refreshContent();

        return rootView;
    }

    public void refreshContent(){

        Callback<List<NewsVO>> callback = new Callback<List<NewsVO>>() {
            @Override
            public void success(List<NewsVO> newsVOs, Response response) {
                adapter.setList(newsVOs);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Commons.showErrorAlert(getActivity(), "Something bad happened, please check your internet connection");
            }
        };
        apiClient.listNews(callback);

    }

}
