package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yysh.officialcar.R;

import java.util.List;

/**
 * Created by Lspring on 2017/4/17.
 */

public class CarConditionRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<String> stringList;
    private Context context;

    public CarConditionRecyAdapter(List<String> stringList, Context context) {
        this.stringList = stringList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holderView= LayoutInflater.from(context).inflate(R.layout.condition_recy_item,parent,false);
        CarConditionHolder holder=new CarConditionHolder(holderView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CarConditionHolder carHolder= (CarConditionHolder) holder;
        String str = stringList.get(holder.getAdapterPosition());
        carHolder.title.setText(str);
        carHolder.content.setText("燕山汽修");
    }

    @Override
    public int getItemCount() {
        return stringList!=null?stringList.size():0;
    }



    static class CarConditionHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        public CarConditionHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.contion_recy_item_title);
            content= ((TextView) itemView.findViewById(R.id.contion_recy_item_content));
        }
    }
}
