package com.dsn.andy.bgmlauncher.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dson.smart.common.entity.DsonSmartCtrlCmd;
import com.dson.smart.common.entity.DsonSmartDevice;
import com.dson.smart.common.entity.DsonSmartDeviceOrder;
import com.dson.smart.common.entity.DsonSmartDeviceType;
import com.dson.smart.common.entity.DsonSmartHostInfo;
import com.dson.smart.common.entity.DsonSmartRoom;
import com.dson.smart.common.entity.DsonSmartScene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by dell on 2018/3/28.
 */

public class SmartHomeActivity extends Activity implements DataBroadcastReceiver.OnDataChangeListener {

    private static SmartHomeActivity instance;

    @Bind(R.id.home)
    Button home;
    @Bind(R.id.setting)
    Button setting;
    @Bind(R.id.status)
    Button status;
    @Bind(R.id.exit)
    Button exit;
    @Bind(R.id.roomList)
    ListView roomList;
    @Bind(R.id.deviceGrid)
    GridView deviceGrid;
    @Bind(R.id.sceneGrid)
    GridView sceneGrid;

    SmarthomeHelper smarthome;


    AreaAdapter areaAdapter;
    List<DsonSmartRoom> areaData = new ArrayList();

    TypeAdapter typeAdapter;
    List<DsonSmartDeviceType> typeData = new ArrayList();

    DeviceAdapter deviceAdapter;
    SceneAdapter sceneAdapter;


    @Bind(R.id.areaArrow)
    ImageView areaArrow;
    @Bind(R.id.areaLayout)
    RelativeLayout areaLayout;
    @Bind(R.id.typeArrow)
    ImageView typeArrow;
    @Bind(R.id.typeLayout)
    RelativeLayout typeLayout;
    @Bind(R.id.typeList)
    ListView typeList;
    @Bind(R.id.sceneLayout)
    RelativeLayout sceneLayout;

    DataBroadcastReceiver receiver;

    List<DsonSmartDevice> gridDevices;


