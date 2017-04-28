package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yysh.officialcar.R;

import java.util.ArrayList;

import bean.RepairRecordBean;

/**
 * Created by Lspring on 2017/4/26.
 */

public class RecordRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<RepairRecordBean> recordBeen;
    private Context context;

    public RecordRecyclerAdapter(ArrayList<RepairRecordBean> recordBeen,Context context){
        this.recordBeen=recordBeen;
        this.context=context;
    }
    //将集合清空
    public void clearList(){
        recordBeen.clear();
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.record_listview_item,parent,false);
        RecordViewHolder itemHolder=new RecordViewHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecordViewHolder itemHolder= (RecordViewHolder) holder;
        RepairRecordBean bean=recordBeen.get(position);
        itemHolder.number.setText(bean.getOrderNumber());
        itemHolder.name.setText(bean.getName());
        itemHolder.time.setText(bean.getTime());
        itemHolder.mileage.setText(bean.getMileage());
        itemHolder.changeMileageTable.setText(bean.getChangeMileageTable());
        itemHolder.settlement.setText(bean.getSettlement());
    }

    @Override
    public int getItemCount() {
        return recordBeen!=null?recordBeen.size():0;
    }


    static class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        TextView name;
        TextView time;
        TextView mileage;
        TextView changeMileageTable;
        TextView settlement;

        public RecordViewHolder(View itemView) {
            super(itemView);
            number= ((TextView) itemView.findViewById(R.id.orderNumber));
            name= ((TextView) itemView.findViewById(R.id.name));
            time= ((TextView) itemView.findViewById(R.id.time));
            mileage= ((TextView) itemView.findViewById(R.id.mileage));
            changeMileageTable= ((TextView) itemView.findViewById(R.id.changeMileageTable));
            settlement= ((TextView) itemView.findViewById(R.id.settlement));
        }
    }
}
