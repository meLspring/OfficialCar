package adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yysh.officialcar.R;

import java.util.ArrayList;

import bean.PhoneBean;

/**
 * Created by Lspring on 2017/3/22.
 */

public class PhoneListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int ITEM_TYPE_HEADER = 0;   //头布局标识
    public static final int ITEM_TYPE_CONTENT = 1;  //内容item标识
    private int mHeaderCount=1;//头部View个数

    private ArrayList<PhoneBean> phoneBeanArrayList;
    private Context context;
    private LayoutInflater inflater;
    public PhoneListViewAdapter(ArrayList<PhoneBean> phoneBeanArrayList,Context context){
        inflater=LayoutInflater.from(context);
        this.phoneBeanArrayList=phoneBeanArrayList;
        this.context=context;
    }

    //item总个数
    public int getContentItemCount(){
        return phoneBeanArrayList.size();
    }
    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }
    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int count=getContentItemCount();    //先获取总长度
        if (isHeaderView(position)) {
            //头部View
            return ITEM_TYPE_HEADER;
        }else{
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_TYPE_HEADER){
            View headerView=inflater.inflate(R.layout.phone_recycler_headview,parent,false);
            HeaderViewHolder headerViewHolder=new HeaderViewHolder(headerView);
            return headerViewHolder;
        }
        View view=inflater.inflate(R.layout.phone_listview_item,parent,false);
        PhoneViewHolder holder=new PhoneViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PhoneViewHolder) {
            PhoneViewHolder holder1 = (PhoneViewHolder) holder;
            PhoneBean bean = phoneBeanArrayList.get(position);
            holder1.name.setText(bean.getPhone_name());
            holder1.phone.setText(bean.getPhone_number());
            holder1.rescue.setText(bean.getPhone_rescue());

        }
        if(holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder= (HeaderViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return phoneBeanArrayList!=null?phoneBeanArrayList.size():0;
    }

    //单条布局item
    static class PhoneViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView phone;
        TextView rescue;
        public PhoneViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.phone_name);
            phone= (TextView) itemView.findViewById(R.id.phone_number);
            rescue= (TextView) itemView.findViewById(R.id.phone_rescue);
        }
    }
    //头部布局item
    static class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
