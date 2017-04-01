package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yysh.officialcar.InforPlatformDetailActivity;
import com.example.yysh.officialcar.R;

import java.util.ArrayList;

import bean.InformationBean;

/**
 * Created by Lspring on 2017/3/22.
 */

public class InformationListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<InformationBean> informationBeanArrayList;
    private Context context;
    private LayoutInflater inflater;
    public InformationListViewAdapter(ArrayList<InformationBean> informationBeanArrayList,Context context){
        this.informationBeanArrayList=informationBeanArrayList;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.information_listview_item,parent,false);
        InforViewHolder holder=new InforViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final InformationBean bean=informationBeanArrayList.get(holder.getAdapterPosition());
        InforViewHolder holder1= (InforViewHolder) holder;
        holder1.time.setText(bean.getTime());
        holder1.infor_news.setText(bean.getInformation());
        //点击事件
        holder1.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("information",bean.getInformation());
                Intent intent=new Intent(context, InforPlatformDetailActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return informationBeanArrayList!=null?informationBeanArrayList.size():0;
    }

    static class InforViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        LinearLayout linearLayout;
        TextView infor_news;
        public InforViewHolder(View itemView) {
            super(itemView);
            time= (TextView) itemView.findViewById(R.id.information_time);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.infor_linear);
            infor_news= (TextView) itemView.findViewById(R.id.infor_news);
        }
    }
}
