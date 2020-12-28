package com.example.digikalastore.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.FragmentAddCommentBinding;
import com.example.digikalastore.event.DeleteReviewEvent;
import com.example.digikalastore.model.Review;
import com.example.digikalastore.viewmodel.ProductViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AddCommentFragment extends Fragment {
    private FragmentAddCommentBinding mBinding;
    private ProductViewModel mViewModel;
    private int mReviewId;

    public static AddCommentFragment newInstance() {
        AddCommentFragment fragment = new AddCommentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_add_comment,
                container,
                false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddCommentFragmentArgs args = AddCommentFragmentArgs.fromBundle(getArguments());
        int productId = args.getProductId();
        mBinding.btnSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review = mBinding.txtContentReview.getText().toString();
                String reviewerName = mBinding.txtReviewerName.getText().toString();
                String reviewerEmail = mBinding.txtReviewerEmail.getText().toString();
                int rating = mBinding.ratingBarReview.getNumStars();
                mViewModel.sendReview(productId, review, reviewerName, reviewerEmail, rating);
                mViewModel.getReviewTestLiveData().observe(getViewLifecycleOwner(), new Observer<Review>() {
                    @Override
                    public void onChanged(Review review) {
                        mReviewId = review.getId();
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void deleteReview(DeleteReviewEvent deleteReviewEvent) {
        mViewModel.deleteReview(mReviewId);
    }
}