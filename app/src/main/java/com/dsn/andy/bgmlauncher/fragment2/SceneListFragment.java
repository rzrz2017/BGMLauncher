package com.dsn.andy.bgmlauncher.fragment2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.MainActivity2;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.home.DsonConstants;
import com.dsn.andy.bgmlauncher.home.SceneAdapter;
import com.dsn.andy.bgmlauncher.home.SmarthomeHelper;
import com.dson.smart.common.entity.DsonSmartScene;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/9/21.
 */

public class SceneListFragment extends PageFragment {
    View layout;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.grid)
    GridView grid;

    SceneAdapter adapter;
    @Bind(R.id.btn_logout)
    Button btnLogout;

    public SceneListFragment() {

    }

    @Override
    public SceneListFragment setActivity(MainActivity2 activity){
        this.activity = activity;

        return this;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_device, null);

        Log.e("andy", "fragment oncreateview :SceneListFragment");
        ButterKnife.bind(this, layout);
        title.setText("智能情景");

        btnLogout.setVisibility(View.GONE);

        refresh();
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public synchronized void refresh() {
        if (DsonConstants.getInstance().getScenes() == null) {
            Log.e("andy","get scenes is null");
            return;
        }
        List<DsonSmartScene> data = DsonConstants.getInstance().getScenes().getScenes();
        Log.e("andy","SCENES DATA SIZE IS "+data.size());

        adapter = new SceneAdapter(getActivity(), data);
        grid.setAdapter(adapter);
        Log.e("andy","set adapter");
        grid.setOnItemClickListener(sceneItemListener);

    }


    AdapterView.OnItemClickListener sceneItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DsonSmartScene scene = adapter.getItem(position);
            openScene(scene);
        }
    };

    void openScene(DsonSmartScene scene) {
        SmarthomeHelper smarthome = SmarthomeHelper.getInstance(getActivity().getApplicationContext());
        smarthome.openScene(scene);
    }
}
