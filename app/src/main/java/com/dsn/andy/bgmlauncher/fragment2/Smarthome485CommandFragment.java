package com.dsn.andy.bgmlauncher.fragment2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.music.COMCommand;
import com.andy.music.COMDevice;
import com.dsn.andy.bgmlauncher.DSNApplication;
import com.dsn.andy.bgmlauncher.MainActivity2;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.com485.COMCommandImgAdapter;
import com.dsn.andy.bgmlauncher.com485.COMDeviceAdapter;
import com.dsn.andy.bgmlauncher.com485.CreateCOMDeviceDialog;
import com.dsn.andy.bgmlauncher.db2.DBOperator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/10/8.
 */

public class Smarthome485CommandFragment extends PageFragment{

    public Smarthome485CommandFragment(){

    }

    @Override
    public void refresh() {

    }

    @Override
    public Smarthome485CommandFragment setActivity(MainActivity2 activity) {
        this.activity = activity;
        return this;
    }



    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.grid)
    GridView grid;

    private List<COMCommand> commands;
    private COMCommandImgAdapter adapter;

    View layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.activity_smarthome485, null);

        ButterKnife.bind(this, layout);

        initData();
        return layout;
    }



    public void initData() {
        if(grid == null) return;
        commands = new ArrayList();

        List<COMCommand> list = new DBOperator<COMCommand>(activity).query(new COMCommand(), null, null);
        commands.addAll(list);



        adapter = new COMCommandImgAdapter(activity, commands);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                COMCommand command = adapter.getItem(position);
                DSNApplication.getInstance().sendSerialPortData(command.getCommandBytes());
                Toast.makeText(activity,command.getCommandName(),Toast.LENGTH_LONG).show();
            }
        });


    }


}
