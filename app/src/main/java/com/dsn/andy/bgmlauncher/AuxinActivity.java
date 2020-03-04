package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dsn.andy.bgmlauncher.auxin.MyJNI;
import com.dsn.andy.bgmlauncher.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/1/12.
 */

public class AuxinActivity extends Activity {


    @Bind(R.id.in)
    RadioButton in;
    @Bind(R.id.out)
    RadioButton out;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auxin);
        ButterKnife.bind(this);

        Utils.execLinuxCommand("chmod 777 /dev/*");

        int auxin = MyJNI.getAuxin();
        Utils.pw("getAuxin:"+auxin);
        if(auxin == (int)'i'){
            in.setChecked(true);
        }else{
            out.setChecked(true);
        }


        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MyJNI.setAuxin('i');
                Utils.pw("setAuxin:i");
               Utils.putInt(AuxinActivity.this,Utils.AUXIN_KEY,1);
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyJNI.setAuxin('o');
                Utils.pw("setAuxin:o");
                Utils.putInt(AuxinActivity.this,Utils.AUXIN_KEY,0);
            }
        });

    }
}
