package fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yysh.officialcar.ChangeCarActivity;
import com.example.yysh.officialcar.InforPlatformActivity;
import com.example.yysh.officialcar.LoginActivity;
import com.example.yysh.officialcar.PhoneActivity;
import com.example.yysh.officialcar.QRCodeActivity;
import com.example.yysh.officialcar.R;
import com.example.yysh.officialcar.SystemIntroduceActivity;

import db.DatabaseManger;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout my_rela_phone,my_rela_info,my_rela_system,my_rela_sigout;
    private TextView my_account,my_phone,my_infor_platform,my_infor_system,my_infor_signout;
    private DatabaseManger dbManager;


    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my,container,false);
        initView(view);
        initData();
        return view;
    }


    private void initView(View view) {
        my_rela_phone = ((RelativeLayout) view.findViewById(R.id.my_rela_phone));
        my_rela_info = ((RelativeLayout) view.findViewById(R.id.my_rela_info));
        my_rela_system = ((RelativeLayout) view.findViewById(R.id.my_rela_system));
        //my_rela_change = ((RelativeLayout) view.findViewById(R.id.my_rela_change));
        my_rela_sigout = ((RelativeLayout) view.findViewById(R.id.my_rela_sigout));
        my_account = ((TextView) view.findViewById(R.id.my_account));
        my_phone = ((TextView) view.findViewById(R.id.my_phone));
        my_infor_platform = ((TextView) view.findViewById(R.id.my_infor_platform));
        my_infor_system = ((TextView) view.findViewById(R.id.my_infor_system));
        //my_infor_change_car = ((TextView) view.findViewById(R.id.my_infor_change_car));
        my_infor_signout = ((TextView) view.findViewById(R.id.my_infor_signout));

        my_rela_phone.setOnClickListener(this);
        my_rela_info.setOnClickListener(this);
        my_rela_system.setOnClickListener(this);
        //my_rela_change.setOnClickListener(this);
        my_rela_sigout.setOnClickListener(this);
    }

    //从数据库中查找
    private void initData() {
        dbManager= DatabaseManger.getInstance(getContext());
        try {
//            int length = dbManager.getDataCounts("myUser");
//            if(length>0){
                Cursor myCursor = dbManager.queryData("myUser", null, null, null, null, null, null);
                while (myCursor.moveToNext()){
                    String userId=myCursor.getString(myCursor.getColumnIndex("userId"));
                    if(userId!=null){
                        my_account.setText("账户："+userId);
                    }
                }
                my_rela_sigout.setVisibility(View.VISIBLE);

//            }else{
//                my_account.setText("您未登录哦");
//                my_rela_sigout.setVisibility(View.GONE);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.my_rela_phone:
                intent=new Intent(getContext(), PhoneActivity.class);
                break;
            case R.id.my_rela_info:
                intent=new Intent(getContext(), InforPlatformActivity.class);
                break;
            case R.id.my_rela_system:
                intent=new Intent(getContext(), QRCodeActivity.class);
                break;
//            case R.id.my_rela_change:
//                intent=new Intent(getContext(), ChangeCarActivity.class);
//                break;
            case R.id.my_rela_sigout:
                exitUser();     //退出当前账户
                return;
        }
        startActivity(intent);
    }


    private void exitUser() {
        try {
            dbManager.clearAllData("myUser");
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
