package com.dsn.andy.bgmlauncher.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import com.dsn.andy.bgmlauncher.R;

/**
 * Created by dell on 2018/4/23.
 */

public class AirConditionDailog extends Dialog {

    public AirConditionDailog(@NonNull Context context) {
        super(context);
    }

    public AirConditionDailog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public AirConditionDailog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ac);
    }
}
