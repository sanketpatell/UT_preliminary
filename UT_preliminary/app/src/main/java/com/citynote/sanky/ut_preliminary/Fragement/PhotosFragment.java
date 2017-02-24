package com.citynote.sanky.ut_preliminary.Fragement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import com.citynote.sanky.ut_preliminary.Photos.Api;
import com.citynote.sanky.ut_preliminary.Photos.FullScreenActivity;
import com.citynote.sanky.ut_preliminary.Photos.ListAdapter;
import com.citynote.sanky.ut_preliminary.Photos.PrettyModel;
import com.citynote.sanky.ut_preliminary.R;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

public class PhotosFragment extends Fragment {


    GridView mGridView;
    ListAdapter mListAdapter;
    PrettyModel prettyModel;
    ProgressBar mProgressBar;
    View PhotosFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      PhotosFragmentView = inflater.inflate(R.layout.fragment_photos, container, false);

        loadPrettyList();

        return PhotosFragmentView;
    }

    private void initView(final PrettyModel prettyModel){

        mProgressBar = (ProgressBar) PhotosFragmentView.findViewById(R.id.progress_load_gallery);
        mProgressBar.setVisibility(View.VISIBLE);

        mGridView = (GridView) PhotosFragmentView.findViewById(R.id.gridView);
        mListAdapter = new ListAdapter(getActivity(),prettyModel);
        mGridView.setAdapter(mListAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url  = prettyModel.getResults().get(position).getUrl();
                // String name = prettyModel.getResults().get(position).getWho();
                Intent intent = new Intent(getActivity(), FullScreenActivity.class);
                intent.putExtra("url",url);
                //   intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }

    public void loadPrettyList(){
        Call<PrettyModel> prettyModelCall = Api.Iapi_loadList().getPrettyModel();
        prettyModelCall.enqueue(new Callback<PrettyModel>() {
            @Override
            public void onResponse(retrofit.Response<PrettyModel> response, Retrofit retrofit) {
                prettyModel = response.body();
                initView(prettyModel);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
