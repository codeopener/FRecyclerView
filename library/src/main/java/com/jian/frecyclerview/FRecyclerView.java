package com.jian.frecyclerview;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jian.frecyclerview.layoutmanager.ExStaggeredGridLayoutManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjian on 2016/6/15.
 */
public class FRecyclerView extends FrameLayout{
    private static final int HEADER = Integer.MIN_VALUE;
    private static final int FOOTER = Integer.MAX_VALUE;

    private RecyclerView mRecyclerView;
    private Context mContext;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private OnItemClickListener mOnItemClickListener;

    private List<ItemData> mDatas = new ArrayList<>();

    private FAdapter mAdapter;

    private Map<Integer, Class<? extends FViewHolder>> mClasses = new HashMap<>();

    private HeaderFooterHolder mHeader;
    private HeaderFooterHolder mFooter;

    private boolean mHasHeader;
    private boolean mHasFooter;

    private List<HeaderFooterItem> mHeaders = new ArrayList<>();
    private List<HeaderFooterItem> mFooters = new ArrayList<>();

    private OnLoadMoreListener mLoadMoreListener;

    private LoadMoreView mLoadMoreView;

    private OnRefreshListener mRefreshListener;

    /**
     * 最后一个的位置
     */
    private int[] mLastPositions;

    public FRecyclerView(Context context) {
        this(context, null);
    }

    public FRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public class ItemData<T>
    {
        private int mType;
        private T mData;
    }

    public class HeaderFooterItem
    {
        public View v;
        public int index;
        public LinearLayout.LayoutParams params;
    }

