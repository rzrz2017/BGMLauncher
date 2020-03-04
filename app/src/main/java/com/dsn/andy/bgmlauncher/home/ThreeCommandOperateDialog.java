package com.dsn.andy.bgmlauncher.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;

/**
 * Created by dell on 2018/4/3.
 */

public class ThreeCommandOperateDialog extends Dialog implements View.OnClickListener {
    TextView title;
    ImageView open;
    ImageView stop;
    ImageView close;

    String titleStr;
    private Context mContext;
    private int status = -1;

    OnOpertateListener listener;

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open:
                status = 1;
                if (listener != null) {
                    listener.operate(status);
                }
                dismiss();
                break;
            case R.id.stop:
                status = 2;
                if (listener != null) {
                    listener.operate(status);
                }
                dismiss();
                break;
            case R.id.close:
                status = 0;
                if (listener != null) {
                    listener.operate(status);
                }
                dismiss();
                break;

        }
    }

    public interface OnOpertateListener {
        public void operate(int status);
    }


    public ThreeCommandOperateDialog(@NonNull Context context, OnOpertateListener listener) {
        super(context);
        this.mContext = context;
        this.listener = listener;
    }

    public ThreeCommandOperateDialog(@NonNull Context context, @StyleRes int themeResId, OnOpertateListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }

    public ThreeCommandOperateDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public ThreeCommandOperateDialog setTitle(String str) {
        titleStr = str;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_three_command);
        setCanceledOnTouchOutside(true);

        initViews();

    }

    private void initViews() {
        title = (TextView)findViewById(R.id.title);
        open = (ImageView)findViewById(R.id.open);
        stop = (ImageView)findViewById(R.id.stop);
        close = (ImageView)findViewById(R.id.close);

        open.setOnClickListener(this);
        stop.setOnClickListener(this);
        close.setOnClickListener(this);

        if(!TextUtils.isEmpty(titleStr)){
            title.setText(titleStr);
        }

    }


}
