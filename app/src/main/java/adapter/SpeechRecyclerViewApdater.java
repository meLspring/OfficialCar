package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yysh.officialcar.R;

import java.util.List;

import bean.SpeechBean;
import speech.MediaManager;

/**
 * Created by Lspring on 2017/4/19.
 */

public class SpeechRecyclerViewApdater extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<SpeechBean> recyclerList;
    private Activity activity;
    private LayoutInflater inflater;
    private View viewanim;
    private int mMinItemWith;// 设置对话框的最大宽度和最小宽度
    private int mMaxItemWith;
    // private GalleryFinalPhotoBackAdapter backAdapter;
    //private List<PhotoInfo> mList=new ArrayList<>();
    public SpeechRecyclerViewApdater(List<SpeechBean> listbean, Activity activity) {
        this.recyclerList = listbean;
        this.activity = activity;
        inflater=LayoutInflater.from(activity);
        // 获取系统宽度
        WindowManager wManager = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wManager.getDefaultDisplay().getMetrics(outMetrics);
        mMaxItemWith = (int) (outMetrics.widthPixels * 0.7f);
        mMinItemWith = (int) (outMetrics.widthPixels * 0.15f);
        //backAdapter=new GalleryFinalPhotoBackAdapter(mList,activity);
    }
    //添加一个方法，用来更新item
    public void addSpeech(SpeechBean speechBean){
        recyclerList.add(speechBean);
        notifyItemInserted(recyclerList.size()+1);
      //  notifyDataSetChanged();
    }

    private DeleteInterface callback;
    public void initInterface(DeleteInterface call){
        this.callback=call;
    }
    public interface DeleteInterface{
        //如果将单条item全部删除完，则调用该方法
        void isDeleteAll();
    }
    //提供一个方法，获取当前adapter的集合
    public List<SpeechBean> getList(){
        return  recyclerList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder=null;
        View view=inflater.inflate(R.layout.speech_item,parent,false);
        holder=new ListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ListViewHolder listViewHolder= (ListViewHolder) holder;
        listViewHolder.seconds.setText(recyclerList.get(holder.getAdapterPosition()).getTime()+"\"");
        ViewGroup.LayoutParams lParams=listViewHolder.length.getLayoutParams();
        lParams.width=(int) (mMinItemWith+mMaxItemWith/60f*recyclerList.get(holder.getAdapterPosition()).getTime());
        listViewHolder.length.setLayoutParams(lParams);

        listViewHolder.length.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 播放动画
                if (viewanim!=null) {//让第二个播放的时候第一个停止播放
                    viewanim.setBackgroundResource(R.mipmap.speech_stop);
                    viewanim=null;
                }
                viewanim = listViewHolder.play.findViewById(R.id.id_recorder_anim);
                viewanim.setBackgroundResource(R.drawable.play);
                AnimationDrawable drawable = (AnimationDrawable) viewanim
                        .getBackground();
                drawable.start();

                Log.e("speechPath",recyclerList.get(holder.getAdapterPosition()).getFilePathString());

                // 播放音频
                MediaManager.playSound(recyclerList.get(holder.getAdapterPosition()).getFilePathString(),
                        new MediaPlayer.OnCompletionListener() {

                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                viewanim.setBackgroundResource(R.mipmap.speech_stop);

                            }
                        });
            }
        });

        //长点击删除这一条语音
        listViewHolder.length.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("long","长点击");
                AlertDialog.Builder dialog=new AlertDialog.Builder(activity);
                dialog.setTitle("是否删除这条语音");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //将当前集合的这个角标元素参数，再去刷新
                        recyclerList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        dialog.dismiss();
                        dialog.cancel();
                        //判断当前集合是否为0
                        if(recyclerList.size()==0){
                            callback.isDeleteAll();     //接口回调
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return recyclerList.size();
    }


    static class ListViewHolder extends RecyclerView.ViewHolder{
        TextView seconds;// 时间
        LinearLayout length;
        View play;// 播放动画
        public ListViewHolder(View itemView) {
            super(itemView);
            seconds=((TextView) itemView.findViewById(R.id.recorder_time));
            play=itemView.findViewById(R.id.id_recorder_anim);
            length= (LinearLayout) itemView.findViewById(R.id.recorder_length);
        }
    }

}
