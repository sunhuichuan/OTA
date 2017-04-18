package com.yao.ota.app.feed.utils;

import com.yao.ota.app.feed.model.OtaAppInfo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * app列表解析工具
 * Created by huichuan on 2017/4/17.
 */
public class AppFeedParseUtils {



    /**
     * app类型的名字集合
     * @param document
     * @return
     */
    public static List<String> parseAppTypeNameList(Document document){

        Element body = document.body();
        Element appTypeTable = body.getElementsByTag("table").get(0);
        Element appTypeList = appTypeTable.getElementsByTag("tr").get(0).getElementsByTag("td").get(1);
//        LoggerUtil.i(TAG,"appTypeList:"+appTypeList);

        Elements appTypeNameSpanList = appTypeList.getElementsByTag("h1").get(0).getElementsByTag("span");
        int appTypeNameSize = appTypeNameSpanList.size();
        List<String> appTypeNameList = new ArrayList<>();
        for (int i=0;i<appTypeNameSize;i++){
            Element elementSpan = appTypeNameSpanList.get(i);
            Elements elementsAppName = elementSpan.getElementsByTag("a");
            if (elementsAppName.isEmpty()){
                appTypeNameList.add(elementSpan.text());
            }else{
                String appTypeName = elementsAppName.get(0).text();
                appTypeNameList.add(appTypeName);

            }
        }

        return appTypeNameList;
    }


    /**
     * 解析app信息集合
     * @param document
     * @return
     */
    public static List<OtaAppInfo> parseAppList(Document document){
        Element body = document.body();
        Element appTypeTable = body.getElementsByTag("table").get(1);
        Elements appTypeList = appTypeTable.getElementsByTag("tbody").get(0).getElementsByTag("tr");
//        LoggerUtil.i(TAG,"appTypeList:"+appTypeList);

        int appListSize = appTypeList.size();
        List<OtaAppInfo> appList = new ArrayList<>();
        //第0行是标题，所以跳过
        for (int i=1;i<appListSize;i++){
            Element elementApp = appTypeList.get(i);
            OtaAppInfo appInfo = new OtaAppInfo();
            Elements appAttribute = elementApp.getElementsByTag("td");
            String versionName = appAttribute.get(0).text();
            String publishTime = appAttribute.get(1).text();
            String downloadUrl = appAttribute.get(3).getElementsByTag("a").get(0).attributes().get("href");
            String appDescription = appAttribute.get(4).text();
            appInfo.setAppVersionName(versionName);
            appInfo.setPublishTime(publishTime);
            appInfo.setAppDownloadUrl(downloadUrl);
            appInfo.setAppDescription(appDescription);
            appList.add(appInfo);
        }

        return appList;
    }
}
