package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yysh.officialcar.ZiXunDetailActivity;

import java.util.List;

/**
 * Created by Lspring on 2017/4/12.
 */

public class ZiXunHeaderViewAdapter extends PagerAdapter{
    private List<Integer> mList;
    private Context context;

    public ZiXunHeaderViewAdapter(List<Integer> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View ret=null;
        //拿着position的位置 % 集合.size
        int newpostition=position % mList.size();
        //获取到条目要显示的内容imageview
        //Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),mList.get(newpostition));
        ImageView ig=new ImageView(context);
        ig.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        Log.e("header",mList.get(newpostition)+"");
        ig.setBackgroundResource( mList.get(newpostition));
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ZiXunDetailActivity.class);
                context.startActivity(intent);
            }
        });
        ret=ig;
        container.addView(ret);
        return ret;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(object instanceof View){
            View view= (View) object;
            container.removeView(view);
        }
    }
}
