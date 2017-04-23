package adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yysh.officialcar.PhotoViewActivity;
import com.example.yysh.officialcar.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import uri.Uri;

/**
 * Created by Lspring on 2017/4/17.
 */

public class GalleryFinalPhotoBackAdapter extends BaseAdapter{
    private List<PhotoInfo> mPhtoList;
    private Activity context;
    private ArrayList<String> photoList;
    private boolean isShow;
    public GalleryFinalPhotoBackAdapter(List<PhotoInfo> mPhtoList, Activity context) {
        this.mPhtoList = mPhtoList;
        this.context = context;
    }
    public void addList(List<PhotoInfo> mList){
        photoList=new ArrayList<>();
        mPhtoList.addAll(mList);
        for (int i = 0; i < mPhtoList.size(); i++) {
            String path=mPhtoList.get(i).getPhotoPath();
            photoList.add(path);
        }
        notifyDataSetChanged();
    }

    public void setIsShow(boolean isShow1){
        isShow=isShow1;
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
    public int getCount() {
        return mPhtoList!=null?mPhtoList.size():0;
    }

    @Override
    public PhotoInfo getItem(int position) {
        return mPhtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GridViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.galleryfinal_photo_item,null);
            holder=new GridViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (GridViewHolder) convertView.getTag();
        }
        PhotoInfo info=mPhtoList.get(position);
        final String photoPath = info.getPhotoPath();
        Log.e("photoPath",photoPath);
        Glide.with(context).load(photoPath).into(holder.ig);
        if(isShow){
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhtoList.remove(position);
                    photoList.remove(position);
                   // Log.e("删除后",mPhtoList.size()+"");
                    notifyDataSetChanged();
                    if(mPhtoList.size()==0){
                        callback.deleteAll();
                    }
                }
            });
        }else{
            holder.delete.setVisibility(View.GONE);
            notifyDataSetChanged();
        }
        if(photoList.size()>0) {
            final int pos=position;
            //点击图片跳转到单独查看大图的界面
            holder.ig.setOnClickListener(new View.OnClickListener() {
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

        final GridViewHolder finalHolder = holder;
        holder.ig.setOnLongClickListener(new View.OnLongClickListener() {
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

        return convertView;
    }


    static class GridViewHolder{
        ImageView ig;
        ImageView delete;
        public GridViewHolder(View view){
            ig= ((ImageView) view.findViewById(R.id.photo_img));
            delete= ((ImageView) view.findViewById(R.id.photo_delete));
        }
    }

}
