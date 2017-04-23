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

import java.util.ArrayList;
import java.util.List;

import bean.InforDetailBean;
import widget.MyGridView;

/**
 * Created by Lspring on 2017/3/23.
 */

public class InforDetailListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<InforDetailBean> beanList;
    private Context context;
    private LayoutInflater inflater;
    private List<Bitmap> bitmapList;    //添加图片到gridview的集合
    private AddImgVideoAdapter addImgVideoAdapter;
    public InforDetailListViewAdapter(List<InforDetailBean> beanList,Context context){
        this.beanList=beanList;
        this.context=context;
        inflater=LayoutInflater.from(context);
        bitmapList=new ArrayList<>();
        addImgVideoAdapter=new AddImgVideoAdapter(bitmapList,context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.infor_platform_detail_item,parent,false);
        DetailViewHolder holder=new DetailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DetailViewHolder viewHolder= (DetailViewHolder) holder;
        InforDetailBean bean=beanList.get(holder.getAdapterPosition());
        if(bean.isTitle()){         //说明是有title标题的
            viewHolder.titleLinear.setVisibility(View.VISIBLE);
            viewHolder.title.setText(bean.getTitle());
        }else{
            viewHolder.titleLinear.setVisibility(View.GONE);
        }
        if(bean.isIcon()){      //说明linear里有图片
            bitmapList.clear();
            viewHolder.iconLinaer.setVisibility(View.VISIBLE);
            viewHolder.detail_gridview.setVisibility(View.VISIBLE);
            for (int i=0;i<5;i++) {
                Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher_round);
                bitmapList.add(bitmap);
            }
            viewHolder.detail_gridview.setAdapter(addImgVideoAdapter);
            addImgVideoAdapter.notifyDataSetChanged();
        }else{
            viewHolder.iconLinaer.setVisibility(View.GONE);
           // addImgVideoAdapter.notifyDataSetChanged();
        }
        viewHolder.user.setText(bean.getUser());
        viewHolder.time.setText(bean.getTime());
        viewHolder.content.setText(bean.getContent());
    }

    @Override
    public int getItemCount() {
        return beanList!=null?beanList.size():0;
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView user;
        TextView content;
        TextView time;
        LinearLayout titleLinear;
        LinearLayout iconLinaer;
        MyGridView detail_gridview;
        public DetailViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.infor_platform_detail_item_title);
            content= (TextView) itemView.findViewById(R.id.infor_platform_detail_item_content);
            user= (TextView) itemView.findViewById(R.id.infor_platform_detail_item_user);
            time= (TextView) itemView.findViewById(R.id.infor_platform_detail_item_time);
            titleLinear= (LinearLayout) itemView.findViewById(R.id.infor_platform_detail_item_isTitle);
            iconLinaer= (LinearLayout) itemView.findViewById(R.id.infor_platform_detail_item_linear);
            detail_gridview=((MyGridView) itemView.findViewById(R.id.infor_platform_detail_item_gridview));
        }
    }
}
