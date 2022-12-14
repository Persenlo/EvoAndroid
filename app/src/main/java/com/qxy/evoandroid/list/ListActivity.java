package com.qxy.evoandroid.list;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.qxy.evoandroid.Adapter.ListAdapter;
import com.qxy.evoandroid.BaseActivity;
import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ActivityListBinding;
import com.qxy.evoandroid.douyinapi.TokenUtil;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
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
    private int select_type=1;
    private int version=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        listViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ListViewModel.class);
        binding.setData(listViewModel);
        binding.setLifecycleOwner(this);

        setDarkStatusBar();

        init();

        initToolbar();

        initObserver();

    }

    private void initObserver() {
        listViewModel.getDataLiveData().observe(this, new Observer<>() {
            @Override
            public void onChanged(VideoRank.DataDTO dataDTO) {
                binding.listRank.setText("?????????|" + dataDTO.getActiveTime());
                //RecyclerView??????
                binding.rvList.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                binding.rvList.addItemDecoration(new DividerItemDecoration(ListActivity.this,DividerItemDecoration.VERTICAL));
                binding.rvList.setAdapter(new ListAdapter(dataDTO, ListActivity.this));
            }
        });
        //?????????????????????
        listViewModel.getVersionLiveData().observe(this, new Observer<>() {
            @Override
            public void onChanged(VersionData versionData) {
                //??????Spinner??????
                List<String> spinnerList=new ArrayList<>();
                spinnerList.add("????????????");
                List<VersionData.VersionListDTO> list=versionData.getList();
                for(VersionData.VersionListDTO data:list){
                    spinnerList.add(getSpinnerItem(data.getStartTime(),data.getEndTime()));
                }
                //??????spinner?????????adapter
                ArrayAdapter<String> adapter=new ArrayAdapter<>(ListActivity.this,android.R.layout.simple_spinner_item,spinnerList);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                binding.listSp.setAdapter(adapter);
                //Spinner????????????????????????
                binding.listSp.setSelection(0);
                binding.listSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        int position=binding.listSp.getSelectedItemPosition();
                        if(position!=0) {
                            version = Integer.parseInt(list.get(position - 1).getVersion());
                            //????????????????????????????????????,???????????????
                            if(!NetUtils.isNet(ListActivity.this)&&!listViewModel.getCacheRepository().isExistRankCache(select_type,version)){
                                binding.listNoNet.setVisibility(View.VISIBLE);
                                binding.rvList.setVisibility(View.GONE);
                            }
                            else {
                                binding.listNoNet.setVisibility(View.GONE);
                                binding.rvList.setVisibility(View.VISIBLE);
                                listViewModel.getListData(cToken, select_type, version);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
        //??????????????????
        binding.listTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                select_type=tab.getPosition()+1;
                if(!NetUtils.isNet(ListActivity.this)&&!listViewModel.getCacheRepository().isExistRankCache(select_type,version)){
                    binding.listNoNet.setVisibility(View.VISIBLE);
                    binding.rvList.setVisibility(View.GONE);
                }
                else {
                    binding.listNoNet.setVisibility(View.GONE);
                    binding.rvList.setVisibility(View.VISIBLE);
                    listViewModel.getListData(cToken,select_type,version);
                    listViewModel.getVersion(cToken,select_type);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.listTl.addTab(binding.listTl.newTab().setText(R.string.rank_movie_name));
        binding.listTl.addTab(binding.listTl.newTab().setText(R.string.rank_teleplay_name));
        binding.listTl.addTab(binding.listTl.newTab().setText(R.string.rank_variety_name));
    }

    //????????????
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

    //??????Toolbar??????
    private void initToolbar() {
        binding.listTb.setNavigationIcon(R.drawable.back);
        binding.listTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //????????????????????????
        binding.listTb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.rank_clear_cache){
                    AlertDialog.Builder alertDialog=new AlertDialog.Builder(ListActivity.this);
                    alertDialog.setTitle("????????????????????????");
                    alertDialog.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listViewModel.getCacheRepository().cleanAllCache();
                        }
                    });
                    alertDialog.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }
                return false;
            }
        });
    }

    private void init() {
        sp = this.getSharedPreferences("userToken.xml", 0);

        //????????????
        TokenUtil tokenUtil = new TokenUtil(this);
        cToken = tokenUtil.getClientToken();
        userToken = tokenUtil.getToken();
        userOpenId = tokenUtil.getOpenId();


        //????????????????????????cToken???????????????????????????????????????????????????
        //?????????Retrofit
        Retrofit checkRetrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        apiService = checkRetrofit.create(ApiService.class);

        if (cToken.equals("empty")) {
            getClientTokenFromReq();
        } else {
            Call<VideoRank> videoRank = apiService.getVideoRank(cToken, select_type, 0);
            RetrofitUtil.enqueue(videoRank, new ResponseCallback<>() {
                @Override
                public void onSuccess(VideoRank videoRank) {
                    if (videoRank.getData().getErrorCode().equals("0")) {
                        //token??????????????????????????????
                        Message message = new Message();
                        message.what = Constant.TOKEN_GET_COMPLETE;
                        handler.sendMessage(message);
                    } else {
                        //token?????????????????????
                        getClientTokenFromReq();
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    //????????????????????????
    private void checkUserInfo() {
        Call<UserInfo> userInfo = apiService.getUserInfo(userToken, userToken, userOpenId);
        RetrofitUtil.enqueue(userInfo, new ResponseCallback<>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                if (userInfo.getData().getErrorCode() == 0) {
                    //?????????
                    listViewModel.getListData(cToken, select_type,version);
                    listViewModel.getVersion(cToken,select_type);
                } else {
                    //?????????
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

    //????????????????????????Token
    private void getClientTokenFromReq() {
        //??????Retrofit
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        ApiService apiService = retrofit.create(ApiService.class);
        //??????ClientToken
        Call<ResponseBody> call = apiService.getClientToken(Constant.CLIENT_KEY, Constant.CLIENT_SECRET, Constant.CLIENT_TYPE);
        RetrofitUtil.enqueue(call, new ResponseCallback<>() {
            @Override
            public void onSuccess(ResponseBody body) {
                //???????????????????????????
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

    //??????JSON
    private void getCTokenFromJson(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            JSONObject data = jsonObject.getJSONObject("data");
            cToken = data.getString("access_token");
            //??????ClientToken
            SharedPreferences.Editor spEdit = sp.edit();
            spEdit.putString("client_token", cToken);
            spEdit.apply();
            //?????????????????????
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
