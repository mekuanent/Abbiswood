package com.bluetea.abbiswood;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bluetea.abbiswood.adapter.ListingsAdapter;
import com.bluetea.abbiswood.helper.Commons;
import com.bluetea.abbiswood.helper.Connectivity;
import com.bluetea.abbiswood.helper.ApiClient;
import com.bluetea.abbiswood.vo.ListingVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VotedListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VotedListFragment extends Fragment {
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

    public static VotedListFragment newInstance(String param1, String param2) {
        VotedListFragment fragment = new VotedListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public VotedListFragment() {
        // Required empty public constructor
    }

    boolean voting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            voting = mParam1.equals("voted");
        }
    }

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.btnFirst)
    Button btnLeft;

    @InjectView(R.id.btnSecond)
    Button btnCenter;

    @InjectView(R.id.btnThird)
    Button btnRight;

    @OnClick(R.id.btnFirst)
    void leftClicked(){
        refreshContent(0L,voting);
    }
    @OnClick(R.id.btnSecond)
    void centerClicked(){
        refreshContent(1L,voting);
    }
    @OnClick(R.id.btnThird)
    void rightClicked(){
        refreshContent(2L,voting);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_voted_list, container, false);
        ButterKnife.inject(this, rootView);

        ArrayList<ListingVO> listingVOs = new ArrayList<>();

        if(voting){
            btnLeft.setText("Best Film");
            btnCenter.setText("Best Actor");
            btnRight.setText("Best Actress");
        }else{
            btnLeft.setText("Box Office");
            btnCenter.setText("Best Cinema");
            btnRight.setText("Coming Soon");
        }

        adapter = new ListingsAdapter(this, voting, listingVOs);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setAdapter(adapter);
        refreshContent(0L,voting);
        return rootView;
    }

    ListingsAdapter adapter;

    public void refreshContent(final Long id, boolean voted){

        Callback<List<ListingVO>> callback = new Callback<List<ListingVO>>() {
            @Override
            public void success(List<ListingVO> listingVOs, Response response) {

                adapter.setListings(listingVOs);
                adapter.setType(id);
                boolean canVote = true;
                for(ListingVO listingVO: listingVOs){
                    if(listingVO.hasVoted)
                        canVote = false;
                }
                adapter.setCanVote(canVote);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void failure(RetrofitError error) {
                Commons.showErrorAlert(getActivity(), "Something bad happened, please check your internet connection");
            }
        };

        if(voted)
            apiClient.listVoted(id, Commons.getDeviceCode(getActivity()), callback);
        else
            apiClient.listNonVoted(id, callback);
    }


}
