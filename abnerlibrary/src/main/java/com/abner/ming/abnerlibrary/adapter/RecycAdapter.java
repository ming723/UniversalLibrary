package com.abner.ming.abnerlibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abner.ming.abnerlibrary.R;
import com.abner.ming.abnerlibrary.net.model.IModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;


public abstract class RecycAdapter <D extends IModel> extends RecyclerView.Adapter<RecycAdapter.ViewHolder> {
    private List<D> dataList;

    public abstract int getLayoutId();

    public RecycAdapter(List<D> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(),parent,false);
        RecycAdapter.ViewHolder vh = new RecycAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        createHolder(holder,dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public abstract void createHolder(RecycAdapter.ViewHolder holder, D d);

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View rootView;
        private SparseArray<View> views=new SparseArray<>();
        public ViewHolder(View itemView) {
            super(itemView);
            rootView=itemView;
        }
        public <T extends View> T get(int id){
            T view=(T)views.get(id);
            if(view==null){
                view=(T)rootView.findViewById(id);
                views.put(id,view);
            }
            return view;
        }
        //TextView设置数据
        public void setText(int viewId, String txt) {
            TextView mTextView = get(viewId);
            mTextView.setText(txt);
        }
        //设置图片
        public void setPic(Context ctx,int viewId, String url) {
            ImageView mImageView = get(viewId);
            Picasso.with(ctx).load(url).fit().error(R.drawable.ic_launcher).placeholder(mImageView.getDrawable()).memoryPolicy(MemoryPolicy.NO_CACHE).into(mImageView);
        }
    }
}
