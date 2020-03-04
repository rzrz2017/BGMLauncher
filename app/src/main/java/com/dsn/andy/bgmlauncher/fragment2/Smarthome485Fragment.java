package com.dsn.andy.bgmlauncher.fragment2;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.andy.music.COMDevice;
import com.dsn.andy.bgmlauncher.MainActivity2;
import com.dsn.andy.bgmlauncher.R;
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

public class Smarthome485Fragment extends PageFragment{

    public Smarthome485Fragment(){

    }

    @Override
    public void refresh() {

    }

    @Override
    public Smarthome485Fragment setActivity(MainActivity2 activity) {
        this.activity = activity;
        return this;
    }



    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.grid)
    GridView grid;

    private List<COMDevice> devices;
    private COMDeviceAdapter adapter;

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
        devices = new ArrayList();

        List<COMDevice> list = new DBOperator<COMDevice>(activity).query(new COMDevice(), null, null);
        devices.addAll(list);

        COMDevice addItem = new COMDevice();
        addItem.setId((long) -1);
        addItem.setDeviceType(-1);
        devices.add(addItem);

        adapter = new COMDeviceAdapter(activity, devices);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                COMDevice device = adapter.getItem(position);
                if (device.getId() == -1) { //add item
                    showCreateDeviceDialog();
                } else {
                    showDeviceDialog(device);
                }
            }
        });


    }

    /*
    弹出新建设备对话框
     */
    private void showCreateDeviceDialog() {

        new CreateCOMDeviceDialog(activity, R.style.dialog, new CreateCOMDeviceDialog.OnSubmitListener() {
            @Override
            public void onSubmit() {
                initData();
            }
        }).show();

    }

    /*
    弹出设备详情对话框
     */
    private void showDeviceDialog(COMDevice device) {
        new CreateCOMDeviceDialog(activity, R.style.dialog, new CreateCOMDeviceDialog.OnSubmitListener() {
            @Override
            public void onSubmit() {
                initData();
            }
        }).setCOMDevice(device).show();
    }


}
