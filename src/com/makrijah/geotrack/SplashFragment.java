package com.makrijah.geotrack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

/**
 * A fragment used in splashscreen
 * @author makrijah
 *
 */
public class SplashFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.activity_splash, container, false);
		
		final TextView tv = (TextView) view.findViewById(R.id.splashTestText);
		tv.setSelected(false);
		final Animation animation = new AlphaAnimation(1.0f, 0.5f);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		tv.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			
			public void onAnimationStart(Animation animation){}
			
			public void onAnimationRepeat(Animation animation) {}
			
			public void onAnimationEnd(Animation animation) {
				tv.startAnimation(animation);				
			}
		});		
		tv.startAnimation(animation);

		return view;
	}

}
