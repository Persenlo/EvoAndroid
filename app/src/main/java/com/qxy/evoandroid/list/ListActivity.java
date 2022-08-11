package com.qxy.evoandroid.list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.qxy.evoandroid.Adapter.ListAdapter;
import com.qxy.evoandroid.BaseActivity;
import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ActivityListBinding;
import com.qxy.evoandroid.douyinapi.TokenUtil;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
import com.qxy.evoandroid.model.RankVersion;
import com.qxy.evoandroid.model.RankVersion.VersionData;
import com.qxy.evoandroid.model.UserInfo;
import com.qxy.evoandroid.model.VideoRank;
import com.qxy.evoandroid.request.ApiService;
import com.qxy.evoandroid.userLogin.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class ListActivity extends BaseActivity {

    private ListViewModel listViewModel;
    private ActivityListBinding binding;
    private String cToken;
    private String userToken;
    private String userOpenId;
    private SharedPreferences sp;
    private ApiService apiService;
    private Retrofit checkRetrofit;
    private int select_type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        listViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ListViewModel.class);
        binding.setData(listViewModel);
        binding.setLifecycleOwner(this);

        init();

        //initSpinner();

        initToolbar();

        initRecyclerView();
    }

    private void initSpinner() {
        listViewModel.getVersionLiveData().observe(this, new Observer<VersionData>() {
            @Override
            public void onChanged(VersionData versionData) {
                //创建Spinner条目
                List<String> spinnerList=new ArrayList<>();
                spinnerList.add("历史榜单");
                List<VersionData.VersionListDTO> list=versionData.getList();
                for(VersionData.VersionListDTO data:list){
                    spinnerList.add(getSpinnerItem(data.getStartTime(),data.getEndTime()));
                }
                //配置spinner适配器adapter
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(ListActivity.this,android.R.layout.simple_spinner_item,spinnerList);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                binding.listSp.setAdapter(adapter);
                //Spinner切换条目点击事件
                binding.listSp.setSelection(0);
                binding.listSp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int position=binding.listSp.getSelectedItemPosition();
                        if(position!=0) {
                            String version = list.get(position - 1).getVersion();
                            listViewModel.getListData(cToken, select_type, Integer.valueOf(version));
                        }
                    }
                });
            }
        });

    }

    //解析时间
    private String getSpinnerItem(String startTime,String endTime){
        StringBuilder sb=new StringBuilder();
        String[] start=startTime.split(" ");
        String[] end=endTime.split(" ");
        String[] startDate=start[0].split("-");
        String[] endDate=end[0].split("-");
        sb.append(startDate[1]).append(".").append(startDate[2])
                .append("-")
                .append(endDate[1]).append(".").append(endDate[2]);
        return sb.toString();
    }

    private void initToolbar() {
        binding.tbList.setNavigationIcon(R.drawable.back);
        binding.tbList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        switch (select_type) {
            case 1:
                binding.tlList.setVisibility(View.VISIBLE);
                binding.tbList.setTitle(R.string.rank_rank_name);
                break;
            case 2:
                binding.tbList.setTitle(R.string.rank_teleplay_name);
                break;
            case 3:
                binding.tbList.setTitle(R.string.rank_variety_name);
                break;
            default:
                break;
        }
    }

    private void initRecyclerView() {
        listViewModel.getDataLiveData().observe(this, new Observer<VideoRank.DataDTO>() {
            @Override
            public void onChanged(VideoRank.DataDTO dataDTO) {
                binding.listRank.setText(dataDTO.getActiveTime());
                binding.rvList.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                binding.rvList.addItemDecoration(new DividerItemDecoration(ListActivity.this,DividerItemDecoration.VERTICAL));
                binding.rvList.setAdapter(new ListAdapter(dataDTO, ListActivity.this));
            }
        });
    }

    private void init() {
        sp = this.getSharedPreferences("userToken.xml", 0);

        //获取令牌
        TokenUtil tokenUtil = new TokenUtil(this);
        cToken = tokenUtil.getClientToken();
        userToken = tokenUtil.getToken();
        userOpenId = tokenUtil.getOpenId();

        select_type = getIntent().getIntExtra("SELECT_TYPE", 0);

        //初始化Retrofit
        checkRetrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        apiService = checkRetrofit.create(ApiService.class);

        if (cToken.equals("empty")) {
            getClientTokenFromReq();
        } else {
            Call<VideoRank> videoRank = apiService.getVideoRank(cToken, select_type, 0);
            RetrofitUtil.enqueue(videoRank, new ResponseCallback<VideoRank>() {
                @Override
                public void onSuccess(VideoRank videoRank) {
                    if (videoRank.getData().getErrorCode().equals("0")) {
                        //token有效，进行下一项检测
                        Message message = new Message();
                        message.what = Constant.TOKEN_GET_COMPLETE;
                        handler.sendMessage(message);
                    } else {
                        //token无效，尝试获取
                        getClientTokenFromReq();
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });

            Call<RankVersion> rankVersion=apiService.getRankVersion(cToken,select_type,10);
            RetrofitUtil.enqueue(rankVersion, new ResponseCallback<RankVersion>() {
                @Override
                public void onSuccess(RankVersion rankVersion) {
                    if (rankVersion.getData().getErrorCode().equals("0")) {
                        //token有效，进行下一项检测
                        Message message = new Message();
                        message.what = Constant.TOKEN_GET_COMPLETE;
                        handler.sendMessage(message);
                    } else {
                        //token无效，尝试获取
                        getClientTokenFromReq();
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    //测试获取用户信息
    private void checkUserInfo() {
        Call<UserInfo> userInfo = apiService.getUserInfo(userToken, userToken, userOpenId);
        RetrofitUtil.enqueue(userInfo, new ResponseCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                if (userInfo.getData().getErrorCode() == 0) {
                    //已登录
                    listViewModel.getListData(cToken, select_type,0);
                    listViewModel.getVersion(cToken,select_type);
                } else {
                    //未登录
                    Toast.makeText(ListActivity.this, R.string.login_login_first, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ListActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //通过发送请求获取Token
    private void getClientTokenFromReq() {
        //获取Retrofit
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        ApiService apiService = retrofit.create(ApiService.class);
        //请求ClientToken
        Call<ResponseBody> call = apiService.getClientToken(Constant.CLIENT_KEY, Constant.CLIENT_SECRET, Constant.CLIENT_TYPE);
        RetrofitUtil.enqueue(call, new ResponseCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody body) {
                //请求成功，开始解析
                try {
                    getCTokenFromJson(body.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //解析JSON
    private void getCTokenFromJson(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            JSONObject data = jsonObject.getJSONObject("data");
            cToken = data.getString("access_token");
            //保存ClientToken
            SharedPreferences.Editor spEdit = sp.edit();
            spEdit.putString("client_token", cToken);
            spEdit.commit();
            //进行下一项检测
            Message message = new Message();
            message.what = Constant.TOKEN_GET_COMPLETE;
            handler.sendMessage(message);

        } catch (JSONException e) {
            Log.e("TokenUtil", "JsonErr:" + e);
        }
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.TOKEN_GET_COMPLETE:
                    checkUserInfo();
                    break;
                case Constant.TOKEN_GET_FAIL:
                    break;
            }
        }
    };

}
