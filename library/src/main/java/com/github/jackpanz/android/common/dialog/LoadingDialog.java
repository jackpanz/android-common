package com.github.jackpanz.android.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.jackpanz.android.common.R;

/**
 * Created by tjy on 2017/6/19.
 */
public class LoadingDialog extends Dialog{

    public TextView messageText = null;

    public TextView getMessageText() {
        return messageText;
    }

    public void setMessageText(TextView messageText) {
        this.messageText = messageText;
    }

    public void setMessage(String message){
        this.messageText.setText(message);
    }


    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder{

        private Context context;
        private String message;
        private boolean isShowMessage=false;
        private boolean isCancelable=false;
        private boolean isCancelOutside=false;


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示信息
         * @param message
         * @return
         */

        public Builder setMessage(String message){
            this.message = message;
            if( message != null && message.length() > 0 ){
                setShowMessage(true);
            }
            return this;
        }

        /**
         * 设置是否显示提示信息
         * @param isShowMessage
         * @return
         */
        public Builder setShowMessage(boolean isShowMessage){
            this.isShowMessage=isShowMessage;
            return this;
        }

        /**
         * 设置是否可以按返回键取消
         * @param isCancelable
         * @return
         */

        public Builder setCancelable(boolean isCancelable){
            this.isCancelable=isCancelable;
            return this;
        }

        /**
         * 设置是否可以取消
         * @param isCancelOutside
         * @return
         */
        public Builder setCancelOutside(boolean isCancelOutside){
            this.isCancelOutside=isCancelOutside;
            return this;
        }

        public LoadingDialog create(){

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_loading,null);
            TextView msgText= view.findViewById(R.id.tipTextView);
            msgText.setText(isShowMessage?message:"");
            msgText.setVisibility(isShowMessage?View.VISIBLE:View.GONE);

            LoadingDialog loadingDailog = new LoadingDialog(context,R.style.MyDialogStyle);
            loadingDailog.setMessageText(msgText);
            loadingDailog.setContentView(view);
            loadingDailog.setCancelable(isCancelable);
            loadingDailog.setCanceledOnTouchOutside(isCancelOutside);
            return  loadingDailog;

        }


    }
}
