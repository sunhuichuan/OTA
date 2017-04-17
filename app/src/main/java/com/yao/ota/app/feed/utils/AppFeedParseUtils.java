package com.yao.ota.app.feed.utils;

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

}
