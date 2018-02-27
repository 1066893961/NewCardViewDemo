package application.app.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import swipecard.CardConfig;
import swipecard.OverLayCardLayoutManager;
import swipecard.RenRenCallback;

public class SwipeCardActivity extends AppCompatActivity {
    RecyclerView mRv;
    CommonAdapter<SwipeCardBean> mAdapter;
    List<SwipeCardBean> mDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new OverLayCardLayoutManager());
        mRv.setAdapter(mAdapter = new CommonAdapter<SwipeCardBean>(this,
                mDatas = SwipeCardBean.initDatas(), R.layout.item_swipe_card) {
            public static final String TAG = "zxt/Adapter";

            @Override
            public void convert(ViewHolder viewHolder, final SwipeCardBean swipeCardBean) {
                Log.d(TAG, "convert() called with: viewHolder = [" + viewHolder + "], swipeCardBean = [" + swipeCardBean + "]");
                viewHolder.setText(R.id.tvName, swipeCardBean.getName());
                viewHolder.setText(R.id.tvPrecent, swipeCardBean.getPostition() + " /" + mDatas.size());
                Picasso.with(SwipeCardActivity.this).load(swipeCardBean.getUrl()).into((ImageView) viewHolder.getView(R.id.iv));
                viewHolder.getView(R.id.iv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"" + swipeCardBean.getPostition(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        //初始化配置
        CardConfig.initConfig(this);
        ItemTouchHelper.Callback callback = new RenRenCallback(mRv, mAdapter, mDatas);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRv);


        findViewById(R.id.btnRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatas.add(new SwipeCardBean(100, "http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "增加的"));
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    public void AddAlphaAni(View view, float fromAlpha, float toAlpha, long durationMillis, int repeatMode, int repeatCount)
    {
        AlphaAnimation alphaAni = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAni.setDuration(durationMillis);   // 设置动画效果时间
        alphaAni.setRepeatMode(repeatMode);     // 重新播放
        alphaAni.setRepeatCount(repeatCount);   // 循环播放

        view.startAnimation(alphaAni);
    }
}
