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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.ReviewAdapter;
import com.example.digikalastore.databinding.FragmentCommentPageBinding;
import com.example.digikalastore.model.Review;
import com.example.digikalastore.viewmodel.ProductViewModel;

import java.util.List;

public class CommentPageFragment extends Fragment {
    private FragmentCommentPageBinding mBinding;
    private ProductViewModel mViewModel;


    public static CommentPageFragment newInstance() {
        CommentPageFragment fragment = new CommentPageFragment();
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
                inflater,
                R.layout.fragment_comment_page,
                container,
                false);

        initRecyclerView();

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CommentPageFragmentArgs args = CommentPageFragmentArgs.fromBundle(getArguments());
        int productId = args.getProductId();
        mViewModel.fetchReviews(productId);
        mViewModel.getReviewLiveData().observe(getViewLifecycleOwner(), new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                setupAdapter(reviews);
            }
        });

        setListener(productId);
    }


    private void initRecyclerView() {
        mBinding.recyclerViewReview.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    public void setListener(int productId) {
        mBinding.fabSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Arezoo", "Come");
                CommentPageFragmentDirections.ActionCommentPageFragmentToAddCommentFragment action =
                        CommentPageFragmentDirections.actionCommentPageFragmentToAddCommentFragment();
                action.setProductId(productId);
                NavHostFragment.findNavController(CommentPageFragment.this).navigate(action);
            }
        });
    }


    private void setupAdapter(List<Review> reviews) {
        mBinding.setReview(reviews);
        ReviewAdapter adapter = new ReviewAdapter(getContext(), reviews);
        mBinding.recyclerViewReview.setAdapter(adapter);
    }
}