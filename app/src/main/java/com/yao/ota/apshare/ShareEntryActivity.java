package com.yao.ota.apshare;

/**
 * 按支付宝要求，用于接收支付宝分享和支付的回调
 */
//public class ShareEntryActivity extends BaseActivity implements IAPAPIEventHandler {
//
//
//    private IAPApi apApi = AlipaySDK.getInstance().getAPApi();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        super.onCreate(savedInstanceState);
//        TextView tv_content = new TextView(this);
//        setContentView(tv_content);
//        //处理分享结果回调信息
//        processData();
//
//
//
//    }
//
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//
//    }
//
//    void processData(){
//        Intent intent = getIntent();
//        if (intent != null) {
//            apApi.handleIntent(intent, this);
//        }
//    }
//
//    @Override
//    public void onReq(BaseReq baseReq) {
//
//    }
//
//    @Override
//    public void onResp(BaseResp baseResp) {
////        int result = SharedObject.SHARE_FAILED;
//        switch (baseResp.errCode) {
//            case BaseResp.ErrCode.ERR_OK:
////                result = SharedObject.SHARE_OK;
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
////                result = SharedObject.SHARE_CANCELLED;
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
////                result = SharedObject.SHARE_FAILED;
//                break;
//        }
//        finish(baseResp);
//    }
//
//    /**
//     * finish掉此Activity并返回result
//     *
//     * @param baseResp 结束页面的返回值
//     */
//    public void finish(BaseResp baseResp) {
//        switch (baseResp.errCode) {
//            case BaseResp.ErrCode.ERR_OK:
//                SdkUtil.showToast(appContext, "分享到支付宝成功");
//                break;
//            case BaseResp.ErrCode.ERR_SENT_FAILED:
//                SdkUtil.showToast(appContext, "分享到支付宝失败");
//                break;
//        }
////        setResult(result);
//        finishSelf();
//    }
//
//}