    AdapterView.OnItemClickListener roomItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectArea(position);
        }
    };
    AdapterView.OnItemClickListener typeItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectType(position);
        }
    };

    AdapterView.OnItemClickListener deviceItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DsonSmartDevice device = deviceAdapter.getItem(position);
            controlDevice(device);
        }
    };

    AdapterView.OnItemClickListener sceneItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DsonSmartScene scene = sceneAdapter.getItem(position);
            openScene(scene);
        }
    };
    @Bind(R.id.custom)
    TextView custom;


    public static SmartHomeActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_smarthome);
        ButterKnife.bind(this);

        DsonSmartHostInfo hostInfo = DsonConstants.getInstance().getHostInfo();
        if(hostInfo != null){
            custom.setText("欢迎使用"+hostInfo.getLoginPrompt()+"智能家居");
        }else{
            hostInfo = ServiceHelper.getInstance(this).getHostInfo();
            if(hostInfo != null){
                custom.setText("欢迎使用"+hostInfo.getLoginPrompt()+"智能家居");
            }else{
                custom.setText("欢迎使用智能家居");
            }
        }

        smarthome = SmarthomeHelper.getInstance(getApplicationContext());

        receiver = (DataBroadcastReceiver) new DataBroadcastReceiver(this).register();
        receiver.setOnDataChangeListener(this);

        initAreaData();
        initSceneData();
        initTypeData();

        areaLayout.performClick();

    }

    synchronized  void  initAreaData() {
        if (DsonConstants.getInstance().getRooms() == null) return;

        areaData.clear();
        DsonSmartRoom all = new DsonSmartRoom();
        all.setRoomName("全部");

        areaData.add(all);

        areaData.addAll(DsonConstants.getInstance().getRooms().getRooms());
        if (areaAdapter == null) {
            areaAdapter = new AreaAdapter(this, areaData);
            roomList.setAdapter(areaAdapter);

            roomList.setOnItemClickListener(roomItemClickListener);

            roomList.setSelector(new ColorDrawable(Color.TRANSPARENT));


            Log.e("andy", "show " + areaAdapter.getCount());

        } else {
            areaAdapter.setData(areaData);
        }

    }

    void fillDevice(List<DsonSmartDevice> devices) {
        gridDevices = devices;
        if (deviceAdapter == null) {
            deviceAdapter = new DeviceAdapter(this, devices);
            deviceGrid.setAdapter(deviceAdapter);

            deviceGrid.setOnItemClickListener(deviceItemClickListener);


        } else {
            deviceAdapter.setData(gridDevices);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        receiver.unregister();
        instance = null;

    }

    @OnClick({R.id.home, R.id.setting, R.id.status, R.id.exit, R.id.areaLayout, R.id.typeLayout, R.id.sceneLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home:
                break;
            case R.id.setting:
                break;
            case R.id.status:
                break;
            case R.id.exit:
                exit();
                break;
            case R.id.areaLayout:
                onArea();
                break;
            case R.id.typeLayout:
                onType();
                break;
            case R.id.sceneLayout:
                onScene();
                break;

        }
    }

    void exit() {
        if(DsonConstants.getInstance().getHostInfo().isSupportLogin()) {
            DsonConstants.getInstance().exit(this);
            startActivity(new Intent(this, LoginSmartHomeActivity.class));
            finish();
        }else{
            finish();
        }
    }

    synchronized void initSceneData() {
        if (DsonConstants.getInstance().getScenes() == null) return;
        List<DsonSmartScene> data = DsonConstants.getInstance().getScenes().getScenes();
        if (sceneAdapter == null) {
            sceneAdapter = new SceneAdapter(this, data);
            sceneGrid.setAdapter(sceneAdapter);

            sceneGrid.setOnItemClickListener(sceneItemListener);

        } else {
            sceneAdapter.notifyDataSetChanged();
        }
    }


    synchronized void initTypeData() {
        List<DsonSmartDeviceType> types = DsonConstants.getInstance().getTypes();
        if (types == null) {
            return;
        }
        DsonSmartDeviceType all = new DsonSmartDeviceType(0);
        typeData = new ArrayList<DsonSmartDeviceType>();
        typeData.add(all);
        typeData.addAll(types);

        Collections.sort(typeData, new Comparator<DsonSmartDeviceType>() {
            @Override
            public int compare(DsonSmartDeviceType o1, DsonSmartDeviceType o2) {
                if (o2.getType() == o1.getType()) {
                    return DsonConstants.getInstance().getDevice(o1).size() - DsonConstants.getInstance().getDevice(o2).size();
                } else {
                    return o1.getType() - o2.getType();
                }
            }
        });


        if (typeAdapter == null) {
            typeAdapter = new TypeAdapter(this, typeData);
            typeList.setAdapter(typeAdapter);

            typeList.setOnItemClickListener(typeItemClickListener);

            typeList.setSelector(new ColorDrawable(Color.TRANSPARENT));


        } else {
            typeAdapter.setData(typeData);
        }


    }

    void onArea() {
        if (DsonConstants.getInstance().getRooms() == null) return;
        if (sceneGrid.getVisibility() == View.VISIBLE) {
            sceneGrid.setVisibility(View.GONE);
            deviceGrid.setVisibility(View.VISIBLE);
        }
        if (roomList.getVisibility() == View.VISIBLE) {
            roomList.setVisibility(View.GONE);
            areaArrow.setBackgroundResource(R.drawable.ic_dson_arrow_down);
        } else {

            initAreaData();
            typeList.setVisibility(View.GONE);
            typeArrow.setBackgroundResource(R.drawable.ic_dson_arrow_down);
            roomList.setVisibility(View.VISIBLE);
            areaArrow.setBackgroundResource(R.drawable.ic_dson_arrow_up);
            selectArea(0);
        }
    }

    void onType() {
        List<DsonSmartDeviceType> types = DsonConstants.getInstance().getTypes();
        if (types == null) {
            return;
        }
        if (sceneGrid.getVisibility() == View.VISIBLE) {
            sceneGrid.setVisibility(View.GONE);
            deviceGrid.setVisibility(View.VISIBLE);
        }
        if (typeList.getVisibility() == View.VISIBLE) {
            typeList.setVisibility(View.GONE);
            typeArrow.setBackgroundResource(R.drawable.ic_dson_arrow_down);
        } else {
            initTypeData();
            roomList.setVisibility(View.GONE);
            areaArrow.setBackgroundResource(R.drawable.ic_dson_arrow_down);
            typeList.setVisibility(View.VISIBLE);
            typeArrow.setBackgroundResource(R.drawable.ic_dson_arrow_up);
            selectArea(0);
        }
    }

    void onScene() {
        if (DsonConstants.getInstance().getScenes() == null) return;
        if (sceneGrid.getVisibility() != View.VISIBLE) {
            sceneGrid.setVisibility(View.VISIBLE);
            deviceGrid.setVisibility(View.GONE);
        }

        if (typeList.getVisibility() == View.VISIBLE) {
            typeList.setVisibility(View.GONE);
            typeArrow.setBackgroundResource(R.drawable.ic_dson_arrow_down);
        }

        if (roomList.getVisibility() == View.VISIBLE) {
            roomList.setVisibility(View.GONE);
            areaArrow.setBackgroundResource(R.drawable.ic_dson_arrow_down);
        }

        initSceneData();
    }


    void controlDevice(final DsonSmartDevice device) {
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
            new ThreeCommandOperateDialog(this, R.style.dialog, new ThreeCommandOperateDialog.OnOpertateListener() {
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

    void openScene(DsonSmartScene scene) {
        smarthome.openScene(scene);
    }

    void selectArea(int position) {
        if (areaAdapter == null) return;
        areaAdapter.setCurrent(position);
        if (position > 0) {
            DsonSmartRoom area = areaAdapter.getCurrentItem();
            fillDevice(area.getDevices());
        } else {
            fillDevice(DsonConstants.getInstance().getDevices().getDevices());
        }
    }

    void selectType(int position) {
        if (typeAdapter == null) return;
        typeAdapter.setCurPosition(position);
        if (position > 0) {
            DsonSmartDeviceType type = typeAdapter.getCurrent();
            fillDevice(DsonConstants.getInstance().getDevice(type));
        } else {
            fillDevice(DsonConstants.getInstance().getDevices().getDevices());
        }
    }


    @Override
    public void onDeviceChange() {

    }

    @Override
    public void onRoomChange() {
        if (areaLayout.getVisibility() == View.VISIBLE) {
            if(areaAdapter == null){
                initAreaData();
                typeList.setVisibility(View.GONE);
                typeArrow.setBackgroundResource(R.drawable.ic_dson_arrow_down);
                roomList.setVisibility(View.VISIBLE);
                areaArrow.setBackgroundResource(R.drawable.ic_dson_arrow_up);
                selectArea(0);
            }else {
                DsonSmartRoom room = areaAdapter.getCurrentItem();
                initAreaData();
                selectArea(areaAdapter.getItemPosition(room));
            }
        }
    }

    @Override
    public void onSceneChange() {
        if (sceneGrid.getVisibility() == View.VISIBLE) {
            initSceneData();
        }
    }

    @Override
    public void onDeviceTypeChange() {
        if (typeLayout.getVisibility() == View.VISIBLE) {
            DsonSmartDeviceType t = typeAdapter.getCurrent();
            initTypeData();
            if(t != null) {
                selectType(typeAdapter.getItemPosition(t));
            }else{
                selectType(0);
            }
        }
    }

    @Override
    public void onDeviceUpdate() {
        Log.e("andy", "onDeviceUpdate" + deviceGrid.getVisibility());
        if (deviceGrid.getVisibility() == View.VISIBLE) {
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

}
