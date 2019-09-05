package com.example.myapplication.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.example.myapplication.R;
import com.example.myapplication.bean.MusicInfo;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/3/18.
 */
public class MusiceHelper {

    private static final Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
    private static Bitmap mCachedBit = null;

    private static Bitmap getDefaultArtwork(Context context) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeResource(context.getResources(), R.mipmap.default_album, opts);
    }

    /**
     * 获取歌曲专辑图片
     *
     * @param context
     * @param song_id
     * @param album_id
     * @param allowDefault
     * @return
     */
    public static Bitmap getArtwork(Context context, long song_id, long album_id, boolean allowDefault) {
        if (album_id < 0) {
            // This is something that is not in the database, so get the album art directly
            // from the file.
            if (song_id >= 0) {
                Bitmap bm = getArtworkFromFile(context, song_id, -1);
                if (bm != null) {
                    return bm;
                }
            }
            if (allowDefault) {
                return getDefaultArtwork(context);
            }
            return null;
        }

        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
        if (uri != null) {
            InputStream in = null;
            try {
                in = res.openInputStream(uri);
                return BitmapFactory.decodeStream(in, null, sBitmapOptions);
            } catch (FileNotFoundException ex) {
                // The album art thumbnail does not actually exist. Maybe the user deleted it, or
                // maybe it never existed to begin with.
                Bitmap bm = getArtworkFromFile(context, song_id, album_id);
                if (bm != null) {
                    if (bm.getConfig() == null) {
                        bm = bm.copy(Bitmap.Config.RGB_565, false);
                        if (bm == null && allowDefault) {
                            return getDefaultArtwork(context);
                        }
                    }
                } else if (allowDefault) {
                    bm = getDefaultArtwork(context);
                }
                return bm;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                }
            }
        }
        return null;
    }

    private static Bitmap getArtworkFromFile(Context context, long songid, long albumid) {
        Bitmap bm = null;

        if (albumid < 0 && songid < 0) {
            throw new IllegalArgumentException("Must specify an album or a song id");
        }
        try {
            if (albumid < 0) {
                //根据songid查
                Uri uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            } else {
                //根据albumid查
                Uri uri = ContentUris.withAppendedId(sArtworkUri, albumid);
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (bm != null) {
            mCachedBit = bm;
        }
        return bm;
    }

    public static ArrayList<MusicInfo> getAllSong(Context context) {
        String[] projection = new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DURATION
                , MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ARTIST};
        Cursor cursor = context.getContentResolver().query(songUri, projection, null, null, null);
        ArrayList<MusicInfo> musiceList = new ArrayList();
        try {
            if (cursor != null || cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String albumId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    MusicInfo musicInfo = new MusicInfo(id, title, duration, data, albumId, artist);
                    musiceList.add(musicInfo);
                }
            }
            return musiceList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }
//    private static MusiceService sService;
//    private static void playAll(Context context, long [] list, int position, boolean force_shuffle) {
//        if (list.length == 0 || sService == null) {
//            Log.d("MusicUtils", "attempt to play empty song list");
//            // Don't try to play empty playlists. Nothing good will come of it.
//            String message = context.getString(R.string.emptyplaylist, list.length);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//            return;
//        }
////        try {
////            if (force_shuffle) {
////                sService.setShuffleMode(MediaPlaybackService.SHUFFLE_NORMAL);
////            }
////            long curid = sService.getAudioId();
////            int curpos = sService.getQueuePosition();
////            if (position != -1 && curpos == position && curid == list[position]) {
////                // The selected file is the file that's currently playing;
////                // figure out if we need to restart with a new playlist,
////                // or just launch the playback activity.
////                long [] playlist = sService.getQueue();
////                if (Arrays.equals(list, playlist)) {
////                    // we don't need to set a new list, but we should resume playback if needed
////                    sService.play();
////                    return; // the 'finally' block will still run
////                }
////            }
////            if (position < 0) {
////                position = 0;
////            }
////            sService.open(list, force_shuffle ? -1 : position);
////            sService.play();
////        } catch (RemoteException ex) {
////        } finally {
////            Intent intent = new Intent("com.android.music.INTERNALPLAYBACK_VIEWER")
////                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////            context.startActivity(intent);
////        }
//    }
}
