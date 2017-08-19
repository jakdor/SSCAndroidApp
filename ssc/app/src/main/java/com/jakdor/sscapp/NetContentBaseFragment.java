package com.jakdor.sscapp;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Extends BaseFragment to handle UI response to loading dynamic content
 * Child class must include loading_layout.xml in layout file
 */
public abstract class NetContentBaseFragment extends BaseFragment {

    @BindView(R.id.loading_view)
    LinearLayout loadingView;
    @BindView(R.id.loading_card)
    CardView loadingCard;
    @BindView(R.id.loading_message)
    TextView loadText;
    @BindView(R.id.timetable_loading)
    ImageView loadingAnim;

    //pass child View in onCreateView
    public void createView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();

        Glide.with(this)
                .load(R.drawable.loading_anim)
                .asGif()
                .into(loadingAnim);

        loadingView.setVisibility(View.VISIBLE);
        loadingCard.setVisibility(View.GONE);
        loadingAnim.setVisibility(View.VISIBLE);
    }

    @Override
    protected void loadContent(){
        super.loadContent();
        loadingView.setVisibility(View.GONE);
    }

    @Override
    protected void loadingUpdate() {
        super.loadingUpdate();
        loadingCard.setVisibility(View.GONE);
        removeContentWhileLoad();
    }

    protected abstract void removeContentWhileLoad();

    @Override
    protected void firstLoadFailure() {
        super.firstLoadFailure();
        loadText.setText(getString(R.string.net_no_init_data));
        loadingCard.setVisibility(View.VISIBLE);
        loadingAnim.setVisibility(View.GONE);
    }
}
