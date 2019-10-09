package com.base.gyh.baselib.widgets.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.gyh.baselib.R;

/**
 * Created by GUOYH on 2019/5/24.
 */
public class MyToolbar extends Toolbar implements View.OnClickListener {
    public static final int BACK = 0x10;
    public static final int MENU = 0x20;

    private Context context;
    private String text ="Title";
    private boolean aBoolean;
    private ViewHolder holder;

    public  void setTitleText(String text){
        this.text = text;
        init(context, aBoolean);
    }

    public void hideBack(boolean isHide){
        if (isHide){
            holder.my_toolbar_back.setVisibility(View.GONE);
        }else{
            holder.my_toolbar_back.setVisibility(View.VISIBLE);
        }
    }

    public MyToolbar(Context context) {
        this(context,null,0);
    }

    public MyToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyToolbar);
        aBoolean = typedArray.getBoolean(R.styleable.MyToolbar_isShowMenu, true);
        typedArray.recycle();
        init(context, aBoolean);
    }


    private void init(Context context, Boolean isShowMenu) {
        View view = LayoutInflater.from(context).inflate(R.layout.mytoolbar, this, true);
        holder = new ViewHolder(view);
        holder.my_toolbar_menu.setVisibility(isShowMenu ? View.VISIBLE : View.INVISIBLE);
        holder.my_toolbar_back.setOnClickListener(this);
        holder.my_toolbar_title.setText(text);
        holder.my_toolbar_menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (onBreakOrMenuClickListener != null) {
            if (i == R.id.my_toolbar_back) {
                onBreakOrMenuClickListener.onClick(BACK);
            } else if (i == R.id.my_toolbar_menu) {
                onBreakOrMenuClickListener.onClick(MENU);
            }
        }

    }


    private OnBreakOrMenuClickListener onBreakOrMenuClickListener;

    public void setOnBreakOrMenuClickListener(OnBreakOrMenuClickListener onBreakOrMenuClickListener) {
        this.onBreakOrMenuClickListener = onBreakOrMenuClickListener;
    }

    public interface OnBreakOrMenuClickListener {
        void onClick(int type);
    }

    public static class ViewHolder {
        public View rootView;
        public TextView my_toolbar_title;
        public ImageView my_toolbar_back;
        public ImageView my_toolbar_menu;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.my_toolbar_title = (TextView) rootView.findViewById(R.id.my_toolbar_title);
            this.my_toolbar_back = (ImageView) rootView.findViewById(R.id.my_toolbar_back);
            this.my_toolbar_menu = (ImageView) rootView.findViewById(R.id.my_toolbar_menu);
        }

    }
}
