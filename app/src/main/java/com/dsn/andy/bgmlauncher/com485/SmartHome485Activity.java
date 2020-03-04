package com.dsn.andy.bgmlauncher.com485;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.andy.music.COMDevice;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.db2.DBOperator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/10/2.
 */

public class SmartHome485Activity extends Activity {


    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.grid)
    GridView grid;

    private List<COMDevice> devices;
    private COMDeviceAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smarthome485);
        ButterKnife.bind(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        initData();
    }

    private void initData() {
        devices = new ArrayList();

        List<COMDevice> list = new DBOperator<COMDevice>(this).query(new COMDevice(), null, null);
        devices.addAll(list);

        COMDevice addItem = new COMDevice();
        addItem.setId((long) -1);
        addItem.setDeviceType(-1);
        devices.add(addItem);

        adapter = new COMDeviceAdapter(this, devices);
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

        new CreateCOMDeviceDialog(this, R.style.dialog, new CreateCOMDeviceDialog.OnSubmitListener() {
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
        new CreateCOMDeviceDialog(this, R.style.dialog, new CreateCOMDeviceDialog.OnSubmitListener() {
            @Override
            public void onSubmit() {
                initData();
            }
        }).setCOMDevice(device).show();
    }


}
