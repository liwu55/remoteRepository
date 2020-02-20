package com.example.kingdee.something;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kingdee.something.util.ToggleLog;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DragonActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private List<String> answerList =new ArrayList<>();
    private EditText etKey;
    private EditText etAnswer;
    private boolean isFound=false;
    private String firstKey;
    private char realEndKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragon);
        requestQueue = Volley.newRequestQueue(this);
        etKey = (EditText) findViewById(R.id.key);
        etAnswer = (EditText) findViewById(R.id.answer);
    }

    public void go(View v){
        firstKey = etKey.getText().toString();
        realEndKey = firstKey.charAt(firstKey.length() - 1);
        answerList.add(firstKey);
        search(firstKey,1);
    }

    private void search(final String key,final int level) {
        String url="http://chengyujielong.51240.com/"+ URLEncoder.encode(key)+"__chengyujielong/";
        StringRequest stringrequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(level>5){
                    return;
                }
                List<String> list=new ArrayList<>();
                char endChar = key.charAt(key.length() - 1);
                int start=response.indexOf("“"+ endChar +"”字在开头的成语");
                int end=response.lastIndexOf("以这个成语进行接龙");
                if(start==-1||end==-1){
                    ToggleLog.d("result","没有结果了");
                    return;
                }
                String checkPart=response.substring(start,end);
                int index=-1;
                while((index=checkPart.indexOf(">"+ endChar))!=-1) {
                    String answer = checkPart.substring(index + 1, index + 5);
                    if(answer.charAt(3)== realEndKey){
                        answerList.add(answer);
                        isFound=true;
                        ToggleLog.d("result","answer="+answerList);
                        return;
                    }
                    list.add(answer);
                    checkPart=checkPart.substring(index+5);
                }
                int inLevel=level;
                inLevel++;
                ToggleLog.d("result", "list" + list);
                for (int i = 0; i < list.size(); i++) {
                    if(isFound){
                        break;
                    }
                    String secondKey=list.get(i);
                    if(secondKey.charAt(0)==secondKey.charAt(3)){
                        continue;
                    }
                    while(answerList.size()>=inLevel){
                        answerList.remove(inLevel-1);
                    }
                    answerList.add(secondKey);
                    ToggleLog.d("searching","answer="+answerList);
                    search(secondKey,inLevel);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringrequest);
    }
}
