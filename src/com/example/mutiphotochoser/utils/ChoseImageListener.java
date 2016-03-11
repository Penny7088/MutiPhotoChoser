package com.example.mutiphotochoser.utils;

import com.example.mutiphotochoser.model.ImageBean;

public interface ChoseImageListener {
	
	public boolean onSelected(ImageBean image);

	public boolean onCancelSelect(ImageBean image);
}
