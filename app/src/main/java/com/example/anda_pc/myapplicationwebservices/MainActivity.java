package com.example.anda_pc.myapplicationwebservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anda_pc.myapplicationwebservices.data.model.Post;
import com.example.anda_pc.myapplicationwebservices.data.remote.APIService;
import com.example.anda_pc.myapplicationwebservices.data.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mResponseTv;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText titleEt =findViewById(R.id.et_title);
        final EditText bodyEt =  findViewById(R.id.et_body);
        Button submitBtn =findViewById(R.id.btn_submit);
        mResponseTv = findViewById(R.id.tv_response);

        mAPIService = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEt.getText().toString().trim();
                String body = bodyEt.getText().toString().trim();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body)) {
                    /*updatePost(title, body);*/
                    deletePost();
                }else{
                    getPost();
                }
            }
        });
    }

    public void sendPost(String title, String body) {

        // RxJava
        mAPIService.savePost(title, body, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Post>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Post post) {
                        showResponse(post.toString());
                        Log.i(TAG, "post submitted to API. " + post.toString());
                    }
                });

  /*      Post test = new Post(title,body,1);
        mAPIService.savePost2(test).enqueue(new Callback<Post>() {

            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API. " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });*/
    }


    public void updatePost(String title, String body){
        mAPIService.updatePost(1,title, body, 1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API. " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void deletePost(){
        mAPIService.deletePost(1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "delete post submitted to API. " + response.body().toString());
                }else{
                    Log.i(TAG, "fail gg");

                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.i(TAG, "fail ");
            }
        });
    }

    public void getPost(){
        mAPIService.doGetListResources(50).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "delete post submitted to API. " + response.body().toString());
                }else{
                    Log.i(TAG, "fail gg");

                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.i(TAG, "fail ");
            }
        });
    }

    public void showResponse(String response) {
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }
}
