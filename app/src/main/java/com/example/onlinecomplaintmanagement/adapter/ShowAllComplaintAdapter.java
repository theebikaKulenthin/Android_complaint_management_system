package com.example.onlinecomplaintmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlinecomplaintmanagement.R;
import com.example.onlinecomplaintmanagement.model.complaint;

import java.util.ArrayList;
import java.util.List;

public class ShowAllComplaintAdapter extends RecyclerView.Adapter<ShowAllComplaintAdapter.viewholder> {

    private Context mContext;
    private List<complaint> mList = new ArrayList<>();

    public ShowAllComplaintAdapter(Context mContext, List<complaint> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.complaint_list_item, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        complaint complaint = mList.get(position);
        Glide.with(mContext).load(complaint.getImage()).into(holder.iv_complaintImage);
        holder.tv_department.setText(complaint.getDepartment());
        holder.tv_description.setText(complaint.getDescription());
        holder.tv_status.setText("status : " + complaint.getStatus());
        holder.tv_reply.setText("reply : " + complaint.getReply());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        ImageView iv_complaintImage;
        TextView tv_department, tv_description, tv_status, tv_reply;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            iv_complaintImage = itemView.findViewById(R.id.iv_complaintImage);
            tv_department = itemView.findViewById(R.id.tv_department);
            tv_description = itemView.findViewById(R.id.tv_complaintDescription);
            tv_status = itemView.findViewById(R.id.tv_complaintStatus);
            tv_reply = itemView.findViewById(R.id.tv_complaintReply);

        }
    }
}
