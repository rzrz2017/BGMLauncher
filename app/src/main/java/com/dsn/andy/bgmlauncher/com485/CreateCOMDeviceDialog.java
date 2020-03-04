package com.dsn.andy.bgmlauncher.com485;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.greendao.dao.COMCommandDao;
import com.andy.music.COMCommand;
import com.andy.music.COMDevice;
import com.dsn.andy.bgmlauncher.DSNApplication;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.db2.DBOperator;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.dsn.andy.bgmlauncher.view.CommomDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/10/2.
 */

public class CreateCOMDeviceDialog extends Dialog {


    @Bind(R.id.iv_device_img)
    ImageView ivDeviceImg;
    @Bind(R.id.et_layout_command_name)
    EditText etLayoutCommandName;
    @Bind(R.id.et_layout_command_content)
    EditText etLayoutCommandContent;
    @Bind(R.id.et_layout_send)
    Button etLayoutSend;
    @Bind(R.id.et_layout_save)
    Button etLayoutSave;
    @Bind(R.id.et_layout_delete)
    Button etLayoutDelete;
    @Bind(R.id.layout_commands)
    GridView layoutCommands;
    @Bind(R.id.btn_add_command)
    Button btnAddCommand;
    @Bind(R.id.cancel)
    TextView delete;
    @Bind(R.id.submit)
    TextView submit;
    @Bind(R.id.btn_back)
    TextView btnBack;
    @Bind(R.id.layout_add_command)
    LinearLayout layoutAddCommand;
    @Bind(R.id.et_device_name)
    EditText etDeviceName;
    @Bind(R.id.btn_save_device)
    Button btnSaveDevice;

    COMDevice mDevice;

    COMCommand curCommand;

    COMCommandAdapter commandAdapter;

    OnSubmitListener listener;

    private int curImg;
    private int[] imgIds = {
            R.drawable.ic_device_light_on,
            R.drawable.com_device_curtain_open,
            R.drawable.com_device_window_open,
            R.drawable.com_device_ac_on,
            R.drawable.com_device_tv_on,
            R.drawable.com_device_scene_switch_on,
            R.drawable.com_device_camera_on,

    };

    public interface OnSubmitListener {
        void onSubmit();
    }

    public CreateCOMDeviceDialog(@NonNull Context context) {
        super(context);
    }

    public CreateCOMDeviceDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public CreateCOMDeviceDialog(@NonNull Context context, @StyleRes int themeResId, OnSubmitListener listener) {
        super(context, themeResId);
        this.listener = listener;
    }

