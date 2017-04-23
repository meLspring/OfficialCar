package adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cn.finalteam.galleryfinal.widget.zoonview.PhotoView;


/**
 * Created by Lspring on 2017/4/18.
 */

public class PhotoViewAdapter extends PagerAdapter{
    private ArrayList<String> photoList;
    private Context context;

    public PhotoViewAdapter(ArrayList<String> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View ret=null;
        String imgPath=photoList.get(position);     //得到图片地址
        PhotoView photoView=new PhotoView(context);
        Glide.with(context).load(imgPath).into(photoView);
        ret=photoView;
        container.addView(ret);
        return ret;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(object instanceof View){
            container.removeView((View) object);
        }
    }
}
