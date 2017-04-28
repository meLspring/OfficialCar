package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yysh.officialcar.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bean.CarStatusBean;

/**
 * Created by Lspring on 2017/4/21.
 */

public class CarStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int ITEM_TYPE_HEADER = 0;   //头布局标识
    public static final int ITEM_TYPE_CONTENT = 1;  //内容item标识
    public static final int ITEM_TYPE_FOOTER = 2;   //尾布局标识
    private List<CarStatusBean> mList;
    private Context context;
    private LayoutInflater inflater;
    private int totalList;      //用来记录添加头尾布局后的总数
    private boolean headerViewShow,footerViewShow;
    private boolean isChange;       //判断当前问题状况是否可以修改
    private View headerView,footerView,itemView,changeTimeItem;
    private FooterViewHolder footerViewHolder;

    public CarStatusAdapter(List<CarStatusBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
        inflater= LayoutInflater.from(context);
    }
    //是否添加头布局
    public void setHeaderView(boolean header){
        this.headerViewShow=header;
    }
    //是否尾布局
    public void setFooterView(boolean footer){
        this.footerViewShow=footer;
    }

    //添加一个方法，当activity页面点击修改后，让足视图可见，并且radiobutton可以点击
    public void changeQuestion(boolean change){
        this.isChange=change;
        if(isChange && footerView!=null) {
            footerViewHolder.footer_linear.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0 && headerViewShow){      //说明有头布局
            //头部View
            return ITEM_TYPE_HEADER;
        }else if(position+1==getItemCount() && footerViewShow){     //说明有尾布局
            return ITEM_TYPE_FOOTER;
        }else{
            return ITEM_TYPE_CONTENT;
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_TYPE_HEADER){
             headerView=inflater.inflate(R.layout.car_status_recycler_header,parent,false);
            HeaderViewHolder headerViewHolder=new HeaderViewHolder(headerView);
            return headerViewHolder;
        }else if(viewType==ITEM_TYPE_FOOTER){
             footerView=inflater.inflate(R.layout.car_status_footerview,parent,false);
            footerViewHolder=new FooterViewHolder(footerView);
            return footerViewHolder;
        }
        else{
            itemView=inflater.inflate(R.layout.car_status_item_view,parent,false);
            ItemViewHolder itemViewHolder=new ItemViewHolder(itemView);
            return itemViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        if(!isChange && footerView!=null){
//            Log.e("gone","gone");
//            footerViewHolder.footer_linear.setVisibility(View.INVISIBLE);
//           // footerView.setVisibility(View.GONE);
//        }else if(isChange && footerView!=null){
//            Log.e("VISIBLE","VISIBLE");
//            footerViewHolder.footer_linear.setVisibility(View.VISIBLE);
//            //footerView.setVisibility(View.VISIBLE);
//        }

        if(holder instanceof FooterViewHolder){

            final FooterViewHolder footerHolder= (FooterViewHolder) holder;
            Date date=new Date();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=format.format(date);
            footerHolder.lastTime.setText(time);

                footerHolder.sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isChange=false;

                        Date date=new Date();
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time=format.format(date);
                        footerHolder.lastTime.setText(time);
                        footerHolder.footer_linear.setVisibility(View.INVISIBLE);
                        notifyDataSetChanged();
                        Toast.makeText(context, "保存", Toast.LENGTH_SHORT).show();

                    }
                });
                footerHolder.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // isChange=false;
                        footerHolder.footer_linear.setVisibility(View.INVISIBLE);
                        Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if(holder instanceof ItemViewHolder){
                ItemViewHolder itemHolder= (ItemViewHolder) holder;
                CarStatusBean carStatusBean = mList.get(position - 1);
                itemHolder.question.setText(carStatusBean.getQuestion());
                if(carStatusBean.isNormal()){
                    itemHolder.rb_normal.setChecked(true);
                    itemHolder.rb_abnormal.setChecked(false);
                }else{
                    itemHolder.rb_normal.setChecked(false);
                    itemHolder.rb_abnormal.setChecked(true);
                }
                if(isChange){       //可以点击
                    itemHolder.rb_normal.setClickable(true);
                    itemHolder.rb_abnormal.setClickable(true);
                    itemHolder.rb_normal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CarStatusBean normalBean = mList.get(position - 1);
                            String question=normalBean.getQuestion();
                            normalBean.setNormal(true);
                            normalBean.setQuestion(question);
                            mList.remove(position-1);
                            mList.add(position-1,normalBean);
                        }
                    });
                    itemHolder.rb_abnormal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CarStatusBean normalBean = mList.get(position - 1);
                            String question=normalBean.getQuestion();
                            normalBean.setNormal(false);
                            normalBean.setQuestion(question);
                            mList.remove(position-1);
                            mList.add(position-1,normalBean);
                        }
                    });
                }else{
                    itemHolder.rb_normal.setClickable(false);
                    itemHolder.rb_abnormal.setClickable(false);
                }
            }


    }

    @Override
    public int getItemCount() {
        if(headerViewShow && footerViewShow){     //有头尾视图
            totalList=mList.size()+2;
        }else if(headerViewShow && !footerViewShow){           //有头布局
            totalList=mList.size()+1;
        }else if (!headerViewShow && footerViewShow){          //有尾布局
            totalList=mList.size()+1;
        }else{                                  //只有item布局
            totalList=mList.size();
        }
        return totalList;
    }

    //item视图
    static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView question;
        RadioGroup rg;
        RadioButton rb_normal;
        RadioButton rb_abnormal;
        public ItemViewHolder(View itemView) {
            super(itemView);
            question=((TextView) itemView.findViewById(R.id.car_statis_question));
            rg=((RadioGroup) itemView.findViewById(R.id.car_status_item_rg));
            rb_normal= ((RadioButton) itemView.findViewById(R.id.car_status_rb_normal));
            rb_abnormal= ((RadioButton) itemView.findViewById(R.id.car_status_rb_abnormal));
        }

    }


    //头部布局item
    static class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }


    //足视图布局
    static class FooterViewHolder extends RecyclerView.ViewHolder{
        Button sure;
        Button cancel;
        LinearLayout footer_linear;
        TextView lastTime;
        public FooterViewHolder(View itemView) {
            super(itemView);
            sure= ((Button) itemView.findViewById(R.id.car_status_footer_sure));
            cancel= ((Button) itemView.findViewById(R.id.car_status_footer_cancel));
            footer_linear= (LinearLayout) itemView.findViewById(R.id.car_status_footer_linear);
            lastTime= (TextView) itemView.findViewById(R.id.lastTime);
        }
    }



}
