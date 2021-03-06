package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.ReviewAdapterItemBinding;
import com.example.digikalastore.event.DeleteReviewEvent;
import com.example.digikalastore.model.Review;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private Context mContext;
    private List<Review> mReviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        mContext = context;
        mReviews = reviews;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.review_adapter_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        holder.mBinding.setReview(mReviews.get(position));
        holder.mBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DeleteReviewEvent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        private ReviewAdapterItemBinding mBinding;

        public ReviewHolder(ReviewAdapterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindReview(Review review) {
            mBinding.setReview(review);
        }
    }
}
