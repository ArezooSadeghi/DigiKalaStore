package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.ImageSliderItemBinding;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.ImageSliderHolder> {

    private Context mContext;
    private List<String> mImageUrls;

    public ImageSliderAdapter(Context context, List<String> imageUrls) {
        mContext = context;
        mImageUrls = imageUrls;
    }

    public List<String> getImageUrls() {
        return mImageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        mImageUrls = imageUrls;
    }

    @Override
    public ImageSliderHolder onCreateViewHolder(ViewGroup parent) {
        ImageSliderItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.image_slider_item,
                parent,
                false);
        return new ImageSliderHolder(binding);
    }

    @Override
    public void onBindViewHolder(ImageSliderHolder viewHolder, int position) {
        viewHolder.bindImage(mImageUrls.get(position));
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    public class ImageSliderHolder extends SliderViewAdapter.ViewHolder {

        ImageSliderItemBinding mBinding;

        public ImageSliderHolder(ImageSliderItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindImage(String url) {
            mBinding.setUrl(url);
        }
    }
}
