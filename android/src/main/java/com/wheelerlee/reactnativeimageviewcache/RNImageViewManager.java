package com.wheelerlee.reactnativeimageviewcache;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RNImageViewManager extends SimpleViewManager<ImageView> {

    private static final String REACT_ON_LOAD_START_EVENT = "onLoadStart";
    private static final String REACT_ON_PROGRESS_EVENT = "onProgress";
    private static final String REACT_ON_ERROR_EVENT = "onError";
    private static final String REACT_ON_LOAD_EVENT = "onLoadComplete";

    private ThemedReactContext mReactContext;

    @Nonnull
    @Override
    public String getName() {
        return "RNImageView";
    }

    @Nonnull
    @Override
    protected ImageView createViewInstance(@Nonnull ThemedReactContext reactContext) {
        mReactContext = reactContext;
        ImageView iv = new ImageView(reactContext);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return iv;
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {

        return MapBuilder.<String, Object>builder()
                .put(REACT_ON_LOAD_START_EVENT, MapBuilder.of("registrationName", REACT_ON_LOAD_START_EVENT))
                .put(REACT_ON_PROGRESS_EVENT, MapBuilder.of("registrationName", REACT_ON_PROGRESS_EVENT))
                .put(REACT_ON_LOAD_EVENT, MapBuilder.of("registrationName", REACT_ON_LOAD_EVENT))
                .put(REACT_ON_ERROR_EVENT, MapBuilder.of("registrationName", REACT_ON_ERROR_EVENT))
                .build();

    }

    @ReactProp(name = "source")
    public void setSource(final ImageView imageView, @Nullable ReadableMap source) {

        ProgressInterceptor.addListener(source.getString("uri"), new ResponseProgressListener() {
            @Override
            public void update(long received, long total, int progress) {
                WritableMap event = new WritableNativeMap();
                event.putInt("receivedSize", (int) received);
                event.putInt("expectedSize", (int) total);
                event.putInt("progress", (int) progress);
                RCTEventEmitter eventEmitter = ((ThemedReactContext)imageView.getContext()).getJSModule(RCTEventEmitter.class);
                eventEmitter.receiveEvent(imageView.getId(), REACT_ON_PROGRESS_EVENT, event);
            }
        });

        RequestOptions requestOptions = new RequestOptions();
        if (RNImageView.defaultSource != null) {
            if (source.hasKey("defaultSource")) {
                requestOptions.placeholder(RNImageView.defaultSource.get(source.getString("defaultSource")).intValue());
                requestOptions.error(RNImageView.defaultSource.get(source.getString("defaultSource")).intValue());
            } else {
                requestOptions.placeholder(RNImageView.defaultSource.get("default").intValue());
                requestOptions.error(RNImageView.defaultSource.get("default").intValue());
            }
        }
//        requestOptions.placeholder(this.defaultSource);
//        requestOptions.error(this.defaultSource);
        RCTEventEmitter eventEmitter = ((ThemedReactContext)imageView.getContext()).getJSModule(RCTEventEmitter.class);
        eventEmitter.receiveEvent(imageView.getId(), REACT_ON_LOAD_START_EVENT, new WritableNativeMap());
        Glide.with(mReactContext)
                .setDefaultRequestOptions(requestOptions)
                .load(source.getString("uri"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@androidx.annotation.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        WritableMap event = new WritableNativeMap();
                        event.putString("message", e.getMessage());
                        RCTEventEmitter eventEmitter = ((ThemedReactContext)imageView.getContext()).getJSModule(RCTEventEmitter.class);
                        eventEmitter.receiveEvent(imageView.getId(), REACT_ON_ERROR_EVENT, event);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        RCTEventEmitter eventEmitter = ((ThemedReactContext)imageView.getContext()).getJSModule(RCTEventEmitter.class);
                        eventEmitter.receiveEvent(imageView.getId(), REACT_ON_LOAD_EVENT, new WritableNativeMap());
                        return false;
                    }
                })
                .into(imageView);

    }

    @ReactProp(name = "resizeMode")
    public void setResizeMode(ImageView view, @Nullable String resizeMode) {

        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
        if ("contain".equals(resizeMode)) {
            scaleType = ImageView.ScaleType.FIT_CENTER;
        }
        if ("cover".equals(resizeMode)) {
            scaleType = ImageView.ScaleType.CENTER_CROP;
        }
        if ("stretch".equals(resizeMode)) {
            scaleType = ImageView.ScaleType.FIT_XY;
        }
        if ("center".equals(resizeMode)) {
            scaleType = ImageView.ScaleType.CENTER_INSIDE;
        }

        if (view.getScaleType() != scaleType)
            view.setScaleType(scaleType);
    }

}