    public CreateCOMDeviceDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CreateCOMDeviceDialog setCOMDevice(COMDevice device) {
        this.mDevice = device;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_com_device);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        initView();
    }

    private void initView() {

        if (mDevice == null) {
            delete.setEnabled(false);
            btnAddCommand.setEnabled(false);
        } else {
            delete.setEnabled(true);
            btnAddCommand.setEnabled(true);

            etDeviceName.setText(mDevice.getDeviceName());
            curImg = mDevice.getDeviceType()-1;
            ivDeviceImg.setBackgroundResource(COMConstants.getDeviceImgResID(mDevice));

        }

        etDeviceName.clearFocus();

        onBack();
    }


    @OnClick({R.id.btn_save_device, R.id.btn_back, R.id.layout_add_command, R.id.iv_device_img, R.id.et_layout_send, R.id.et_layout_save, R.id.et_layout_delete, R.id.btn_add_command, R.id.cancel, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save_device:
                onSaveDevice();
                break;
            case R.id.iv_device_img:
                curImg++;
                if(curImg>=imgIds.length){
                    curImg = 0;
                }
                ivDeviceImg.setBackgroundResource(imgIds[curImg]);
                break;
            case R.id.et_layout_send:
                onSendCommand();
                break;
            case R.id.et_layout_save:
                onSaveCommand();
                break;
            case R.id.et_layout_delete:
                onDeleteCommand();
                break;
            case R.id.btn_add_command:
                onAddCommand();
                break;
            case R.id.cancel:
                onDeleteDevice();
                break;
            case R.id.submit:
                dismiss();
                break;
            case R.id.layout_add_command:
                break;
            case R.id.btn_back:
                onBack();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();

        if (listener != null) {
            listener.onSubmit();
        }

    }

    private void onDeleteDevice() {
        new CommomDialog(getContext(), R.style.dialog, "确定要删除这个设备吗？", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    boolean ret = new DBOperator<COMDevice>(getContext()).delete(mDevice);
                    String where = "where " + COMCommandDao.Properties.DeviceId.columnName + "=" + mDevice.getId();
                    new DBOperator<COMCommand>(getContext()).deleteAll(new COMCommand(), where, null);

                    if (ret) {
                        Utils.toast(getContext(), "删除设备成功");
                    } else {
                        Utils.toast(getContext(), "删除设备失败");
                    }

                    dismiss();
                } else {

                }
            }
        }).setNegativeButton("取消").setPositiveButton("确定")
                .setTitle("提示").show();


    }

    private void onSaveDevice() {

        String name = etDeviceName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Utils.toast(getContext(), "设备名称不能为空");
            return;
        }
        COMDevice device = null;
        DBOperator operator = new DBOperator<COMDevice>(getContext());
        long id = -1;
        if (mDevice == null) {
            device = new COMDevice();
            device.setDeviceType(curImg+1);
            device.setDeviceName(name);

            id = operator.insert(device);

            if (id > -1) {
                Utils.toast(getContext(), "保存成功");
            } else {
                Utils.toast(getContext(), "保存失败");
            }

            device.setId(id);
            mDevice = device;

            btnAddCommand.setEnabled(true);
            delete.setEnabled(true);

        } else {
            device = mDevice;
            device.setDeviceName(name);
            device.setDeviceType(curImg+1);

            boolean ret = operator.update(device);

            if (ret) {
                Utils.toast(getContext(), "保存成功");
            } else {
                Utils.toast(getContext(), "保存失败");
            }
        }


    }

    /*
    发送指令数据
     */
    private void onSendCommand() {
        String commandStr = etLayoutCommandContent.getText().toString();
        if (TextUtils.isEmpty(commandStr)) {
            Utils.toast(getContext(), "指令内容为空");
            return;
        }
        try {
            byte[] commandBuffer = Utils.toByteBuffer(commandStr);
            Log.e("andy", "send:" + Utils.toHexString(commandBuffer));
            sendCommand(commandBuffer);
        } catch (Exception e) {

        }

    }

    private void sendCommand(byte[] buffer){
        try {
            DSNApplication application = DSNApplication.getInstance();
            application.sendSerialPortData(buffer);
        } catch (Exception e) {

        }
    }

    /*
    保存指令到数据库
     */
    private void onSaveCommand() {
        String commandName = etLayoutCommandName.getText().toString();
        String commandBytes = etLayoutCommandContent.getText().toString();
        if (TextUtils.isEmpty(commandName)) {
            Utils.toast(getContext(), "指令名称不能为空");
            return;
        }

        if (TextUtils.isEmpty(commandBytes)) {
            Utils.toast(getContext(), "指令内容不能为空");
            return;
        }

        if (mDevice == null) {
            Utils.toast(getContext(), "请先添加设备");
            return;
        }


        if (curCommand == null) {
            COMCommand cmd = new COMCommand();
            cmd.setDeviceId(mDevice.getId());
            cmd.setCommandName(commandName);
            try {
                Log.e("andy", "commandbytes:" + commandBytes);
                Log.e("andy", "commandbytes 2:" + Utils.toByteBuffer(commandBytes));
                cmd.setCommandBytes(Utils.toByteBuffer(commandBytes));
            } catch (Exception e) {
                Utils.toast(getContext(), "输入指令内容不正确:" + e.getMessage());
                return;
            }

            DBOperator<COMCommand> operator = new DBOperator(getContext());
            long id = operator.insert(cmd);

            if (id > -1) {
                Utils.toast(getContext(), "添加指令成功");
            } else {
                Utils.toast(getContext(), "添加指令失败");
            }

            cmd.setId(id);
            curCommand = cmd;

            onBack();

        } else {
            curCommand.setCommandName(commandName);
            try {
                curCommand.setCommandBytes(Utils.toByteBuffer(commandBytes));
            } catch (Exception e) {
                Utils.toast(getContext(), "输入指令内容不正确:" + e.getMessage());
                return;
            }

            DBOperator<COMCommand> operator = new DBOperator(getContext());
            boolean ret = operator.update(curCommand);

            if (ret) {
                Utils.toast(getContext(), "修改指令成功");
            } else {
                Utils.toast(getContext(), "修改指令失败");
            }

            onBack();

        }

    }

    /*
    将指令从数据库删除
     */
    private void onDeleteCommand() {
        if (curCommand == null) {
            Utils.toast(getContext(), "未指定指令");
            return;
        }
        new CommomDialog(getContext(), R.style.dialog, "确定要删除这个指令吗？", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    boolean ret = new DBOperator<COMCommand>(getContext()).delete(curCommand);
                    if (ret) {
                        Utils.toast(getContext(), "删除指令成功");
                    } else {
                        Utils.toast(getContext(), "删除指令失败");
                    }
                    onBack();
                    dismiss();
                } else {

                }
            }
        }).setNegativeButton("取消").setPositiveButton("确定")
                .setTitle("提示").show();

    }

    /*
    跳转到添加指令界面
     */
    private void onAddCommand() {
        layoutCommands.setVisibility(View.GONE);
        layoutAddCommand.setVisibility(View.VISIBLE);
        curCommand = null;

        //etLayoutCommandContent.setText("");
        //etLayoutCommandName.setText("");

    }

    /*
    跳转到指令列表界面
     */
    private void onBack() {
        layoutCommands.setVisibility(View.VISIBLE);
        layoutAddCommand.setVisibility(View.GONE);

        initCommandData();


    }

    private void initCommandData() {
        if (mDevice == null) return;


        String where = "where " + COMCommandDao.Properties.DeviceId.columnName + "=" + mDevice.getId();
        List<COMCommand> data = new DBOperator<COMCommand>(getContext()).query(new COMCommand(), where, null);
        commandAdapter = new COMCommandAdapter(getContext(), data);
        layoutCommands.setAdapter(commandAdapter);

        layoutCommands.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                COMCommand cmd = commandAdapter.getItem(position);
                curCommand = cmd;

                layoutCommands.setVisibility(View.GONE);
                layoutAddCommand.setVisibility(View.VISIBLE);

                etLayoutCommandName.setText(curCommand.getCommandName());
                etLayoutCommandContent.setText(Utils.toHexString(curCommand.getCommandBytes()));

                return true;
            }
        });

        layoutCommands.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                COMCommand cmd = commandAdapter.getItem(position);
                sendCommand(cmd.getCommandBytes());
            }
        });

    }

}
