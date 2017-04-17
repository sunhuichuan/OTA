package com.yao.ota.app.feed;

/**
 * FeedLayout的更新配置
 * Created by huichuan on 2017/4/17.
 */
public interface IMainFeedLayoutConfig {


    /**
     * app列表的pageId
     */
    public static final String PAGE_ID_MAIN_FEED_APP = "main_feed_app_list";
    /**
     * 工具页面的pageId
     */
    public static final String PAGE_ID_TOOLS = "tools_page";



    /**
     * 更新配置
     * @param updateConfig
     */
    public void update(UpdateConfig updateConfig);


    /**
     * 更新配置
     */
    public static class UpdateConfig{
        //需要显示的PageId
        private String showingPageId;

        public UpdateConfig(String pageId){
            this.showingPageId = pageId;
        }

        public String getShowingPageId() {
            return showingPageId;
        }
    }


}
