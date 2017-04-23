package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yysh.officialcar.R;

import java.util.List;

import bean.ZiXunBean;

/**
 * Created by Lspring on 2017/4/12.
 */

public class ZiXunListViewAdapter extends BaseAdapter{
    private List<ZiXunBean> mList;
    private Context context;

    public ZiXunListViewAdapter(List<ZiXunBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    @Override
    public ZiXunBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ZiXunHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.zixun_list_item,null);
            holder=new ZiXunHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ZiXunHolder) convertView.getTag();
        }
        ZiXunBean ziXunBean = mList.get(position);
        holder.title.setText(ziXunBean.getTitle());
        holder.content.setText(ziXunBean.getContent());
        return convertView;
    }



    static class ZiXunHolder{
        ImageView ig;
        TextView title;
        TextView content;
        public ZiXunHolder(View view){
            ig=((ImageView) view.findViewById(R.id.zixun_list_item_ig));
            title=((TextView) view.findViewById(R.id.zixun_list_item_title));
            content=((TextView) view.findViewById(R.id.zixun_list_item_content));
        }
    }
}
