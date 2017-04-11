package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.yysh.officialcar.R;

import java.util.List;

/**
 * Created by Lspring on 2017/4/11.
 */

public class AddImgVideoAdapter extends BaseAdapter{
    private List<Bitmap> imgList;
    private Context context;
    public AddImgVideoAdapter(List<Bitmap> imgList,Context context){
        this.imgList=imgList;
        this.context=context;
    }
    @Override
    public int getCount() {
        return imgList!=null?imgList.size():0;
    }

    @Override
    public Bitmap getItem(int position) {
        return imgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.add_gridview,null);
            holder=new GridViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (GridViewHolder) convertView.getTag();
        }
        holder.img.setImageBitmap(imgList.get(position));
        return convertView;
    }

    static class GridViewHolder{
        ImageView img;
        public GridViewHolder(View view){
            img=((ImageView) view.findViewById(R.id.add_img));
        }
    }
}
