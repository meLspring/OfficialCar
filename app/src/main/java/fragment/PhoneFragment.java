package fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.yysh.officialcar.R;

import java.util.ArrayList;

import adapter.PhoneListViewAdapter;
import bean.PhoneBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneFragment extends Fragment {
    private ArrayList<PhoneBean> phoneBeanArrayList;
     private PhoneListViewAdapter adapter;
    private RecyclerView phone_recyclerVew;

    public PhoneFragment() {
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
        View view=inflater.inflate(R.layout.fragment_phone,container,false);
        phone_recyclerVew = ((RecyclerView) view.findViewById(R.id.phone_recyclerVew));
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        phone_recyclerVew.setLayoutManager(manager);
        phone_recyclerVew.setAdapter(adapter);

        return view;
    }

    private void initData() {
        phoneBeanArrayList=new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            PhoneBean bean=new PhoneBean();
            bean.setPhone_name("中燕汽修");
            bean.setPhone_number("81306321");
            bean.setPhone_rescue("51100608");
            phoneBeanArrayList.add(bean);
        }
        adapter=new PhoneListViewAdapter(phoneBeanArrayList,getContext());
    }
}
