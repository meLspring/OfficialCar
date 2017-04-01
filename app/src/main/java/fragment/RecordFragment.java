package fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yysh.officialcar.R;

import java.util.ArrayList;

import adapter.RecordListViewAdapter;
import bean.RepairRecordBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {

    private ListView record_listview;
    private ArrayList<RepairRecordBean> recordList;
    private RecordListViewAdapter adapter;
    public RecordFragment() {
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_record,container,false);
        record_listview = ((ListView) view.findViewById(R.id.record_listview));
        record_listview.setAdapter(adapter);
        return view;
    }

    private void initData() {
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
        adapter=new RecordListViewAdapter(recordList,getContext());
    }
    
}
