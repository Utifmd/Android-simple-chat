package com.app.utif.android_simple_chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.utif.android_simple_chat.R;
import com.app.utif.android_simple_chat.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by utif on 6/5/17.
 */

public class AdapterUsr extends RecyclerView.Adapter<AdapterUsr.ViewHolderUsr>{
    public static String ONLINE = "online";
    public static String OFFLINE= "offline";

    private List<User> mUserList;
    private Context mCtx;

    private String mUid;
    private Long mTime;

    public AdapterUsr(Context ctx, List<User> list){
        mCtx = ctx;
        mUserList = list;
    }

    @Override
    public ViewHolderUsr onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderUsr(mCtx, LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_usr, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolderUsr holder, int position) {
        User model = mUserList.get(position);

        holder.getSender().setText(model.getSender());

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void setCurrenUsr(String uid, Long time){
        mUid = uid;
        mTime = time;
    }

    public void refill(User model){
        mUserList.add(model);
        notifyDataSetChanged();
    }

    public void cleanUp(){
        mUserList.clear();
    }

    public class ViewHolderUsr extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context ctxViewHolder;
        private TextView sender;
        private CircleImageView avaPro;

        public ViewHolderUsr(Context ctx, View itemView){
            super(itemView);

            ctxViewHolder = ctx;
            sender = (TextView) itemView.findViewById(R.id.senderName);
            itemView.setOnClickListener(this);
        }
        public TextView getSender(){
            return sender;
        }
        @Override
        public void onClick(View v) {
            //Intent in = new Intent(this, ChatActivity.class);
        }
    }
}
