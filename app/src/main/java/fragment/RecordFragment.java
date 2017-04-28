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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yysh.officialcar.LoginActivity;
import com.example.yysh.officialcar.QRCodeActivity;
import com.example.yysh.officialcar.R;

import java.util.ArrayList;

import adapter.RecordListViewAdapter;
import adapter.RecordRecyclerAdapter;
import bean.RepairRecordBean;
import db.DatabaseManger;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {

    private ArrayList<RepairRecordBean> recordList;
    private RecyclerView record_recycler;
    private DatabaseManger dbManager;
    private RelativeLayout record_rela;
    private TextView record_toLogin;
    private RecordBrodcast recordBrodcast;
    private RecordRecyclerAdapter recordRecyclerAdapter;

    public RecordFragment() {
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
        View view=inflater.inflate(R.layout.fragment_record,container,false);
        record_recycler = ((RecyclerView) view.findViewById(R.id.record_recycler));
        record_rela = ((RelativeLayout) view.findViewById(R.id.record_rela));
        record_toLogin = ((TextView) view.findViewById(R.id.record_toLogin));
        initData();
        setListener();

        //注册广播
        recordBrodcast=new RecordBrodcast();
        IntentFilter filter=new IntentFilter();
        filter.addAction("clear");
        getContext().registerReceiver(recordBrodcast,filter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(recordBrodcast);
    }

    private void initData() {
        try {
            int length = dbManager.getDataCounts("myUser");
            //参数2是要查询的字段
            Cursor myCursor = dbManager.queryData("myUser", new String[]{"qrcode"}, null, null, null, null, null);
            while(myCursor.moveToNext()){
                int qrcode=myCursor.getInt(myCursor.getColumnIndex("qrcode"));
                if(qrcode==1) {          //1表示以及扫码过后绑定的车
                    recordList=new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        RepairRecordBean bean=new RepairRecordBean();
                        bean.setOrderNumber((i+1)+".");
                        bean.setName("中燕汽修");
                        bean.setTime("2007.10.31至2007.10.31");
                        bean.setMileage("里程表底119318");
                        bean.setChangeMileageTable("更换里程表(表底归零)");
                        bean.setSettlement("结算金额(元)6666");
                        recordList.add(bean);
                    }
                    recordRecyclerAdapter=new RecordRecyclerAdapter(recordList,getContext());
                    LinearLayoutManager manger=new LinearLayoutManager(getContext());
                    manger.setOrientation(LinearLayoutManager.VERTICAL);
                    record_recycler.setLayoutManager(manger);
                    record_recycler.setAdapter(recordRecyclerAdapter);

                    record_recycler.setVisibility(View.VISIBLE);
                    record_rela.setVisibility(View.GONE);
                }else{
                    recordRecyclerAdapter.clearList();
                    record_recycler.setVisibility(View.GONE);
                    record_rela.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setListener() {
        record_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QRCodeActivity.class));
                //getActivity().finish();
            }
        });
    }

    //广播类
    class RecordBrodcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //在广播类里将集合清空
            recordRecyclerAdapter.clearList();
            record_recycler.setVisibility(View.GONE);
            record_rela.setVisibility(View.VISIBLE);
        }
    }


}
