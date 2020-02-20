package com.example.kingdee.something.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;


import com.example.kingdee.something.R;
import com.example.kingdee.something.util.ToggleLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingdee on 2016/5/31.
 */
public class MyEditDialog extends Dialog {
    private LinearLayout mNumbers;
    private Button mCancel;
    private Button mEnsure;
    private NumberTextView curView;
    private OnButtonClickListener listener;
    private InputMethodManager imm;
    Map<Integer, String> kvTable;
    private String lastValue;
    private int lastPosition;
    private boolean showInputMethod = false;
    private boolean isInput = false;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public MyEditDialog(Context context) {
        this(context, 0);
        setTitle("修改手机号");
        showInputMethod=false;
        View view = View.inflate(context, R.layout.dialog_edit, null);
        mNumbers = (LinearLayout) view.findViewById(R.id.dialog_edit_numbers);
        for (int i = 0; i < mNumbers.getChildCount(); i++) {
            final View child = mNumbers.getChildAt(i);
            final int z=i;
            if (child instanceof NumberTextView) {
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        curView = (NumberTextView) child;
                        if (someOneChanging()) {
                            resume();
                        } else {
                            curView.requestFocus();
                            showInputMethod();
                        }
                        lastPosition=z;
                        lastValue = curView.getText().toString();
                        curView.setChanging(true);
                        curView.setText("");
                    }
                });
            }
        }
        mCancel = (Button) view.findViewById(R.id.dialog_edit_cancel);
        mEnsure = (Button) view.findViewById(R.id.dialog_edit_ensure);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCalcelClick();
                dismiss();
            }
        });
        mEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEnsureClick();
                dismiss();
            }
        });
        setContentView(view);
        setCancelable(false);
       /* Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
    }

    private void resume() {
        View childAt = mNumbers.getChildAt(lastPosition);
        if (childAt instanceof NumberTextView) {
            ((NumberTextView) childAt).setChanging(false);
            ((NumberTextView) childAt).setText(lastValue);
        }
    }

    private boolean someOneChanging() {
        for (int i = 0; i < mNumbers.getChildCount(); i++) {
            View childAt = mNumbers.getChildAt(i);
            if (childAt instanceof NumberTextView) {
                if (((NumberTextView) childAt).isChanging()) {
                    lastPosition = i;
                    return true;
                }
            }
        }
        return false;
    }

    private void showInputMethod() {
        if (imm == null) {
            imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
//        if(!imm.isActive()) {
        if (!showInputMethod) {
            ToggleLog.d("softinput", "isActive=" + imm.isActive());
            List<InputMethodInfo> inputMethodList = imm.getInputMethodList();
            for (int i = 0; i < inputMethodList.size(); i++) {
                InputMethodInfo inputMethodInfo = inputMethodList.get(i);
                String id=inputMethodInfo.getId();
                ToggleLog.d("inputmethod","id="+id);
            }
            imm.setInputMethod(curView.getApplicationWindowToken(), "com.baidu.input_mi/.ImeService");
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            ToggleLog.d("softinput", "isActive=" + imm.isActive());
            showInputMethod = true;
        }
//            imm.showSoftInput(curView,InputMethodManager.SHOW_FORCED);
//        }
    }

    public MyEditDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setNumber(String number) {
        int count = mNumbers.getChildCount();
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (count - 1 >= i) {
                View child = mNumbers.getChildAt(i);
                if (i == 0) {
                    child.requestFocus();
                }
                if (child instanceof NumberTextView) {
                    ((NumberTextView) child).setText(ch + "");
                }
            }
        }
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

    public String getNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mNumbers.getChildCount(); i++) {
            View view = mNumbers.getChildAt(i);
            if (view instanceof NumberTextView) {
                sb.append(((NumberTextView) view).getText().toString());
            }
        }
        return sb.toString();
    }

    public void clearChanging() {
        for (int i = 0; i < mNumbers.getChildCount(); i++) {
            View view = mNumbers.getChildAt(i);
            if (view instanceof NumberTextView) {
                ((NumberTextView) view).setChanging(false);
            }
        }
    }

    public void setIsInput(boolean isInput) {
        this.isInput = isInput;
        if (isInput) {
            curView = (NumberTextView)mNumbers.getChildAt(1);
            //第二位开始修改
            curView.setChanging(true);
            lastPosition = 1;
            lastValue = curView.getText().toString();
            curView.setText("");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showInputMethod();
                }
            }, 1000);
        }
    }

    public interface OnButtonClickListener {
        void onCalcelClick();
        void onEnsureClick();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_UP) {
            ToggleLog.d("keyevent", "up code=" + event.getKeyCode());
            switch (event.getKeyCode()) {
                case 8://1
                case 9://2
                case 10://3
                case 11://4
                case 12://5
                case 13://6
                case 14://7
                case 15://8
                case 16://9
                case 7://0
                case 67://X
                case 4://返回
                    if (kvTable == null) {
                        initKvTable();
                    }
                    String value = kvTable.get(event.getKeyCode());
                    if (!value.equals("删除") && !value.equals("返回")) {
                        curView.setChanging(false);
                        curView.setText(value);
                        if (isInput) {
                            //如果不是最后一位,移动到下一位
                            if (lastPosition != 10) {
                                lastPosition += 1;
                                curView = (NumberTextView) mNumbers.getChildAt(lastPosition);
                                curView.setChanging(true);
                                lastValue = curView.getText().toString();
                                curView.setText("");
                            }else{
                                curView = (NumberTextView) mNumbers.getChildAt(lastPosition);
                                lastValue = curView.getText().toString();
                            }
                        } else {
                            if (showInputMethod) {
                                ToggleLog.d("softinput", "isActive=" + imm.isActive());
                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                ToggleLog.d("softinput", "isActive=" + imm.isActive());
                                showInputMethod = false;
                            }
                        }
                    }
                    if (value.equals("返回")) {
                        if (showInputMethod) {
                            ToggleLog.d("softinput", "isActive=" + imm.isActive());
                            curView.setChanging(false);
                            curView.setText(lastValue);
                            showInputMethod = false;
                        }
                    }
                    ToggleLog.d("keyevent", "按下了" + value);
                    break;
            }
        } else {
            ToggleLog.d("keyevent", "down code=" + event.getKeyCode());
        }
        return super.dispatchKeyEvent(event);
    }

    private void initKvTable() {
        kvTable = new HashMap<>();
        kvTable.put(8, "1");
        kvTable.put(9, "2");
        kvTable.put(10, "3");
        kvTable.put(11, "4");
        kvTable.put(12, "5");
        kvTable.put(13, "6");
        kvTable.put(14, "7");
        kvTable.put(15, "8");
        kvTable.put(16, "9");
        kvTable.put(7, "0");
        kvTable.put(67, "删除");
        kvTable.put(4, "返回");
    }
}
