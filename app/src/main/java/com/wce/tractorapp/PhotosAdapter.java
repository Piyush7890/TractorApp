package com.wce.tractorapp;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickedListener listener;
    private static final int ADD = 0;
    private static final int PHOTO = 1;
    private static final int CAMERA = 2;

    private List<Uri> photos;

    public PhotosAdapter(OnItemClickedListener listener) {
        this.listener = listener;
        photos = new ArrayList<>();
    }

    public void addImage(Uri uri)
    {
        this.photos.add(uri);
        notifyItemInserted(photos.size()+1);
    }

    public List<Uri> getPhotos()
    {
        return photos;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==photos.size())
            return ADD;
        else if(position==photos.size()+1)
            return CAMERA;
        else return PHOTO;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==PHOTO)
        return new PhotoViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent,false));
        else if(viewType==CAMERA)
            return new SimpleCameraViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_camera, parent, false));
        else
        return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position<photos.size())
        Glide.with(holder.itemView.getContext()).load(photos.get(position)).into(((PhotoViewHolder) holder).photo);
    }

    public void setData(List<Uri> photos)
    {
        this.photos = photos;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return photos.size()+2;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photo  = itemView.findViewById(R.id.photo);
        }
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder{

        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAddPhotoClicked();
                }
            });

        }
    }

    public class SimpleCameraViewHolder extends RecyclerView.ViewHolder {
        public SimpleCameraViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCameraClicked();
                }
            });
        }
    }

    public interface OnItemClickedListener
    {
        void onAddPhotoClicked();
        void onCameraClicked();
    }
}
