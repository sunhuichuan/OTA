package com.yao.ota.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.yao.dependence.event.WeiXinLoginEvent;
import com.yao.dependence.sso.WeixinSDK;
import com.yao.devsdk.log.LoggerUtil;
import com.yao.devsdk.utils.SdkUtil;

import de.greenrobot.event.EventBus;

/**
 * 只是为了接收微信分享和微信支付回调
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";
    private IWXAPI api = WeixinSDK.getInstance().getWxapi();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv_content = new TextView(this);
        setContentView(tv_content);
        LoggerUtil.e(TAG, "onCreate了了了了了");
        processData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LoggerUtil.e(TAG, "onNewIntent了了了了了");
        setIntent(intent);
        processData();
    }

    void processData(){
        Intent intent = getIntent();
        if (intent!=null){
            api.handleIntent(intent, this);
        }
    }

    @Override
    public void onReq(BaseReq req) {

        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }

        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        LoggerUtil.e(TAG,"微信返回的数据为："+resp);


        if(resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            LoggerUtil.d(TAG,"onPayFinish,errCode="+resp.errCode);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("啥啥啥");
        }




        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                SdkUtil.showDebugToast("微信请求成功");

                requestOk(resp);


                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                SdkUtil.showDebugToast("微信请求取消");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                SdkUtil.showDebugToast("微信认证失败");
                break;
            default:
                SdkUtil.showDebugToast("微信其他错误请求");
                break;
        }

        finish();
    }


    void requestOk(BaseResp resp){
        if (resp == null || !(resp instanceof SendAuth.Resp)){
            return;
        }

        SendAuth.Resp authResponse = (SendAuth.Resp) resp;

        int errorCode = authResponse.errCode;
        if (errorCode == BaseResp.ErrCode.ERR_OK){
            String code = authResponse.code;
            String openId = authResponse.openId;
            String state = authResponse.state;
            String lang = authResponse.lang;
            String country = authResponse.country;

            EventBus.getDefault().post(new WeiXinLoginEvent(errorCode,code,openId,state,lang,country));

        }

    }



    private void goToGetMsg() {

        SdkUtil.showDebugToast("被微信呼起，这是什么鬼");

//        Intent intent = new Intent(this, GetFromWXActivity.class);
//        intent.putExtras(getIntent());
//        startActivity(intent);
//        finish();
    }

    private void goToShowMsg(ShowMessageFromWX.Req showReq) {

        SdkUtil.showDebugToast("显示微信消息，这又是什么鬼");


//        WXMediaMessage wxMsg = showReq.message;
//        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
//
//        StringBuffer msg = new StringBuffer(); // ×éÖ¯Ò»¸ö´ýÏÔÊ¾µÄÏûÏ¢ÄÚÈÝ
//        msg.append("description: ");
//        msg.append(wxMsg.description);
//        msg.append("\n");
//        msg.append("extInfo: ");
//        msg.append(obj.extInfo);
//        msg.append("\n");
//        msg.append("filePath: ");
//        msg.append(obj.filePath);
//
//        Intent intent = new Intent(this, ShowFromWXActivity.class);
//        intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
//        intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
//        intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
//        startActivity(intent);
//        finish();
    }
}