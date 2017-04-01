package fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.yysh.officialcar.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adapter.InformationListViewAdapter;
import bean.InformationBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment {
    private RecyclerView infor_recyclerview;
    private ArrayList<InformationBean> informationBeanArrayList;
    private InformationListViewAdapter adapter;
    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        informationBeanArrayList=new ArrayList<>();
        InformationBean beanOne=new InformationBean();
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        beanOne.setTime(time);
        beanOne.setInformation("中控异响，维修后没有解决");

        InformationBean beanTwo=new InformationBean();
        beanTwo.setTime(time);
        beanTwo.setInformation("油耗增加");

        informationBeanArrayList.add(beanOne);
        informationBeanArrayList.add(beanTwo);

        adapter=new InformationListViewAdapter(informationBeanArrayList,getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_information,container,false);
        infor_recyclerview = ((RecyclerView) view.findViewById(R.id.infor_recyclerview));
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        infor_recyclerview.setLayoutManager(manager);
        infor_recyclerview.setAdapter(adapter);
        return view;
    }

}
