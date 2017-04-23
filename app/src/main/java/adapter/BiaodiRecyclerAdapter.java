package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yysh.officialcar.R;

import java.util.ArrayList;

import bean.BiaodiBean;

/**
 * Created by Lspring on 2017/4/20.
 */

public class BiaodiRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int ITEM_TYPE_HEADER = 0;   //头布局标识
    public static final int ITEM_TYPE_CONTENT = 1;  //内容item标识
    public static final int ITEM_TYPE_FOOTER = 2;   //尾布局标识
    private ArrayList<BiaodiBean> biaodiBeanArrayList;
    private int totalList;      //用来记录添加头尾布局后的总数
    private boolean headerView,footerView;
    private Context context;
    private LayoutInflater inflater;

    public BiaodiRecyclerAdapter(ArrayList<BiaodiBean> biaodiBeanArrayList,Context context){
        inflater=LayoutInflater.from(context);
        this.biaodiBeanArrayList=biaodiBeanArrayList;
        this.context=context;
    }
    //是否添加头布局
    public void setHeaderView(boolean header){
         this.headerView=header;
    }
    //是否尾布局
    public void setFooterView(boolean footer){
         this.footerView=footer;
    }

    public void addList(BiaodiBean bean){
        biaodiBeanArrayList.add(bean);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if(position==0 && headerView){      //说明有头布局
            //头部View
            return ITEM_TYPE_HEADER;
        }else if(position+1==getItemCount() && footerView){
            return ITEM_TYPE_FOOTER;
        }else{
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_TYPE_HEADER){
            View headerView=inflater.inflate(R.layout.biaodi_hearder_view,parent,false);
            HeaderViewHolder headerViewHolder=new HeaderViewHolder(headerView);
            return headerViewHolder;
        }else if(viewType==ITEM_TYPE_CONTENT){
            View itemView = inflater.inflate(R.layout.biaodi_recycler_item, parent, false);
            BiaodiItenmHolder holder = new BiaodiItenmHolder(itemView);
            return holder;
        }else{
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==ITEM_TYPE_CONTENT) {
            if (holder instanceof BiaodiItenmHolder) {
                //这里因为有头布局，item角标上加1了，所以获取对应角标集合数据要减去1
                BiaodiBean biaodiBean = biaodiBeanArrayList.get(position-1);
                BiaodiItenmHolder itemHolder = (BiaodiItenmHolder) holder;
                itemHolder.values.setText(biaodiBean.getValues()+"公里");
                itemHolder.number.setText(biaodiBean.getNumber());
                itemHolder.time.setText(biaodiBean.getTime());
            }
        }
    }

    @Override
    public int getItemCount() {
        if(headerView && footerView){     //有头尾视图
             totalList=biaodiBeanArrayList.size()+2;
        }else if(headerView && !footerView){           //有头布局
             totalList=biaodiBeanArrayList.size()+1;
        }else if (!headerView && footerView){          //有尾布局
             totalList=biaodiBeanArrayList.size()+1;
        }else{                                  //只有item布局
             totalList=biaodiBeanArrayList.size();
        }
        Log.e("totalList",totalList+"");
        return totalList;
    }

    //单条布局item
    static class BiaodiItenmHolder extends RecyclerView.ViewHolder{
        TextView values;
        TextView number;
        TextView time;
        public BiaodiItenmHolder(View itemView) {
            super(itemView);
            values=((TextView) itemView.findViewById(R.id.biaodi_values));
            number=((TextView) itemView.findViewById(R.id.biaodi_number));
            time=((TextView) itemView.findViewById(R.id.biaodi_time));
        }
    }
    //头部布局item
    static class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
