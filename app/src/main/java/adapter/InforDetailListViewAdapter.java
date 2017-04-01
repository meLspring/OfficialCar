package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yysh.officialcar.R;

import java.util.List;

import bean.InforDetailBean;

/**
 * Created by Lspring on 2017/3/23.
 */

public class InforDetailListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<InforDetailBean> beanList;
    private Context context;
    private LayoutInflater inflater;
    public InforDetailListViewAdapter(List<InforDetailBean> beanList,Context context){
        this.beanList=beanList;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.detail_listview_item,parent,false);
        DetailViewHolder holder=new DetailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DetailViewHolder viewHolder= (DetailViewHolder) holder;
        InforDetailBean bean=beanList.get(holder.getAdapterPosition());
        viewHolder.userName.setText(bean.getDetail_userName());
        viewHolder.content.setText(bean.getShowContent());
        boolean tag=bean.isDetail_linear();
        if(tag){        //说明有图片视频或者音频
            viewHolder.BigLinear.setVisibility(View.VISIBLE);
            viewHolder.show_linear.setVerticalGravity(LinearLayout.VERTICAL);
            for (int i=0;i<3;i++) {
                ImageView ig = new ImageView(context);
                ig.setLayoutParams(new ViewGroup.LayoutParams(120, 120));
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                ig.setImageBitmap(bitmap);
                viewHolder.show_linear.addView(ig);
            }
        }else{
            viewHolder.BigLinear.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return beanList!=null?beanList.size():0;
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView content;
        LinearLayout BigLinear;
        LinearLayout show_linear;
        public DetailViewHolder(View itemView) {
            super(itemView);
            userName= (TextView) itemView.findViewById(R.id.detail_userName);
            content= (TextView) itemView.findViewById(R.id.showContent);
            BigLinear= (LinearLayout) itemView.findViewById(R.id.detail_BigLinear);
            show_linear= (LinearLayout) itemView.findViewById(R.id.show_linear);
        }
    }
}
