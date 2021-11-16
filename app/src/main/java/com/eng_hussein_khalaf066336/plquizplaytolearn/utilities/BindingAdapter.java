package com.eng_hussein_khalaf066336.plquizplaytolearn.utilities;

import android.app.Application;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("android:imageURL")
    public static void setImageURL(ImageView imageView , String URL)
    {try {
            imageView.setAlpha(0f);
            Picasso.get().load(URL).noFade().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    imageView.animate().setDuration(300).alpha(1f).start();

                }

                @Override
                public void onError(Exception e) {

                }
            });

        }
        catch (Exception ignored)
        {

        }
    }

}

