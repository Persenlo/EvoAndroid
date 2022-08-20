package com.qxy.evoandroid.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.qxy.evoandroid.R;

import java.util.List;

/**
 * DialogP（自定义Dialog）
 * Last update:2022.8
 * author:Persenlo
 */
public class DialogP extends Dialog {

    /**
     * DialogP显示模式
     * 0:一个按钮
     * 1：两个按钮
     * 2：可编辑文本
     */

    public static final int DIALOG_P_EASY=0;
    public static final int DIALOG_P_NORMAL=1;
    public static final int DIALOG_P_EDIT=2;

    //默认显示单按钮
    private int dialogType=0;

    //组件
    private TextView title;
    private TextView message;
    private Button positiveButton;
    private Button negativeButton;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;

    public onButtonClickListener onButtonClickListener;



    //可编辑内容
    private String titleP;
    private String messageP;
    private String posBtnP;
    private String negBtnP;
    private String hintP;

    Context context;


    //构造方法，绑定style
    public DialogP(@NonNull Context context, int dialogType) {
        super(context,R.style.DialogP);
        this.context=context;
        this.dialogType=dialogType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_p);
        //可从空白处关闭
        setCanceledOnTouchOutside(false);
        initView();
        refreshView();
        initEvent();
    }





    private void initView() {
        title=findViewById(R.id.tv_dialog_p_title);
        message=findViewById(R.id.tv_dialog_p_message);
        positiveButton=findViewById(R.id.bt_dialog_p_confirm);
        negativeButton=findViewById(R.id.bt_dialog_p_cancel);
        textInputLayout=findViewById(R.id.et_dialog_p_layout);
        textInputEditText=findViewById(R.id.et_dialog_p_text);


    }

    private void refreshView() {
        if(dialogType==0){

            negativeButton.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);
            textInputEditText.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(titleP))
                title.setText(titleP);
            if(!TextUtils.isEmpty(messageP))
                message.setText(messageP);
            if (!TextUtils.isEmpty(posBtnP))
                positiveButton.setText(posBtnP);

        }else if(dialogType==1){

            textInputLayout.setVisibility(View.GONE);
            textInputEditText.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(titleP))
                title.setText(titleP);
            if(!TextUtils.isEmpty(messageP))
                message.setText(messageP);
            if (!TextUtils.isEmpty(posBtnP))
                positiveButton.setText(posBtnP);
            if(!TextUtils.isEmpty(negBtnP))
                negativeButton.setText(negBtnP);

        }else if(dialogType==2){

            message.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(titleP))
                title.setText(titleP);
            if(!TextUtils.isEmpty(hintP))
                textInputLayout.setHint(hintP);
            if (!TextUtils.isEmpty(posBtnP))
                positiveButton.setText(posBtnP);
            if(!TextUtils.isEmpty(negBtnP))
                negativeButton.setText(negBtnP);

        }else return;
    }

    public void show(){
        super.show();
        refreshView();
    }

    //处理按钮事件
    private void initEvent() {
        //确定
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onButtonClickListener!=null)
                    onButtonClickListener.onPositiveButtonClick();
            }
        });
        //取消
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onButtonClickListener!=null)
                    onButtonClickListener.onNegativeButtonClick();
            }
        });
    }

    public interface onButtonClickListener{

        void onPositiveButtonClick();

        void onNegativeButtonClick();
    }

    public DialogP setOnButtonClickListener(onButtonClickListener onButtonClickListener){
        this.onButtonClickListener=onButtonClickListener;
        return this;
    }

    public DialogP setTitle(String title){
        this.titleP=title;
        return this;
    }

    public DialogP setMessage(String message){
        this.messageP=message;
        return this;
    }

    public DialogP setPosBtnText(String text){
        this.posBtnP=text;
        return this;
    }

    public DialogP setNegBtnText(String text){
        this.negBtnP=text;
        return this;
    }

    public DialogP setHint(String hint){
        this.hintP=hint;
        return this;
    }

    //设置是否从空白处关闭
    public DialogP setCanCancel(boolean canCancel){
        this.setCanceledOnTouchOutside(canCancel);
        this.setCancelable(canCancel);
        return this;
    }



    public String getText(){
        return textInputEditText.getText().toString();
    }
}
