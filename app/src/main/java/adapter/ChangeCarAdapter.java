package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yysh.officialcar.R;

import java.util.List;

import bean.ChangeCarBean;

/**
 * Created by Lspring on 2017/4/13.
 */

public class ChangeCarAdapter extends BaseAdapter{
    private List<ChangeCarBean> chageCarList;
    private Context context;

    public ChangeCarAdapter(List<ChangeCarBean> chageCarList, Context context) {
        this.chageCarList = chageCarList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return chageCarList!=null?chageCarList.size():0;
    }

    @Override
    public ChangeCarBean getItem(int position) {
        return chageCarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChageCarViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.change_car_list_item,null);
            holder=new ChageCarViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ChageCarViewHolder) convertView.getTag();
        }
        ChangeCarBean changeCarBean = chageCarList.get(position);
        holder.textView.setText(changeCarBean.getCarNumber());
        return convertView;
    }

    static class ChageCarViewHolder{
        TextView textView;
        public ChageCarViewHolder(View view){
            textView= (TextView) view.findViewById(R.id.chage_car_number);
        }
    }
}
