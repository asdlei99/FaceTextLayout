package la.juju.www.android.ftil.widgets;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import java.util.List;
import la.juju.www.android.ftil.listeners.OnFaceTextClickListener;
import la.juju.www.android.ftil.R;
import la.juju.www.android.ftil.utils.FaceTextInputLayoutHelper;

/**
 * Created by HelloVass on 16/2/24.
 */
public class FaceTextInputLayout extends RelativeLayout {

  private FaceTextInputLayoutHelper mFaceTextInputLayoutHelper;

  public FaceTextInputLayout(Context context) {
    this(context, null);
  }

  public FaceTextInputLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FaceTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mFaceTextInputLayoutHelper.unregister();
  }

  private void init(Context context, AttributeSet attrs) {
    View.inflate(getContext(), R.layout.layout_face_text_input, this);

    ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
    DotViewLayout dotViewLayout = (DotViewLayout) findViewById(R.id.dotview_layout);

    // TODO: 生成页面在主线程，以后挪到其他线程中
    mFaceTextInputLayoutHelper = FaceTextInputLayoutHelper.newInstance(context);
    // 生成所有“颜文字页面”
    List<RecyclerView> allPageList = mFaceTextInputLayoutHelper.generateAllPage();

    MyPagerAdapter pagerAdapter = new MyPagerAdapter(allPageList);

    viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
    viewPager.setAdapter(pagerAdapter);
    dotViewLayout.setViewPager(viewPager);
  }

  private static class MyPagerAdapter extends PagerAdapter {

    private List<RecyclerView> mFaceTextInputPageList;

    public MyPagerAdapter(List<RecyclerView> recyclerViewList) {
      mFaceTextInputPageList = recyclerViewList;
    }

    @Override public int getCount() {
      return mFaceTextInputPageList.size();
    }

    @Override public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
      container.addView(mFaceTextInputPageList.get(position));
      return mFaceTextInputPageList.get(position);
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView(mFaceTextInputPageList.get(position));
    }
  }

  public void setOnFaceTextClickListener(OnFaceTextClickListener onFaceTextClickListener) {
    mFaceTextInputLayoutHelper.register(onFaceTextClickListener);
  }
}
