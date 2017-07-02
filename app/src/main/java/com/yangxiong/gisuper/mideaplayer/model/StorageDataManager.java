package com.yangxiong.gisuper.mideaplayer.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseArray;

import com.yangxiong.gisuper.mideaplayer.global.LogUtil;

/**
 * Created by yangxiong on 2017/6/16.
 */

public class StorageDataManager {
    private static final String TAG = "StorageDataManager";
    private static StorageDataManager instance;
    private Context context;
    private final ContentResolver mResolver;

    private StorageDataManager(Context context) {
        this.context = context;
        mResolver = context.getContentResolver( );
    }

    public static StorageDataManager getInstance(Context context) {
        if (instance == null) {
            synchronized (LogUtil.class) {
                if (instance == null) {
                    instance = new StorageDataManager(context);
                }
            }
        }
        return instance;
    }

    private String[] mImages = new String[]{
            "https://drscdn.500px.org/photo/127892951/h%3D600_k%3D1_a%3D1/3487a549dbbe46e2d803a37281543322",
            "https://drscdn.500px.org/photo/127893495/h%3D600_k%3D1_a%3D1/8462ac67727eecd23c104612ab998633",
            "https://drscdn.500px.org/photo/127891921/h%3D600_k%3D1_a%3D1/c5aec47c6c924d733f58cec483dc41a6",
            "https://drscdn.500px.org/photo/127900863/h%3D600_k%3D1_a%3D1/e63c59888014392bac32cfb9c383bb9e",
            "https://drscdn.500px.org/photo/127870627/h%3D600_k%3D1_a%3D1/df562860314d42dd9a4f8bf4ee0ac0e5",
    };
    /**
     * 获取所有的图片
     *
     * @return SparseArray<ImageBean>
     */
    public SparseArray<ImageBean> getImageList() {
        SparseArray<ImageBean> list = new SparseArray<>( );
        int i = 0;
        for (; i < mImages.length; i++) {
            ImageBean imageBean = new ImageBean();
            imageBean.isLocal = false;
            imageBean.path = mImages[i];
            imageBean.id =""+ i;
            imageBean.name = "name"+i;
            list.put(i,imageBean);
        }
        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA};
        String orderBy = MediaStore.Images.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = mResolver.query(uri, projection, null, null, orderBy);
        if (null == cursor) {
            LogUtil.d(TAG, "getImageList:  cursor is null");
            return null;
        }

        while (cursor.moveToNext( )) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String path = cursor.getString(2);

            ImageBean imageBean = new ImageBean( );
            imageBean.autoId = i;
            imageBean.id = id;
            imageBean.name = name;
            imageBean.path = path;
            imageBean.isLocal = true;
            list.put(i++, imageBean);

        }
        LogUtil.d(TAG, "listSize: " + list.size( ));
        return list;
    }

    public SparseArray<AudioBean> getAudioList() {
        SparseArray<AudioBean> list = new SparseArray<>( );

        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE,MediaStore.Audio.Media.TITLE};
        String orderBy = MediaStore.Audio.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = mResolver.query(uri, projection, null, null, orderBy);
        if (null == cursor) {
            LogUtil.d(TAG, "getImageList:  cursor is null");
            return null;
        }
        int i = 0;
        while (cursor.moveToNext( )) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String path = cursor.getString(2);
            String tittle = cursor.getString(4);
            AudioBean audioBean = new AudioBean( );
            audioBean.autoId = i++;
            audioBean.id = id;
            audioBean.name = name;
            audioBean.path = path;
            audioBean.tittle = tittle;
            list.put(audioBean.autoId, audioBean);

        }
        LogUtil.d(TAG, "listSize: " + list.size( ));
        return list;
    }
    public SparseArray<VideoBean> getVideoList() {
        SparseArray<VideoBean> list = new SparseArray<>( );

        String[] projection = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.SIZE,MediaStore.Video.Media.TITLE};
        String orderBy = MediaStore.Video.Media.ARTIST;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = mResolver.query(uri, projection, null, null, orderBy);
        if (null == cursor) {
            LogUtil.d(TAG, "getImageList:  cursor is null");
            return null;
        }
        int i = 0;
        while (cursor.moveToNext( )) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String path = cursor.getString(2);
            String tittle = cursor.getString(4);
            VideoBean videoBean = new VideoBean( );
            videoBean.autoId = i++;
            videoBean.id = id;
            videoBean.name = name;
            videoBean.path = path;
            videoBean.tittle = tittle;
            list.put(videoBean.autoId, videoBean);

        }
        LogUtil.d(TAG, "listSize: " + list.size( ));
        return list;
    }

}
