package com.reborn.skin.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.avocarrot.json2view.DynamicView;
import com.avocarrot.json2view.DynamicViewId;
import com.orhanobut.logger.Logger;
import com.reborn.skin.ui.base.BaseSkinActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 戴震宇 on 2018/8/14 0014.
 */
public class Json2ViewActivity extends BaseSkinActivity  implements View.OnClickListener{

    JSONObject jsonObject;
    View view;
    @Override
    protected Object getLayout() {
        try{
            jsonObject = new JSONObject(readFile("test.json",this));
        }catch (JSONException je){
            je.printStackTrace();
            jsonObject = null;
        }
        if (jsonObject != null){
             view = DynamicView.createView(this,jsonObject,SampleViewHolder.class);
            ((SampleViewHolder) view.getTag()).clickableView.setOnClickListener(this);
            view.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
            setContentView(view);
        }else {
            Logger.e("Could not load valid json file");
        }
        return view;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }
    private String readFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line;
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null) isr.close();
                if (fIn != null) fIn.close();
                if (input != null) input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    @Override
    public void onClick(View v) {
        startActivity(MainActivity.class);
    }

    static public class SampleViewHolder {
        @DynamicViewId(id = "testClick")
        public View clickableView;

        public SampleViewHolder() {
        }
    }
}
