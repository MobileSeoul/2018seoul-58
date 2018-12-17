package com.walk.angel.angelwalk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;

public abstract class DaumMapSchemeURL {

    public static final String DAUMMAP_PACKAGE_NAME = "net.daum.android.map";

    public static final String DAUMMAP_DOWNLOAD_PAGE = "https://play.google.com/store/apps/details?id=net.daum.android.map";


    private Context mContext;
    private Intent mIntent;

    public DaumMapSchemeURL(Context context, Intent intent){
        this.mContext = context;
        this.mIntent = intent;
    }

    /**
     * myp scheme을 처리할 수 있는 어플리케이션이 존재하는지 검사
     * @return 사용가능할 경우 true
     */
    public boolean canOpenDaummapURL() {
        PackageManager pm = mContext.getPackageManager();
        List infos = pm.queryIntentActivities(mIntent, PackageManager.MATCH_DEFAULT_ONLY);

        return infos != null && infos.size() > 0;
    }

    /**
     * 다음맵 설치 여부 검사
     * @return 설치되어 있을 경우 true
     */
    public boolean existDaummapApp() {
        PackageManager pm = mContext.getPackageManager();

        try {
            return (pm.getPackageInfo(DAUMMAP_PACKAGE_NAME, PackageManager.GET_SIGNATURES) != null);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 다음맵 다운로드 페이지열기
     */
    public static void openDaummapDownloadPage(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(DAUMMAP_DOWNLOAD_PAGE));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
