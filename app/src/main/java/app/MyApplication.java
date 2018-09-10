package app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.HashSet;

/**
 * Created by linyd on 2018/9/10.
 */

public class MyApplication extends Application {
    public static final String TAG = "app";
    public static Context mContext = null;
    public static HashSet<Activity> allActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"++++++++++ MyApplication onCreate ++++++++++");
        mContext = this;
        init();

    }

    private void init() {
        allActivity =  new HashSet<>();
    }

    public static Context getContext(){
        return mContext;
    }

    public static void addActivity(Activity activity){
        allActivity.add(activity);
    }

    public static void removeActivty(Activity activity){
        allActivity.remove(activity);
    }

    public static void exitApp(){
        synchronized (allActivity){
            for(Activity activity : allActivity){
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }



}
