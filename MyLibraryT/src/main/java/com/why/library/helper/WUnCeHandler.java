package com.why.library.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import com.why.library.WBaseApplication;
import com.why.library.tool.WLogTool;
import android.os.Environment;
import android.os.Looper;

/**
 * @ClassName WUnCeHandler
 * @author  WangHuanyu
 * @todo 全局异常处理
 * @date 2016/11/9 10:21
 */
public class WUnCeHandler implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler mDefaultHandler;
    private final String TAG = "CatchExcep";
    WBaseApplication application;
  //用来存储设备信息和异常信息  
    private Map<String, String> infos = new HashMap<String, String>();
    //用于格式化日期,作为日志文件名的一部分  
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private String crashPath = "/sdcard/wanghuanyu/Error/";


    public WUnCeHandler(WBaseApplication application){
         //获取系统默认的UncaughtException处理器    
         mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
         this.application = application;  
    }  
      
    @Override  
    public void uncaughtException(Thread thread, Throwable ex) {      
        if(!handleException(ex) && mDefaultHandler != null){   
            //如果用户没有处理则让系统默认的异常处理器来处理    
            mDefaultHandler.uncaughtException(thread, ex);
        }else{
            saveCrashInfo2File(ex);
            System.exit(0);
        }    
    }  
      
    /**  
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.  
     *   
     * @param ex  
     * @return true:如果处理了该异常信息;否则返回false.  
     */    
    private boolean handleException(Throwable ex) {    
        if (ex == null) {    
            return false;    
        }    
        //使用Toast来显示异常信息    
        new Thread(){    
            @Override    
            public void run() {    
                Looper.prepare();    
//                WToast.showTextToast(application.getApplicationContext(), "程序异常,即将关闭");
                Looper.loop();    
            }   
        }.start();    
        return true;    
    }

    /**
     * 存入crash信息
     * @param ex
     * @return
     */
    private String saveCrashInfo2File(Throwable ex) {  
        
        StringBuffer sb = new StringBuffer();  
        for (Map.Entry<String, String> entry : infos.entrySet()) {  
            String key = entry.getKey();  
            String value = entry.getValue();  
            sb.append(key + "=" + value + "\n");  
        }  
          
        Writer writer = new StringWriter();  
        PrintWriter printWriter = new PrintWriter(writer);  
        ex.printStackTrace(printWriter);  
        Throwable cause = ex.getCause();  
        while (cause != null) {  
            cause.printStackTrace(printWriter);  
            cause = cause.getCause();  
        }  
        printWriter.close();  
        String result = writer.toString();  
        sb.append(result);  
        try {  
            long timestamp = System.currentTimeMillis();  
            String time = formatter.format(new Date(timestamp));  
            String fileName = "crash-" + time + "-" + timestamp + ".txt";  
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
                File dir = new File(crashPath);
                if (!dir.exists()) {  
                    dir.mkdirs();  
                }  
                FileOutputStream fos = new FileOutputStream(crashPath + fileName);
                fos.write(sb.toString().getBytes());  
                fos.close();  
            }  
            return fileName;  
        } catch (Exception e) {  
            WLogTool.e(TAG, "an error occured while writing file...");
        }  
        return null;  
    }  
}  
