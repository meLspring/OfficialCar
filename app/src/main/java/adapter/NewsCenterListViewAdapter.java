package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yysh.officialcar.R;

import java.util.ArrayList;

import bean.InformationBean;

/**
 * Created by Lspring on 2017/3/22.
 */

public class NewsCenterListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<InformationBean> informationBeanArrayList;
    private Context context;
    private LayoutInflater inflater;
    public NewsCenterListViewAdapter(ArrayList<InformationBean> informationBeanArrayList,Context context){
        this.informationBeanArrayList=informationBeanArrayList;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.news_center_listview_item,parent,false);
        NewsCenterViewHolder holder=new NewsCenterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InformationBean bean=informationBeanArrayList.get(holder.getAdapterPosition());
        NewsCenterViewHolder newsCenterViewHolder= (NewsCenterViewHolder) holder;
        newsCenterViewHolder.time.setText(bean.getTime());
        newsCenterViewHolder.newsCenter_info.setText(bean.getInformation());
    }

    @Override
    public int getItemCount() {
        return informationBeanArrayList!=null?informationBeanArrayList.size():0;
    }

    static class NewsCenterViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView newsCenter_info;
        public NewsCenterViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.news_center_time);
            newsCenter_info = (TextView) itemView.findViewById(R.id.news_center_infor);
        }
    }

}
