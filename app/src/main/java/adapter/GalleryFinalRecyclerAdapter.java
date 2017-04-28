package adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.GlideModule;
import com.example.yysh.officialcar.PhotoViewActivity;
import com.example.yysh.officialcar.R;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;


/**
 * Created by Lspring on 2017/4/27.
 */

public class GalleryFinalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<PhotoInfo> mPhtoList;
    private Activity context;
    private ArrayList<String> photoList;
    private boolean isShow;
    private GridViewHolder gridHolder;


    public GalleryFinalRecyclerAdapter(List<PhotoInfo> mPhtoList, Activity context) {
        this.mPhtoList = mPhtoList;
        this.context = context;
    }

    public void addList(List<PhotoInfo> mList){
        photoList=new ArrayList<>();        //将图片选择器选择的图片地址保存到集合里，传到浏览大图页面
        mPhtoList.addAll(mList);
        for (int i = 0; i < mPhtoList.size(); i++) {
            String path=mPhtoList.get(i).getPhotoPath();
            photoList.add(path);
        }
        notifyDataSetChanged();
    }

    public void setIsShow(boolean isShow1){
        isShow=isShow1;
        //gridViewHolder.delete.setVisibility(View.GONE);
        notifyDataSetChanged();
    }
    public boolean isShow(){
        return isShow;
    }

    //提供一个方法，获取当前adapter的集合
    public List<PhotoInfo> getList(){
        return  mPhtoList;
    }

    private ImgDelAllInterface callback;
    public void initInterface(ImgDelAllInterface face){
        this.callback=face;
    }
    //接口回调
    public interface ImgDelAllInterface{
        void deleteAll();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.galleryfinal_photo_item,null,false);
         gridHolder=new GridViewHolder(view);
        return gridHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final GridViewHolder gridViewHolder= (GridViewHolder) holder;
        PhotoInfo info=mPhtoList.get(position);
        final String photoPath = info.getPhotoPath();
        //gridViewHolder.ig.setTag(photoPath);
        Glide.with(context).load(photoPath)
                .thumbnail(0.2f).skipMemoryCache(false).into(gridViewHolder.ig);
        //gridViewHolder.ig.setImageBitmap(BitmapFactory.decodeFile(photoPath));
        if(isShow){
            gridViewHolder.delete.setVisibility(View.VISIBLE);
            gridViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Log.e("删除后",mPhtoList.size()+"");
                    mPhtoList.remove(position);
                    photoList.remove(position);
                    //notifyDataSetChanged();
                    notifyItemRemoved(position);

                    if(mPhtoList.size()==0){
                        callback.deleteAll();
                    }
                }
            });
        }else{
            gridViewHolder.delete.setVisibility(View.GONE);
           // notifyDataSetChanged();
        }
        if(photoList.size()>0) {
            final int pos=position;
            //点击图片跳转到单独查看大图的界面
            gridViewHolder.ig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PhotoViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("photoInfoList",photoList);
                    bundle.putInt("imgPosi",pos);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        gridViewHolder.ig.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //开启震动
                Vibrator vibrator= (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
                vibrator.vibrate(200); //震动一秒
                isShow=true;
                notifyDataSetChanged();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPhtoList!=null?mPhtoList.size():0;
    }





    static class GridViewHolder extends RecyclerView.ViewHolder{
        ImageView ig;
        ImageView delete;

        public GridViewHolder(View itemView) {
            super(itemView);
            ig= ((ImageView) itemView.findViewById(R.id.photo_img));
            delete= ((ImageView) itemView.findViewById(R.id.photo_delete));
        }
    }
}
