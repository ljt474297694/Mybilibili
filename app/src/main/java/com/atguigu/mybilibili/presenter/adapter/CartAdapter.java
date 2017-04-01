package com.atguigu.mybilibili.presenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.model.dao.MailBean;
import com.atguigu.mybilibili.presenter.CartDaoPresenter;
import com.atguigu.mybilibili.view.ICartDaoView;
import com.atguigu.mybilibili.view.view.AddSubView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 李金桐 on 2017/3/27.
 * QQ: 474297694
 * 功能: xxxx
 */

public class CartAdapter extends MyBaseAdapter<MailBean> implements ICartDaoView {
    private final CheckBox quanXuanCheckbox;
    private final TextView tvBianji;
    private final TextView tvPrice;
    private boolean isDelete = false;
    private final CartDaoPresenter presenter;

    public CartAdapter(Context mContext, final List<MailBean> data, final TextView tvBianji, CheckBox checkbox, TextView tvPrice) {
        super(mContext, data);
        this.datas = data;
        this.tvBianji = tvBianji;
        this.quanXuanCheckbox = checkbox;
        this.tvPrice = tvPrice;
        presenter = new CartDaoPresenter(this);
        tvBianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDelete = !isDelete;
                if (isDelete) {
                    tvBianji.setText("完成");
                } else {
                    tvBianji.setText("编辑");
                }
                notifyDataSetChanged();
            }
        });
        quanXuanCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < datas.size(); i++) {
                    MailBean bean = datas.get(i);
                    bean.setClick(quanXuanCheckbox.isChecked());
                    notifyPrice();
                    notifyDataSetChanged();
                }
            }
        });
        notifyPrice();
        quanXuanIsChick();
    }

    private void notifyPrice() {
        int price = 0;
        for (int i = 0; i < datas.size(); i++) {
            MailBean bean = datas.get(i);
            if (bean.isClick()) {
                if (bean.getId() == 1) {
                    price += 166 * bean.getNumber();
                } else {
                    price += 288 * bean.getNumber();
                }
            }
        }
        tvPrice.setText(price + "￥");
    }

    @Override
    protected BaseViewHodler setViewHolder(ViewGroup parent) {
        return new CartViewHolder(inflater.inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void getAll(ArrayList<MailBean> data) {
        this.datas = data;
    }


    class CartViewHolder extends BaseViewHodler {

        @Bind(R.id.checkbox)
        CheckBox checkbox;
        @Bind(R.id.tv_id)
        TextView tvId;
        @Bind(R.id.addsubview)
        AddSubView addsubview;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.bt_delete)
        Button btDelete;


        public CartViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void setData() {
            final MailBean bean = datas.get(getLayoutPosition());


            tvId.setText("商品:" + bean.getId());
            int price = bean.getId() == 1 ? 166 : 288;
            tvPrice.setText(price * bean.getNumber() + "￥");

            addsubview.setValue(bean.getNumber());
            addsubview.setMaxValue(100);
            if (isDelete) {
                btDelete.setVisibility(View.VISIBLE);
            } else {
                btDelete.setVisibility(View.INVISIBLE);
            }
            checkbox.setChecked(bean.isClick());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkbox.setChecked(!checkbox.isChecked());
                    bean.setClick(checkbox.isChecked());
                    quanXuanIsChick();
                    notifyPrice();
                }
            });
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, "delete", Toast.LENGTH_SHORT).show();
                    presenter.delete(bean);
                    datas.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    notifyPrice();
                    quanXuanIsChick();
                }
            });
            addsubview.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
                @Override
                public void onNumberChenge(int number) {
                    bean.setNumber(number);
                    presenter.updata(bean);
                    notifyPrice();
                    notifyItemChanged(getLayoutPosition());
                }
            });
        }
    }

    private void quanXuanIsChick() {
        for (int i = 0; i < datas.size(); i++) {
            if (!datas.get(i).isClick()) {
                quanXuanCheckbox.setChecked(false);
                return;
            }
        }
        quanXuanCheckbox.setChecked(true);
    }
}