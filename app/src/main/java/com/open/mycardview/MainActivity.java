package com.open.mycardview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LinearLayout llEcardWallet;
    private RelativeLayout rvBottomView;

    private List<View> views;
    private View viewBottom;

    private View bottom_card;
    private boolean isOpenState = false;//默认没有展开
    private int topCard = 0;//那张卡在最上面
    Context context;
    private int cardMarginHeight = -100;


    private int recycleMoveHeight = 110;
    private int recycleBeginMarginHeight = 110;
    /**
     * 设置卡包  卡包界面
     */
    private void setCardInfoData() {

        llEcardWallet.removeAllViews();

        if (views != null && views.size() > 0) {
            views.clear();
        } else {
            views = new ArrayList<>();
        }
//        LogUtil.d("卡包里面的个数 = " + views.size());
        int size =3;
//        int size = res.size();
        if (size > 3) {
            size = 2;
        }
        if (size > 1) {
            isOpenState = false;
            setViewMargin(rvBottomView, true, 0, 0, -recycleBeginMarginHeight, 0);
            moveView(rvBottomView, recycleMoveHeight, 0, 0);//底部recycleview向上移动
        }
        for (int i = 0; i < size; i++) {
            View walletViewitem = View.inflate(this, R.layout.item_wallet_me_card, null);
//            VipCardInfoBean item = res.get(i);

            LinearLayout ll_recharge = walletViewitem.findViewById(R.id.ll_recharge);
            LinearLayout ll_ticket_top = walletViewitem.findViewById(R.id.ll_ticket_top);
            TextView iv_change_pwd = walletViewitem.findViewById(R.id.iv_change_pwd);
            LinearLayout ll_integration_top = walletViewitem.findViewById(R.id.ll_integration_top);
            LinearLayout money_top = walletViewitem.findViewById(R.id.money_top);
            TextView card_name_top = walletViewitem.findViewById(R.id.card_name_top);
            TextView card_num_top = walletViewitem.findViewById(R.id.card_num_top);
//            setTextViewStyles(card_name_top);


            TextView tv_money = walletViewitem.findViewById(R.id.tv_money);
            money_top.setTag(i);
            ll_ticket_top.setTag(i);
            ll_recharge.setTag(i);
            ll_integration_top.setTag(i);

            tv_money.setTag(i);
            TextView integration_top_tv = walletViewitem.findViewById(R.id.integration_top_tv);
            TextView ticket_top_tv = walletViewitem.findViewById(R.id.ticket_top_tv);
            TextView money_top_tv = walletViewitem.findViewById(R.id.money_top_tv);

            iv_change_pwd.setTag(i);
            card_num_top.setTag(i);
            integration_top_tv.setTag(i);
            ticket_top_tv.setTag(i);
            money_top_tv.setTag(i);

            RelativeLayout rl_ecard_root = walletViewitem.findViewById(R.id.rl_ecard_root);
            LinearLayout card_top_container = walletViewitem.findViewById(R.id.card_top_container);
            views.add(walletViewitem);
            llEcardWallet.addView(walletViewitem);

            if (i == 0) {
                rl_ecard_root.setBackgroundResource(R.mipmap.me_e_card1);
            } else if (i == 1) {
                rl_ecard_root.setBackgroundResource(R.mipmap.me_e_card2);
            } else if (i % 3 == 2) {
                rl_ecard_root.setBackgroundResource(R.mipmap.me_e_card3);
            }

            walletViewitem.setTag(i);
            rl_ecard_root.setTag(i);
            card_top_container.setTag(i);
            if (size > 1) {
                rl_ecard_root.setOnClickListener(onCardClickListener);
                card_top_container.setOnClickListener(onCardClickListener);
//                setViewMargin(recyclerView, true, 0, 0, cardMarginHeight, 0);
            }


            iv_change_pwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                }
            });
            ll_integration_top.setTag(i);
            ll_integration_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtils.showCenter("积分");
                }
            });
            money_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtils.showCenter("储值卡");
                    int position = (int) v.getTag();

                }
            });
            ll_ticket_top.setTag(i);
            ll_ticket_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                }
            });
            ll_recharge.setTag(i);
            ll_recharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtils.showCenter("充值");
                    int position = (int) v.getTag();
                }
            });

        }


        //手动再添加一个view 用于撑大电子钱包布局
        bottom_card = View.inflate(this, R.layout.bottom_card, null);
        LinearLayout card_bottom = bottom_card.findViewById(R.id.card_bottom);
        llEcardWallet.addView(card_bottom);


        for (int i = 0; i < views.size(); i++) {
            if (i != 0) {
                setViewMargin(views.get(i), true, 0, 0, cardMarginHeight, 0);
            }
        }


    }

    private int cardMoveHeight = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        llEcardWallet = findViewById(R.id.ll_ecard_wallet);
        rvBottomView = findViewById(R.id.rv_bottom_view);

        viewBottom = findViewById(R.id.view_bottom);

        setCardInfoData();

    }
    /**
     * 根据手机的分辨率从 dp 的单位转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    View.OnClickListener onCardClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
//            VipCardInfoBean vipCardInfoBean = res.get(position);
//            CardMoneyBean cardMoneyBean = vipCardInfoBean.getCardMoneyBean();
            Intent intent;
            switch (v.getId()) {
                case R.id.card_top_container://
//                    itemAllOnclick(position);
                    cardOnclick(position);
//                    ToastUtils.showCenter("点击card_top_container");
                    break;
            }

        }
    };

    private void cardOnclick(int tag) {
//        ToastUtils.showCenter("点击 = " + tag);
        if (isOpenState) {//展开
            viewBottom.setVisibility(View.GONE);
        } else {//收起
            viewBottom.setVisibility(View.VISIBLE);
        }
        //当前收起状态
        if (!isOpenState) {

            //展开卡包
            for (int i = 0; i < views.size(); i++) {
//                LogUtil.d("展开卡包 点击第" + tag + " 个" + ",当前第 i" + i);
                if (i > tag) {
//                    LogUtil.d("向下移动 第 " + i + "个 距离 " + recycleMoveHeight);
                    //向下移动
                    moveView(views.get(i), 0, recycleMoveHeight);
                } else if (i < tag) {

                    //向下移动
//                    LogUtil.d("向下移动 i < tag 第 " + i + "个 距离 " + recycleMoveHeight);
                    moveView(views.get(i), 0, recycleMoveHeight + cardMoveHeight);
                } else if ((i == tag)) {
//                    LogUtil.d("第 " + i + "个 向上移动  距离 -" + recycleMoveHeight* i);
                    //向上移动
                    moveView(views.get(i), 0, -cardMoveHeight * i);
                }
            }
            isOpenState = true;
            //让view展开
            moveView(rvBottomView, 0, recycleMoveHeight);//底部recycleview向下移动
        } else if (tag == topCard && isOpenState) {

            //点击卡片和顶部的相同就收起卡包
            for (int i = 0; i < views.size(); i++) {
                if (i > tag) {
                    //向上移动
                    moveView(views.get(i), recycleMoveHeight, 0);
                } else if (i < tag) {
                    //向下移动
                    moveView(views.get(i), recycleMoveHeight + cardMoveHeight, 0);
                } else if ((i == tag)) {
                    //向下移动
                    moveView(views.get(i), -cardMoveHeight * i, 0);
                }
            }
            //当前展开状态 让view收起
            isOpenState = false;
            moveView(rvBottomView, recycleMoveHeight, 0);//底部recycleview向上移动
        } else {
            //交换卡包
//            LogUtil.d("交换卡片 点击第" + tag + " 个 顶部是第" + topCard + "个 ");
            changeItem(topCard, tag);


        }
        topCard = tag;
//        isShowChangePWD(isOpenState);
    }

    /**
     * 切换
     *
     * @param topCard
     * @param onclickCard
     */
    private void changeItem(int topCard, int onclickCard) {
//        LogUtil.d("切换条目 topCard = " + topCard + " onclickCard = " + onclickCard);
        //处理顶部
        switch (topCard) {
            case 0:
                moveView(views.get(topCard), 0, recycleMoveHeight + cardMoveHeight + 5);
                break;
            case 1:
                if (onclickCard == 2) {
                    moveView(views.get(topCard), -cardMoveHeight, recycleMoveHeight + cardMoveHeight);
                } else {
                    moveView(views.get(topCard), -cardMoveHeight, recycleMoveHeight + cardMoveHeight * onclickCard);
                }
                break;
            case 2://调试

                if (onclickCard == 0) {
                    moveView(views.get(0), recycleMoveHeight, -cardMoveHeight);
                    moveView(views.get(1), recycleMoveHeight + cardMoveHeight, recycleMoveHeight);
                } else if (onclickCard == 1) {
//                    moveView(views.get(1), recycleMoveHeight, -cardMoveHeight);
//                    moveView(views.get(0), changeThreeHeight, -changeOneHeight);
//                    moveView(views.get(topCard),  - recycleMoveHeight, recycleMoveHeight);
                }
                moveView(views.get(topCard), -recycleMoveHeight, recycleMoveHeight);
                break;
        }
        //处理点击
        switch (onclickCard) {
            case 0:
                moveView(views.get(onclickCard), recycleMoveHeight + cardMoveHeight + 5, 0);
                break;
            case 1:
                if (topCard == 2) {
                    moveView(views.get(onclickCard), recycleMoveHeight + cardMoveHeight, -cardMoveHeight);
                } else {
                    moveView(views.get(onclickCard), recycleMoveHeight, -cardMoveHeight);
                }
//                moveView(views.get(1), changeThreeHeight, -changeTwoHeight);//正常
                break;
            case 2://调试
                moveView(views.get(onclickCard), recycleMoveHeight, -cardMoveHeight * onclickCard);
//                moveView(views.get(2), moveThreeHeight, -moveThreeHeight);
                if (topCard == 0) {
                    moveView(views.get(1), recycleMoveHeight, recycleMoveHeight + cardMoveHeight);
                } else if (topCard == 1) {
//                    moveView(views.get(0), changeThreeHeight, changeOneHeight);
                }
                break;
        }
    }

    /**
     * 移动动画
     *
     * @param view
     * @param begin
     * @param end
     */
    private void moveView(View view, int begin, int end) {
        final ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", getPxFromDpi(begin), getPxFromDpi(end));
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        animator1.setStartDelay(100);//设置动画延迟执行
        animator1.setDuration(500);//设置动画时间
        animator1.start();
    }

    /**
     * 移动动画
     *
     * @param view
     * @param begin
     * @param end
     */
    private void moveView(View view, int begin, int end, int duration) {
        final ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", getPxFromDpi(begin), getPxFromDpi(end));
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
//        animator1.setStartDelay(100);//设置动画延迟执行
        animator1.setDuration(duration);//设置动画时间
        animator1.start();
    }

    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param isDp   需要设置的数值是否为DP
     * @param left   左边距
     * @param right  右边距
     * @param top    上边距
     * @param bottom 下边距
     * @return
     */
    public ViewGroup.LayoutParams setViewMargin(View view, boolean isDp, int left, int right, int top, int bottom) {
        if (view == null) {
            return null;
        }

        int leftPx = left;
        int rightPx = right;
        int topPx = top;
        int bottomPx = bottom;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            //不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }

        //根据DP与PX转换计算值
        if (isDp) {
            leftPx = getPxFromDpi(left);
            rightPx = getPxFromDpi(right);
            topPx = getPxFromDpi(top);
            bottomPx = getPxFromDpi(bottom);
        }
        //设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx);
        view.setLayoutParams(marginParams);
        return marginParams;
    }

    private int getPxFromDpi(int left) {
        final int i = dip2px(getApplicationContext(), left);
        Log.d(TAG, "getPxFromDpi: i = " + i);
        return i;
    }


}