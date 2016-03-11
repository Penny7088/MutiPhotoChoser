package com.example.mutiphotochoser.content;

import java.util.ArrayList;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.content.AsyncTaskLoader;

import com.example.mutiphotochoser.model.ImageBean;

@SuppressLint("NewApi")
public class ImageLoad extends AsyncTaskLoader<ArrayList<ImageBean>> {

	private ArrayList<ImageBean> mImages = null;

	public ImageLoad(Context context) {
		super(context);
	}

	@Override
	public ArrayList<ImageBean> loadInBackground() {
		// TODO Auto-generated method stub
		ArrayList<ImageBean> imageList = new ArrayList<ImageBean>();
		Cursor imageCursor = getContext().getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Images.Media.DATA,
						MediaStore.Images.Media._ID }, null, null,
				MediaStore.Images.Media._ID);
		if (imageCursor != null && imageCursor.getCount() > 0) {
			while (imageCursor.moveToNext()) {
				ImageBean item = new ImageBean(
						imageCursor.getString(imageCursor
								.getColumnIndex(MediaStore.Images.Media.DATA)),
						false);
				imageList.add(item);
			}
		}
		if (imageCursor != null) {
			imageCursor.close();
		}
		Collections.reverse(imageList);
		return imageList;
	}

	@Override
	protected void onStartLoading() {
		if (mImages != null && mImages.size() > 0) {
			deliverResult(mImages);
		}

		if (takeContentChanged() || mImages == null) {
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading() {
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	@Override
	public void onCanceled(ArrayList<ImageBean> images) {
		if (images != null) {
			images.clear();
			images = null;
		}
	}

	@Override
	protected void onReset() {
		super.onReset();
		// Ensure the loader is stopped
		onStopLoading();
		if (mImages != null) {
			mImages.clear();
			mImages = null;
		}
	}

}
