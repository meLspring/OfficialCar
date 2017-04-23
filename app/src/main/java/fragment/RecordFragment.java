package fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yysh.officialcar.LoginActivity;
import com.example.yysh.officialcar.R;

import java.util.ArrayList;

import adapter.RecordListViewAdapter;
import bean.RepairRecordBean;
import db.DatabaseManger;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {

    private ListView record_listview;
    private ArrayList<RepairRecordBean> recordList;
    private RecordListViewAdapter adapter;
    private DatabaseManger dbManager;
    private RelativeLayout record_rela;
    private TextView record_toLogin;

    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager=DatabaseManger.getInstance(getContext());
        initData();
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_record,container,false);
        record_listview = ((ListView) view.findViewById(R.id.record_listview));
        record_rela = ((RelativeLayout) view.findViewById(R.id.record_rela));
        record_toLogin = ((TextView) view.findViewById(R.id.record_toLogin));
        record_listview.setAdapter(adapter);

        setListener();
        return view;
    }


    private void initData() {
        recordList=new ArrayList<>();
        try {
            int length = dbManager.getDataCounts("myUser");
            if(length>0){
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
                adapter=new RecordListViewAdapter(recordList,getContext());
                record_listview.setVisibility(View.VISIBLE);
                record_rela.setVisibility(View.GONE);
            }else{
                record_listview.setVisibility(View.GONE);
                record_rela.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setListener() {
        record_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

}
