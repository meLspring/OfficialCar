package fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yysh.officialcar.QRCodeActivity;
import com.example.yysh.officialcar.R;

import java.util.ArrayList;
import java.util.List;

import adapter.CarConditionRecyAdapter;
import db.DatabaseManger;

/**
 * A simple {@link Fragment} subclass.
 */
//车况详情fragment
public class CarConditionFragment extends Fragment {
    private List<String> conditionList;
    private RecyclerView condition_recycler;
    private CarConditionRecyAdapter adapter;
    private DatabaseManger dbManager;
    private TextView to_Login;
    private RelativeLayout condition_toLogin_rela;
    private CarConditionBrodcast carConditionBrodcast;

    public CarConditionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager=DatabaseManger.getInstance(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_car_condition,container,false);
        initView(view);
        initData();
        setListener();

        //注册广播
        carConditionBrodcast=new CarConditionBrodcast();
        IntentFilter filter=new IntentFilter();
        filter.addAction("clear");
        getContext().registerReceiver(carConditionBrodcast,filter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消注册广播
        getContext().unregisterReceiver(carConditionBrodcast);
    }

    private void initData() {
        try {
           // int length = dbManager.getDataCounts("myUser");
            //先去数据库查询表条数是否不为0,不为0表示有账号存在
            //参数2是要查询的字段
            Cursor myCursor = dbManager.queryData("myUser", new String[]{"qrcode"}, null, null, null, null, null);
            while(myCursor.moveToNext()){
                int qrcode=myCursor.getInt(myCursor.getColumnIndex("qrcode"));
                if(qrcode==1){          //1表示以及扫码过后绑定的车
                    conditionList=new ArrayList<>();
                    conditionList.add("单位");
                    conditionList.add("部门/单位");
                    conditionList.add("车辆详情");
                    conditionList.add("厂牌型号");
                    conditionList.add("发动机型号");
                    conditionList.add("排量/功率");
                    conditionList.add("环保等级");
                    conditionList.add("使用性质");
                    conditionList.add("投保公司");
                    conditionList.add("年检时间");
                    conditionList.add("二级维护时间");
                    conditionList.add("核定载客");
                    conditionList.add("生产厂家");
                    conditionList.add("发动机型号");
                    conditionList.add("车辆识别代码号");
                    conditionList.add("总质量");
                    conditionList.add("核定载质量");
                    conditionList.add("整备质量");
                    adapter=new CarConditionRecyAdapter(conditionList,getContext());

                    LinearLayoutManager manager=new LinearLayoutManager(getContext());
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    condition_recycler.setLayoutManager(manager);
                    condition_recycler.setAdapter(adapter);


                    condition_recycler.setVisibility(View.VISIBLE);
                    condition_toLogin_rela.setVisibility(View.GONE);

                }else {
                    adapter.clearList();
                    condition_recycler.setVisibility(View.GONE);
                    condition_toLogin_rela.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(View view) {
        condition_recycler = ((RecyclerView) view.findViewById(R.id.condition_recyclerview));
        to_Login = ((TextView) view.findViewById(R.id.condition_toLogin));
        condition_toLogin_rela = ((RelativeLayout) view.findViewById(R.id.condition_toLogin_rela));


    }

    private void setListener() {
        to_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QRCodeActivity.class));
               // getActivity().finish();
            }
        });
    }

    //广播类
    class CarConditionBrodcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //在广播类里将集合清空
            adapter.clearList();
            condition_recycler.setVisibility(View.GONE);
            condition_toLogin_rela.setVisibility(View.VISIBLE);
        }
    }

}
