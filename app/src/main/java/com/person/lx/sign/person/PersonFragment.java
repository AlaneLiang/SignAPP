package com.person.lx.sign.person;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.person.lx.sign.R;
import com.person.lx.sign.utils.ImageUtil;

import static android.content.Context.MODE_PRIVATE;

public class PersonFragment extends Fragment implements PersonContract.view {

    private LinearLayout linearLayout;
    private View view;
    public PersonFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);
        initView();

        return view;
    }

    public void initView(){
        linearLayout = view.findViewById(R.id.layout_person);
        Bitmap bm = ImageUtil.decodeSampledBitmapFromResource(getResources(),R.drawable.bg,480,720);
        Drawable drawable =new BitmapDrawable(bm);
        linearLayout.setBackground(drawable);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * 从SharedPreferences获取存储的值
     * @param value
     * @return
     */
    private String  getFromSharedPreferences(String value){
        SharedPreferences preferences = getActivity().getSharedPreferences("data",MODE_PRIVATE);
        String result = preferences.getString(value,"");
        return result;
    }
    @Override
    public String getCompanyId() {

        return getFromSharedPreferences("companyId");
    }

    @Override
    public String getToken() {
        return getFromSharedPreferences("token");
    }

    @Override
    public String getPhone() {
        return getFromSharedPreferences("phone");
    }

}
