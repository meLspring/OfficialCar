package widget;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Lspring on 2017/4/28.
 */
//Glide初始化配置
public class GlideConfig implements GlideModule{
    int diskSize=1024*1024*50;
    int memorySize= (int) ((Runtime.getRuntime().maxMemory())/8);       //取内存的1/8作为缓存

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //定义缓存大小
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,diskSize));
        //定义内存大小
        builder.setMemoryCache(new LruResourceCache(memorySize));
        //定义图片格式
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