    private void init()
    {
        LayoutInflater.from(mContext).inflate(R.layout.frecyclerview, this, true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.f_swiperefreshlayout);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mRefreshListener != null)
                {
                    mRefreshListener.onRefresh();
                    setLoadState(LoadMoreView.LOADSTATE_IDLE);
                }
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.f_recyclerview);
        LinearLayoutManager ma = new LinearLayoutManager(mContext);
        ma.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(ma);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                    if(manager instanceof LinearLayoutManager)
                    {
                        if(((LinearLayoutManager)manager).findLastVisibleItemPosition() == (recyclerView.getAdapter().getItemCount() - 1))
                        {
                            Log.i("FRecyclerView", "onLoadMore");
                            loadMore();
                        }
                    } else if(manager instanceof GridLayoutManager)
                    {
                        if(((GridLayoutManager)manager).findLastVisibleItemPosition() == (recyclerView.getAdapter().getItemCount() - 1))
                        {
                            Log.i("FRecyclerView", "onLoadMore");
                            loadMore();
                        }
                    } else if(manager instanceof ExStaggeredGridLayoutManager)
                    {
                        ExStaggeredGridLayoutManager exManager = (ExStaggeredGridLayoutManager) manager;
                        if(mLastPositions == null)
                        {
                            mLastPositions = new int[exManager.getSpanCount()];
                        }
                        exManager.findLastVisibleItemPositions(mLastPositions);
                        if(findMax(mLastPositions) == (recyclerView.getAdapter().getItemCount() - 1))
                        {
                            Log.i("FRecyclerView", "onLoadMore");
                            loadMore();
                        }
                    }

                }
            }
        });

        mAdapter = new FAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 取数组中最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    private void addHeader()
    {
        if(mHasHeader || mHeaders.size() <= 0)
        {
            return;
        }
        mHasHeader = true;
        ItemData data = new ItemData();
        data.mType = HEADER;
        mDatas.clear();
        mDatas.add(0, data);
    }

    private void addFooter()
    {
        if(mHasFooter || mFooters.size() <= 0)
        {
            return;
        }
        mHasFooter = true;
        ItemData data = new ItemData();
        data.mType = FOOTER;
        mDatas.add(mDatas.size(), data);
    }

    public void addHeaderView(View v)
    {
        addHeaderView(v, -1);
    }

    public void addHeaderView(View v, int index)
    {
        addHeaderView(v, index, null);
    }

    public void addHeaderView(View v, LinearLayout.LayoutParams params)
    {
        addHeaderView(v, -1, params);
    }

    public void addHeaderView(View v, int index, LinearLayout.LayoutParams params)
    {
        HeaderFooterItem header = new HeaderFooterItem();
        header.index = index;
        header.v = v;
        header.params = params == null ? new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) : params;
        mHeaders.add(header);
        addHeader();
    }

    public void addFooterView(View v)
    {
        addFooterView(v, -1);
    }

    public void addFooterView(View v, int index)
    {
        addFooterView(v, index, null);
    }

    public void addFooterView(View v, LinearLayout.LayoutParams params)
    {
        addFooterView(v, -1, params);
    }

    public void addFooterView(View v, int index, LinearLayout.LayoutParams params)
    {
        HeaderFooterItem footer = new HeaderFooterItem();
        footer.index = index;
        footer.v = v;
        footer.params = params == null ? new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) : params;
        mFooters.add(footer);
        addFooter();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    public FAdapter getmAdapter()
    {
        return mAdapter;
    }

    public RecyclerView getRecyclerView()
    {
        return mRecyclerView;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout)
    {
        mRecyclerView.setLayoutManager(layout);
    }

    public void setOnRefreshListener(OnRefreshListener listener)
    {
        if(listener == null)
        {
            mSwipeRefreshLayout.setEnabled(false);
        } else
        {
            mSwipeRefreshLayout.setEnabled(true);
            this.mRefreshListener = listener;
        }
    }

    public void setRefreshComplete()
    {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void setColorSchemeResources(@ColorRes int... colorResIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
    }

    public static class FViewHolder<D> extends RecyclerView.ViewHolder
    {
        public FViewHolder(View itemView) {
            super(itemView);
        }

        public FViewHolder(ViewGroup parent, Integer layoutId)
        {
            this(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        }

        protected <T extends View> T findView(@IdRes int id)
        {
            return (T) itemView.findViewById(id);
        }

        protected Context getContext()
        {
            return itemView.getContext();
        }

        public void bindData(D data, int positoin){};
    }

    public class FAdapter<T> extends RecyclerView.Adapter<FViewHolder>
    {
        @Override
        public FViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == HEADER)
            {
                if(mHeader == null)
                {
                    Log.i("FRecyclerView", "aaaaa");
                    mHeader = new HeaderFooterHolder(parent, 0);
                    mHeader.addView(mHeaders);
                }
                return mHeader;
            } else if(viewType == FOOTER)
            {
                if(mFooter == null)
                {
                    Log.i("FRecyclerView", "bbbbbb");
                    mFooter = new HeaderFooterHolder(parent, 0);
                    mFooter.addView(mFooters);
                }
                return mFooter;
            } else
            {
                final FViewHolder holder = createHolder(parent,viewType);
                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnItemClickListener != null)
                            mOnItemClickListener.onItemClick(FRecyclerView.this, v, holder.getAdapterPosition() - 1, holder.getItemId());
                    }
                });
                return holder;
            }
        }

        @Override
        public void onBindViewHolder(FViewHolder holder, int position) {
            holder.bindData(getItem(position).mData, position);
        }

        public ItemData getItem(int position)
        {
            return mDatas.get(position);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mDatas.get(position).mType;
        }

        public boolean isHeader(int position)
        {
            return mHasHeader && position == 0;
        }

        public boolean isFooter(int position)
        {
            return mHasFooter && position == (mDatas.size() - 1);
        }
    }

    public FAdapter getAdapter()
    {
        return mAdapter;
    }

    private FViewHolder createHolder(ViewGroup parent, int viewType)
    {
            try {
                Log.i("mClasses",mClasses.get(viewType).getName());
                Class cl = Class.forName(mClasses.get(viewType).getName());
                Constructor constructor = cl.getDeclaredConstructor(new Class[]{ViewGroup.class, Integer.class});
                Object ob = constructor.newInstance(new Object[]{parent, 0});
                return (FViewHolder<?>) ob;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        return null;
    }

    public void dataCommit()
    {
        mAdapter.notifyDataSetChanged();
//        mAdapter.notifyItemRangeInserted(0,mDatas.size());
    }

    public void setOnIntemClickListener(OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        /**
         * @param recyclerview FRecyclerView
         * @param v the itemView in Holder
         * @param position holder.getAdapterPosition()
         * @param id  holder.getItemId()
         * */
        void onItemClick(FRecyclerView recyclerview, View v, int position, long id);
    }

    public <K extends FViewHolder<V>, V> void dataAdd(Class<K> holder, V data)
    {
        ItemData<V> item = new ItemData<>();
        item.mData = data;
        item.mType = registerType(holder);
        int size = mDatas.size();
        mDatas.add( mHasFooter ? size - 1 : size, item);
    }

    public <K extends FViewHolder<V>, V> void dataAddAll(final Class<K> holder, final List<V> datas)
    {
        for (V v: datas)
        {
            dataAdd(holder, v);
        }
    }

    public void dataClear()
    {
        mDatas.clear();
        mAdapter.notifyDataSetChanged();
        mHasHeader = false;
        mHasFooter = false;
        mHeader = null;
        mFooter = null;
        addHeader();
        addFooter();
    }

    private int registerType(Class<? extends FRecyclerView.FViewHolder<?>> cls)
    {
        int typeId = cls.hashCode();
        mClasses.put(typeId, cls);
        return typeId;
    }

    private <T> T classNew(Class<T> cls)
    {
        try
        {
            T t = cls.newInstance();
            return t;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public interface OnRefreshListener
    {
        void onRefresh();
    }

    public interface OnLoadMoreListener
    {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener, int pageSize)
    {
        this.mLoadMoreListener = listener;
        if(mLoadMoreView == null)
        {
            mLoadMoreView = new LoadMoreView(mContext);
            mLoadMoreView.setOnLoadMoreViewListener(new LoadMoreView.OnLoadMoreViewListener() {
                @Override
                public void onErrorClick(View v) {
                    loadMore();
                }
            });
        }
        addFooterView(mLoadMoreView, mFooters.size());
    }

    private void loadMore()
    {
        if(!mLoadMoreView.canLoadMore())
        {
            return;
        }
        if(mLoadMoreListener != null)
        {
            mLoadMoreListener.onLoadMore();
            setLoadState(LoadMoreView.LOADSTATE_LOADING);
        }
    }

    public void setLoadState(int state)
    {
        mLoadMoreView.setState(state, mAdapter.getItemCount());
    }


}
