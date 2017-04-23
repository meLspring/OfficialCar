package fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.yysh.officialcar.R;
import com.example.yysh.officialcar.ZiXunDetailActivity;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import adapter.ZiXunHeaderViewAdapter;
import adapter.ZiXunListViewAdapter;
import bean.ZiXunBean;
import widget.MyListView;
import widget.VpSwipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZiXunFragment extends Fragment {
    private VpSwipeRefreshLayout zixun_swipe;
    private MyListView zixun_listview;
    private XBanner zixun_xbanner;
    private List<Integer> mList;
    private List<ZiXunBean> beanList;
    private ZiXunListViewAdapter list_adapter;


    public ZiXunFragment() {
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
        View view=inflater.inflate(R.layout.fragment_zixun,container,false);
        initView(view);
        setListener();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        zixun_xbanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        zixun_xbanner.stopAutoPlay();
    }



    private void setListener() {
        //swipeRefresh的监听事件,重新请求数据和关闭动画
       zixun_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
                if(zixun_swipe.isRefreshing()){}
                zixun_swipe.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        zixun_swipe.setRefreshing(false);
                    }
                },3000);
           }
       });



        //listview的监听事件
        zixun_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), ZiXunDetailActivity.class);
                startActivity(intent);
            }
        });


        //xbanner的点击事件
        zixun_xbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                Intent intent=new Intent(getContext(), ZiXunDetailActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initView(View view) {
        zixun_swipe = ((VpSwipeRefreshLayout) view.findViewById(R.id.zixun_swipe));
        zixun_listview = ((MyListView) view.findViewById(R.id.zixun_listview));
        zixun_swipe.setColorSchemeColors(Color.GREEN,Color.BLUE,Color.RED);
        View headerView=LayoutInflater.from(getContext()).inflate(R.layout.zixun_header_view,null);
        zixun_xbanner = ((XBanner) headerView.findViewById(R.id.zixun_xbanner));

        zixun_listview.addHeaderView(headerView);   //添加头视图

        //为xbanner绑定数据
        zixun_xbanner.setData(mList,null);
        //为xbanner适配数据
        zixun_xbanner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                Glide.with(getContext()).load(mList.get(position)).into((ImageView) view);
            }
        });

        zixun_listview.setAdapter(list_adapter);

        //设置xbanner的页面切换效果
        zixun_xbanner.setPageTransformer(Transformer.Accordion);
        //设置xbanner
        zixun_xbanner.setPageChangeDuration(1000);

    }



    private void initData() {
        mList=new ArrayList<>();
        beanList=new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mList.add(R.mipmap.ic_launcher);
            ZiXunBean bean=new ZiXunBean();
            bean.setTitle("莫虚言");
            bean.setContent("这世界有太多伤感，太多悲情，太多无奈，却又无力回天");
            beanList.add(bean);
        }
        list_adapter=new ZiXunListViewAdapter(beanList,getContext());
    }
}
