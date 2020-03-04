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
import com.dsn.andy.bgmlauncher.home.DeviceAdapter;
import com.dsn.andy.bgmlauncher.home.DsonConstants;
import com.dsn.andy.bgmlauncher.home.SmarthomeHelper;
import com.dsn.andy.bgmlauncher.home.ThreeCommandOperateDialog;
import com.dson.smart.common.entity.DsonSmartCtrlCmd;
import com.dson.smart.common.entity.DsonSmartDevice;
import com.dson.smart.common.entity.DsonSmartDeviceOrder;
import com.dson.smart.common.entity.DsonSmartDeviceType;
import com.dson.smart.common.entity.DsonSmartHostInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/9/21.
 */

public class DeviceListFragment extends PageFragment {

    View layout;
    @Bind(R.id.grid)
    GridView grid;

    List<DsonSmartDevice> gridDevices;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.btn_logout)
    Button btnLogout;

    private DeviceAdapter deviceAdapter;
    MainActivity2 activity;

    AdapterView.OnItemClickListener deviceItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DsonSmartDevice device = deviceAdapter.getItem(position);
            controlDevice(device);
        }
    };

    public DeviceListFragment() {

    }

    public DeviceListFragment setActivity(MainActivity2 activity){
        this.activity = activity;

        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_device, null);
        ButterKnife.bind(this, layout);

        title.setText("智能设备");


        refresh();

        DsonSmartHostInfo hostInfo = DsonConstants.getInstance().getHostInfo();
        if(hostInfo!=null && !hostInfo.isSupportLogin()){
            btnLogout.setVisibility(View.GONE);
        }


        Log.e("andy", "fragment oncreateview :DeviceListFragment");

        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void refresh() {
        if (grid == null) return;
        if (DsonConstants.getInstance().getDevices() == null) return;
        fillDevice(DsonConstants.getInstance().getDevices().getDevices());
    }


    void fillDevice(List<DsonSmartDevice> devices) {
        if (devices == null) return;
        gridDevices = devices;

        deviceAdapter = new DeviceAdapter(getActivity(), devices);
        grid.setAdapter(deviceAdapter);

        grid.setOnItemClickListener(deviceItemClickListener);



    }

    void controlDevice(final DsonSmartDevice device) {
        final SmarthomeHelper smarthome = SmarthomeHelper.getInstance(getActivity());
        if (DsonSmartDeviceType.isOnOffDevice(Integer.parseInt(device.getDeviceType()))) {
            String order = device.getDsonSmartCtrlCmd().getOrder();

            DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
            if (DsonSmartDeviceOrder.OPEN.equals(order)) {
                cmd.setOrder(DsonSmartDeviceOrder.CLOSE);
                cmd.setValue1("0");
            } else {
                cmd.setOrder(DsonSmartDeviceOrder.OPEN);
                cmd.setValue1("1");
            }
            Log.e("andy", "order=" + cmd.getOrder());
            cmd.setDeviceId(device.getDeviceId());
            smarthome.controlDevice(cmd);

        } else if (DsonSmartDeviceType.isThreeStatusDevice(Integer.parseInt(device.getDeviceType()))) {
            new ThreeCommandOperateDialog(getActivity(), R.style.dialog, new ThreeCommandOperateDialog.OnOpertateListener() {
                @Override
                public void operate(int status) {
                    DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                    cmd.setDeviceId(device.getDeviceId());
                    if (status == 1) {
                        cmd.setValue1("1");
                        cmd.setOrder(DsonSmartDeviceOrder.OPEN);
                    } else if (status == 0) {
                        cmd.setValue1("0");
                        cmd.setOrder(DsonSmartDeviceOrder.CLOSE);
                    } else if (status == 2) {
                        cmd.setValue1("2");
                        cmd.setOrder(DsonSmartDeviceOrder.STOP);
                    }

                    smarthome.controlDevice(cmd);

                }
            }).setTitle(device.getDeviceName()).show();
        } else if (Integer.parseInt(device.getDeviceType()) == DsonSmartDeviceType.DEVICE_TYPE_AIRCONDITION) {
//            new AirConditionDailog(this,R.style.dialog).show();
        }

    }

    public void onDeviceUpdate() {
        Log.e("andy", "onDeviceUpdate" + grid.getVisibility());
        if (grid.getVisibility() == View.VISIBLE) {
            for (int i = 0; i < gridDevices.size(); i++) {
                DsonSmartDevice d = gridDevices.get(i);
                for (DsonSmartDevice dd : DsonConstants.getInstance().getDevices().getDevices()) {
                    if (d.equals(dd)) {
                        gridDevices.set(i, dd);
                        break;
                    }
                }
            }

            if (deviceAdapter != null)
                deviceAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        Log.e("andy","btn logout 1");
        if(activity != null){
            Log.e("andy","btn logout 2");
            activity.setFragmentsForHomeLogout();
        }
        Log.e("andy","btn logout 3");
        DsonSmartHostInfo hostInfo  = DsonConstants.getInstance().getHostInfo();
        if(hostInfo!=null && hostInfo.isSupportLogin()) {
            Log.e("andy","btn logout 4");
            DsonConstants.getInstance().exit(getActivity());
        }
        Log.e("andy","btn logout 5");



    }
}
