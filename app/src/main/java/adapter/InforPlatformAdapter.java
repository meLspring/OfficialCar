package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yysh.officialcar.R;

import java.util.List;

import bean.InforPlatformBean;
import bean.ZiXunBean;

/**
 * Created by Lspring on 2017/4/13.
 */

public class InforPlatformAdapter extends BaseAdapter{
    private List<InforPlatformBean> mList;
    private Context context;

    public InforPlatformAdapter(List<InforPlatformBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    @Override
    public InforPlatformBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InforPlatforHolder  holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.infor_platform_list_item,null);
            holder=new InforPlatforHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (InforPlatforHolder) convertView.getTag();
        }
        InforPlatformBean inforPlatformBean = mList.get(position);
        holder.title.setText(inforPlatformBean.getTitle());
        holder.content.setText(inforPlatformBean.getContent());
        return convertView;
    }



    static class InforPlatforHolder{
        ImageView ig;
        TextView title;
        TextView content;
        public InforPlatforHolder(View view){
            ig=((ImageView) view.findViewById(R.id.infor_platform_list_item_ig));
            title=((TextView) view.findViewById(R.id.infor_platform_list_item_title));
            content=((TextView) view.findViewById(R.id.infor_platform_list_item_content));
        }
    }
}
