package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yysh.officialcar.R;

import java.util.ArrayList;

import bean.RepairRecordBean;

/**
 * Created by Lspring on 2017/3/22.
 */

public class RecordListViewAdapter extends BaseAdapter {
    private ArrayList<RepairRecordBean> recordBeen;
    private Context context;
    public RecordListViewAdapter(ArrayList<RepairRecordBean> recordBeen,Context context){
        this.recordBeen=recordBeen;
        this.context=context;
    }
    @Override
    public int getCount() {
        return recordBeen!=null?recordBeen.size():0;
    }

    @Override
    public RepairRecordBean getItem(int position) {
        return recordBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.record_listview_item,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        RepairRecordBean bean=recordBeen.get(position);
        holder.number.setText(bean.getOrderNumber());
        holder.name.setText(bean.getName());
        holder.time.setText(bean.getTime());
        holder.mileage.setText(bean.getMileage());
        holder.changeMileageTable.setText(bean.getChangeMileageTable());
        holder.settlement.setText(bean.getSettlement());
        return convertView;
    }

    static class ViewHolder{
        TextView number;
        TextView name;
        TextView time;
        TextView mileage;
        TextView changeMileageTable;
        TextView settlement;
        public ViewHolder(View view){
            number= ((TextView) view.findViewById(R.id.orderNumber));
            name= ((TextView) view.findViewById(R.id.name));
            time= ((TextView) view.findViewById(R.id.time));
            mileage= ((TextView) view.findViewById(R.id.mileage));
            changeMileageTable= ((TextView) view.findViewById(R.id.changeMileageTable));
            settlement= ((TextView) view.findViewById(R.id.settlement));
        }
    }

}
