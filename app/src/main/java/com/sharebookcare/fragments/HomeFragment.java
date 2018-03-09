package com.sharebookcare.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sharebookcare.R;
import com.sharebookcare.activity.BookActivity;
import com.sharebookcare.activity.DetailActivity;
import com.sharebookcare.bean.Book;
import com.sharebookcare.common.AppUtil;
import com.sharebookcare.contract.HomeContract;
import com.sharebookcare.presenter.HomePresenter;

import java.util.ArrayList;

import static com.sharebookcare.activity.DetailActivity.BOOK_KEY;


public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeContract.View {
    private static final String TAG = HomeFragment.class.getCanonicalName();

    private RelativeLayout recommandRl; //第一套 layout_destrition
    private RelativeLayout lastestRl;
    private RelativeLayout allRl;

    private LinearLayout allRl1; //第二套 recommand_book_1
    private LinearLayout allRl2;
    private LinearLayout allRl3;

    private LinearLayout recommandRl1; //第二套 recommand_book_1
    private LinearLayout recommandRl2;
    private LinearLayout recommandRl3;

    private TextView recommandTv1; //第三套
    private TextView recommandTv2;
    private TextView recommandTv3;

    private TextView allTv1; //第三套
    private TextView allTv2;
    private TextView allTv3;


    private ImageView recommandImg1;
    private ImageView recommandImg2;
    private ImageView recommandImg3;

    private ImageView allImg1;
    private ImageView allImg2;
    private ImageView allImg3;


    private LinearLayout latestRl1;
    private LinearLayout latestRl2;
    private LinearLayout latestRl3;

    private TextView latestTv1;
    private TextView latestTv2;
    private TextView latestTv3;

    private ImageView latestImg1;
    private ImageView latestImg2;
    private ImageView latestImg3;


    private TextView[] recommandTvs;
    private TextView[] latestTvs;

    private TextView recommandTitleTv;
    private TextView latestTitileTv;
    private TextView allTitleTv;

    private TextView recommandMoreTv;
    private TextView latestMoreTv;
    private TextView allMoreTv;

    private ArrayList<Book> recommandList;
    private ArrayList<Book> latestList;
    private ArrayList<Book> allList;

    private HomePresenter presenter;

    private static final String RECOMMAND = "recommand";
    private static final String LATEST = "latest";
    private static final String ALL = "all";
    private static final String RECOMMANDRL1 = "recommandRl1";
    private static final String RECOMMANDRL2 = "recommandRl2";
    private static final String RECOMMANDRL3 = "recommandRl3";
    private static final String LATEATRL1 = "latestRl1";
    private static final String LATEATRL2 = "latestRl2";
    private static final String LATEATRL3 = "latestRl3";

    private static final String ALLRL1 = "allRl1";
    private static final String ALLRL2 = "allRl2";
    private static final String ALLRL3 = "allRl3";

    @Override
    protected void initView(View view) {
        recommandRl = view.findViewById(R.id.descrition1); //第一套
        lastestRl = view.findViewById(R.id.descrition2);
        allRl = view.findViewById(R.id.descrition3);

        initRecommandView();
        initLatestView();
        initAllView();

        initRecommandTitle();
        initLatestTitle();
        initAllTitle();


        recommandTvs = new TextView[]{
                recommandTv1, recommandTv2
        };

        latestTvs = new TextView[]{
                latestTv1, latestTv2
        };

    }

    private void initAllTitle() {
        allTitleTv = allRl.findViewById(R.id.distrition_title);
        allMoreTv = allRl.findViewById(R.id.more);
    }

    private void initAllView() {
        allRl1 = allRl.findViewById(R.id.recommand_book_1);
        allRl2 = allRl.findViewById(R.id.recommand_book_2);
        allRl3 = allRl.findViewById(R.id.recommand_book_3);

        allTv1 = allRl1.findViewById(R.id.name_tv);
        allImg1 = allRl1.findViewById(R.id.book_iv);

        allTv2 = allRl2.findViewById(R.id.name_tv);
        allImg2 = allRl2.findViewById(R.id.book_iv);

        allTv3 = allRl3.findViewById(R.id.name_tv);
        allImg3 = allRl3.findViewById(R.id.book_iv);
    }

    private void initLatestTitle() {
        latestTitileTv = lastestRl.findViewById(R.id.distrition_title);
        latestMoreTv = lastestRl.findViewById(R.id.more);
    }

    private void initRecommandTitle() {

        recommandTitleTv = recommandRl.findViewById(R.id.distrition_title);
        recommandMoreTv = recommandRl.findViewById(R.id.more);
    }

    private void initLatestView() {
        latestRl1 = lastestRl.findViewById(R.id.recommand_book_1);
        latestRl2 = lastestRl.findViewById(R.id.recommand_book_2);
        latestRl3 = lastestRl.findViewById(R.id.recommand_book_3);

        latestTv1 = latestRl1.findViewById(R.id.name_tv);
        latestImg1 = latestRl1.findViewById(R.id.book_iv);

        latestTv2 = latestRl2.findViewById(R.id.name_tv);
        latestImg2 = latestRl2.findViewById(R.id.book_iv);

        latestTv3 = latestRl3.findViewById(R.id.name_tv);
        latestImg3 = latestRl3.findViewById(R.id.book_iv);
    }

    //初始化第二套
    private void initRecommandView() {
        recommandRl1 = recommandRl.findViewById(R.id.recommand_book_1);
        recommandRl2 = recommandRl.findViewById(R.id.recommand_book_2);
        recommandRl3 = recommandRl.findViewById(R.id.recommand_book_3);

        recommandTv1 = recommandRl1.findViewById(R.id.name_tv);
        recommandImg1 = recommandRl1.findViewById(R.id.book_iv);

        recommandTv2 = recommandRl2.findViewById(R.id.name_tv);
        recommandImg2 = recommandRl2.findViewById(R.id.book_iv);

        recommandTv3 = recommandRl3.findViewById(R.id.name_tv);
        recommandImg3 = recommandRl3.findViewById(R.id.book_iv);


    }


    @Override
    protected void initListener() {
        recommandMoreTv.setOnClickListener(this);
        latestMoreTv.setOnClickListener(this);
        allMoreTv.setOnClickListener(this);

        recommandRl1.setOnClickListener(this);
        recommandRl2.setOnClickListener(this);
        recommandRl3.setOnClickListener(this);

        allRl1.setOnClickListener(this);
        allRl2.setOnClickListener(this);
        allRl3.setOnClickListener(this);

        latestRl1.setOnClickListener(this);
        latestRl2.setOnClickListener(this);
        latestRl3.setOnClickListener(this);

        recommandRl1.setTag(RECOMMANDRL1);
        recommandRl2.setTag(RECOMMANDRL2);
        recommandRl3.setTag(RECOMMANDRL3);

        latestRl1.setTag(LATEATRL1);
        latestRl2.setTag(LATEATRL2);
        latestRl3.setTag(LATEATRL3);

        allRl1.setTag(ALLRL1);
        allRl2.setTag(ALLRL2);
        allRl3.setTag(ALLRL3);

    }

    @Override
    protected void initData() {

        recommandTitleTv.setText(R.string.recommand);
        latestTitileTv.setText(R.string.latested);
        allTitleTv.setText("所有图书");

        recommandMoreTv.setTag(RECOMMAND);
        latestMoreTv.setTag(LATEST);
        allMoreTv.setTag(ALL);

        presenter = new HomePresenter(this);
        presenter.getLatestList();
        presenter.getRecommandList();
        presenter.getAllList();

    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (RECOMMAND.equals(tag)) {
            Log.i(TAG, "recommand more....");
            Bundle bundle = new Bundle();
            bundle.putSerializable(BookActivity.BOOK_KEY, recommandList);
            bundle.putString(BookActivity.TITLE, "推荐读书");
            AppUtil.startActivity(getActivity(), BookActivity.class, bundle);

        } else if (LATEST.equals(tag)) {
            Log.i(TAG, "latest more.....");
            Bundle bundle = new Bundle();
            bundle.putString(BookActivity.TITLE, "最新读书");
            bundle.putSerializable(BookActivity.BOOK_KEY, latestList);
            AppUtil.startActivity(getActivity(), BookActivity.class, bundle);
        }else if (ALL.equals(tag)) {
            Log.i(TAG, "latest more.....");
            Bundle bundle = new Bundle();
            bundle.putString(BookActivity.TITLE, "所有读书");
            bundle.putSerializable(BookActivity.BOOK_KEY, allList);
            AppUtil.startActivity(getActivity(), BookActivity.class, bundle);
        }else if (RECOMMANDRL1.equals(tag)) {
            if (recommandList.size() >= 1) {
                Log.i(TAG, "recommandList1 clicked");
                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_KEY, recommandList.get(0));
                AppUtil.startActivity(getActivity(), DetailActivity.class, bundle);
            }
        } else if (RECOMMANDRL2.equals(tag)) {
            if (recommandList.size() >= 2) {
                Log.i(TAG, "recommandList2 clicked");
                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_KEY, recommandList.get(1));
                AppUtil.startActivity(getActivity(), DetailActivity.class, bundle);

            }
        } else if (RECOMMANDRL3.equals(tag)) {
            if (recommandList.size() >= 3) {
                Log.i(TAG, "recommandList3 clicked");
                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_KEY, recommandList.get(2));
                AppUtil.startActivity(getActivity(), DetailActivity.class, bundle);

            }
        } else if (LATEATRL1.equals(tag)) {
            if (latestList.size() >= 1) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_KEY, latestList.get(0));
                AppUtil.startActivity(getActivity(), DetailActivity.class, bundle);

            }
        } else if (LATEATRL2.equals(tag)) {
            if (latestList.size() >= 2) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_KEY, latestList.get(1));
                AppUtil.startActivity(getActivity(), DetailActivity.class, bundle);

            }
        } else if (LATEATRL3.equals(tag)) {
            if (latestList.size() >= 3) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_KEY, latestList.get(2));
                AppUtil.startActivity(getActivity(), DetailActivity.class, bundle);

            }
        }else if (ALLRL1.equals(tag)) {
            if (allList.size() >= 1) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_KEY, allList.get(0));
                AppUtil.startActivity(getActivity(), DetailActivity.class, bundle);

            }
        } else if (ALLRL2.equals(tag)) {
            if (allList.size() >= 2) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_KEY, allList.get(1));
                AppUtil.startActivity(getActivity(), DetailActivity.class, bundle);

            }
        } else if (ALLRL3.equals(tag)) {
            if (allList.size() >= 3) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(BOOK_KEY, allList.get(2));
                AppUtil.startActivity(getActivity(), DetailActivity.class, bundle);

            }
        }
    }

    @Override
    public void getRecommandSuccess(ArrayList<Book> recommandList) {
        if (recommandList != null && recommandList.size() >= 1) {
            HomeFragment.this.recommandList = recommandList;
            recommandTv1.setText(recommandList.get(0).getTitle());
            AppUtil.loadImg(getActivity(), recommandList.get(0).getImg(), recommandImg1);
            Log.i(TAG, "start recommand book1 success");
            if (recommandList.size() >= 2) {
                recommandTv2.setText(recommandList.get(1).getTitle());
                AppUtil.loadImg(getActivity(), recommandList.get(1).getImg(), recommandImg2);
                Log.i(TAG, "start recommand book2 success");
            }

            if (recommandList.size() >= 3) {
                recommandTv3.setText(recommandList.get(2).getTitle());
                AppUtil.loadImg(getActivity(), recommandList.get(2).getImg(), recommandImg3);
                Log.i(TAG, "start recommand book3 success");
            }
        }
    }

    @Override
    public void getLatestSuccess(ArrayList<Book> latestList) {
        if (latestList != null && latestList.size() >= 1) {
            HomeFragment.this.latestList = latestList;
            Log.i(TAG, "latestList.size=" + latestList.size());
            latestTv1.setText(latestList.get(0).getTitle());
            AppUtil.loadImg(getActivity(), latestList.get(0).getImg(), latestImg1);
            Log.i(TAG, "start latest book1 success");

            if (latestList.size() >= 2) {
                latestTv2.setText(latestList.get(1).getTitle());
                AppUtil.loadImg(getActivity(), latestList.get(1).getImg(), latestImg2);
                Log.i(TAG, "start latest book2 success");

            }

            if (latestList.size() >= 3) {
                latestTv3.setText(latestList.get(2).getTitle());
                AppUtil.loadImg(getActivity(), latestList.get(2).getImg(), latestImg3);
                Log.i(TAG, "start latest book3 success");

            }

        } else {
            Log.i(TAG, "no latestList");
        }
    }

    @Override
    public void getAllSuccess(ArrayList<Book> allList) {
        if (allList != null && allList.size() >= 1) {
            HomeFragment.this.allList = allList;
            Log.i(TAG, "allList.size=" + allList.size());
            allTv1.setText(allList.get(0).getTitle());
            AppUtil.loadImg(getActivity(), allList.get(0).getImg(), allImg1);
            Log.i(TAG, "start all book1 success");

            if (allList.size() >= 2) {
                allTv2.setText(allList.get(1).getTitle());
                AppUtil.loadImg(getActivity(), allList.get(1).getImg(), allImg2);
                Log.i(TAG, "start all book2 success");

            }

            if (allList.size() >= 3) {
                allTv3.setText(allList.get(2).getTitle());
                AppUtil.loadImg(getActivity(), allList.get(2).getImg(), allImg3);
                Log.i(TAG, "start all book3 success");

            }

        } else {
            Log.i(TAG, "no all book");
        }
    }
}
