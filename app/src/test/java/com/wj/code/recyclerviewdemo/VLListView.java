//package com.wj.code.recyclerviewdemo;
//
///**
// * Created by wangjian on 2016/6/15.
// */
//        import java.util.ArrayList;
//        import java.util.List;
//
//        import android.annotation.SuppressLint;
//        import android.content.Context;
//        import android.util.AttributeSet;
//        import android.view.Gravity;
//        import android.view.LayoutInflater;
//        import android.view.MotionEvent;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.view.animation.DecelerateInterpolator;
//        import android.widget.AbsListView;
//        import android.widget.BaseAdapter;
//        import android.widget.LinearLayout;
//        import android.widget.ListView;
//        import android.widget.RelativeLayout;
//        import android.widget.Scroller;
//        import android.widget.TextView;
//
//
//public class VLListView extends RelativeLayout
//{
//    public VLListView(Context context)
//    {
//        super(context);
//        init();
//    }
//
//    public VLListView(Context context, AttributeSet attrs)
//    {
//        super(context, attrs);
//        init();
//    }
//
//    public VLListView(Context context, AttributeSet attrs, int defStyle)
//    {
//        super(context, attrs, defStyle);
//        init();
//    }
//
//    private void init()
//    {
//        mLayoutInflater = LayoutInflater.from(getContext());
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        mListView = new ListViewWrapper(getContext());
//        addView(mListView, params);
//        mListView.setTag(this);
//        mListViewItems = new ArrayList<VLListViewItem<?>>();
//        mListViewItemsTemp = new ArrayList<VLListViewItem<?>>();
//        mListViewTypeClasses = new ArrayList<Class<? extends VLListViewType<?>>>();
//        mListViewTypes = new ArrayList<VLListViewType<?>>();
//        mListViewAdapter = new VLListViewAdapter();
//        mResetAdapterFlag = true;
//        mListHeader = null;
//        mListFooter = null;
//        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.alignWithParent = true;
//        params.addRule(ALIGN_PARENT_TOP);
//        mFloatHeader = new RelativeLayout(getContext());
//        addView(mFloatHeader, params);
//        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.alignWithParent = true;
//        params.addRule(ALIGN_PARENT_BOTTOM);
//        mFloatFooter = new RelativeLayout(getContext());
//        addView(mFloatFooter, params);
//    }
//
//    public View setFloatHeader(int layoutId)
//    {
//        return mLayoutInflater.inflate(layoutId, mFloatHeader);
//    }
//
//    public View setFloatFooter(int layoutId)
//    {
//        return mLayoutInflater.inflate(layoutId, mFloatFooter);
//    }
//
//    public interface OnComputeScrollListener
//    {
//        public void onComputeScroll();
//    }
//
//    public static class ListViewWrapper extends ListView
//    {
//        private OnComputeScrollListener mHeaderOnComputeScrollListener = null;
//        private OnComputeScrollListener mFooterOnComputeScrollListener = null;
//        private OnTouchListener mHeaderOnTouchListener = null;
//        private OnTouchListener mFooterOnTouchListener = null;
//
//        @SuppressLint("ClickableViewAccessibility")
//        public ListViewWrapper(Context context)
//        {
//            super(context);
//            setOnTouchListener(new OnTouchListener()
//            {
//                @SuppressLint("ClickableViewAccessibility")
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent)
//                {
//                    if(mHeaderOnTouchListener!=null)
//                        mHeaderOnTouchListener.onTouch(view, motionEvent);
//                    if(mFooterOnTouchListener!=null)
//                        mFooterOnTouchListener.onTouch(view, motionEvent);
//                    return view.onTouchEvent(motionEvent);
//                }
//            });
//        }
//
//        public void setHeaderOnTouchListener(OnTouchListener headerTouchListener)
//        {
//            mHeaderOnTouchListener = headerTouchListener;
//        }
//
//        public void setFooterOnTouchListener(OnTouchListener footerTouchListener)
//        {
//            mFooterOnTouchListener = footerTouchListener;
//        }
//
//        public ListViewWrapper(Context context, AttributeSet attrs)
//        {
//            super(context, attrs);
//        }
//
//        public ListViewWrapper(Context context, AttributeSet attrs, int defStyle)
//        {
//            super(context, attrs, defStyle);
//        }
//
//        @Override
//        public void computeScroll()
//        {
//            super.computeScroll();
//            if(mHeaderOnComputeScrollListener!=null) mHeaderOnComputeScrollListener.onComputeScroll();
//            if(mFooterOnComputeScrollListener!=null) mFooterOnComputeScrollListener.onComputeScroll();
//        }
//
//        public void setHeaderOnComputeScrollListener(OnComputeScrollListener headerOnComputeScrollListener)
//        {
//            mHeaderOnComputeScrollListener = headerOnComputeScrollListener;
//        }
//
//        public void setFooterOnComputeScrollListener(OnComputeScrollListener footerOnComputeScrollListener)
//        {
//            mFooterOnComputeScrollListener = footerOnComputeScrollListener;
//        }
//
//        /**设置当listview滑动时不加载图片*/
//        @Override
//        public void setOnScrollListener(OnScrollListener l) {
//            super.setOnScrollListener(new PauseOnScrollListener(ImageLoaderModel.instance().getImageLoader(), true, true, l));
//        }
//    }
//
//    private LayoutInflater mLayoutInflater;
//    private ListViewWrapper mListView;
//    private List<VLListViewItem<?>> mListViewItems;
//    private List<VLListViewItem<?>> mListViewItemsTemp;
//    private List<Class<? extends VLListViewType<?>>> mListViewTypeClasses;
//    private List<VLListViewType<?>> mListViewTypes;
//    private VLListViewAdapter mListViewAdapter;
//    private boolean mResetAdapterFlag;
//    private VLListHeader mListHeader;
//    private VLListFooter mListFooter;
//    private RelativeLayout mFloatHeader;
//    private RelativeLayout mFloatFooter;
//
//    public static interface VLListViewType<T>
//    {
//        public View onViewCreate(VLListView listView, int position, LayoutInflater inflater, ViewGroup parent, T data);
//        public void onViewUpdate(VLListView listView, int position, View view, T data, Object id);
//
//    }
//
//    public static class VLListViewItem<T>
//    {
//        public int mTypeId;
//        public T mData;
//        public Object mId;
//    }
//
//    public VLListHeader getListHeader()
//    {
//        return mListHeader;
//    }
//
//    public VLListFooter getListFooter()
//    {
//        return mListFooter;
//    }
//
//    public void setListHeader(VLListHeader listHeader)
//    {
//        VLDebug.Assert(mListHeader==null);
//        mListHeader = listHeader;
//        mListHeader.init(getContext(), this);
//    }
//
//    public void setListFooter(VLListFooter listFooter)
//    {
//        VLDebug.Assert(mListFooter==null);
//        mListFooter = listFooter;
//        mListFooter.init(getContext(), this);
//    }
//
//    public ListView listView()
//    {
//        return mListView;
//    }
//
//    public int registerType(Class<? extends VLListView.VLListViewType<?>> cls)
//    {
//        mListViewTypeClasses.add(cls);
//        VLListViewType<?> type = VLUtils.classNew(cls);
//        VLDebug.Assert(type!=null);
//        mListViewTypes.add(type);
//        mResetAdapterFlag = true;
//        return mListViewTypes.size()-1;
//    }
//
//    public void dataClear()
//    {
//        mListViewItemsTemp.clear();
//    }
//
//    public int dataGetCount()
//    {
//        return mListViewItemsTemp.size();
//    }
//
//    public <V> V dateGetAt(int index)
//    {
//        VLListViewItem<?> item = mListViewItems.get(index);
//        @SuppressWarnings("unchecked")
//        V data = (V)item.mData;
//        return data;
//    }
//
//    public <K extends VLListViewType<V>,V> void dataAddTail(Class<K> type, V data)
//    {
//        VLListViewItem<V> item = new VLListViewItem<V>();
//        item.mTypeId = getViewTypeId(type);
//        item.mData = data;
//        item.mId = null;
//        mListViewItemsTemp.add(item);
//    }
//
//    public <K extends VLListViewType<V>,V> void dataAddListTail(Class<K> type, List<V> data)
//    {
//        for(int i = 0; i < data.size(); i++){
//            VLListViewItem<V> item = new VLListViewItem<V>();
//            item.mTypeId = getViewTypeId(type);
//            item.mData = data.get(i);
//            item.mId = null;
//            mListViewItemsTemp.add(item);
//        }
//    }
//
//    public <K extends VLListViewType<V>,V>  void dataSetAt(Class<K> type, V data, int index)
//    {
//        if(index<0 || index>=mListViewItemsTemp.size()) return;
//        @SuppressWarnings("unchecked")
//        VLListViewItem<V> item = (VLListViewItem<V>) mListViewItemsTemp.get(index);
//        item.mData = data;
//        mListViewItemsTemp.set(index, item);
//    }
//
//    public void dataDelAt(int index)
//    {
//        if(index<0 || index>=mListViewItemsTemp.size()) return;
//        mListViewItemsTemp.remove(index);
//    }
//
//    public <K extends VLListViewType<V>,V> void dataAddTail(Class<K> type, V data, Object id)
//    {
//        VLListViewItem<V> item = new VLListViewItem<V>();
//        item.mTypeId = getViewTypeId(type);
//        item.mData = data;
//        item.mId = id;
//        mListViewItemsTemp.add(item);
//    }
//
//    public static final int RELOAD_SCROLL_TOP = 0;
//    public static final int RELOAD_SCROLL_BOTTOM = 1;
//    public static final int RELOAD_PRESERVE_FIRST_POS = 2;
//    public static final int RELOAD_PRESERVE_LAST_POS = 3;
//
//    private int mReloadItemTop;
//    private List<Object> mReloadItemIds = new ArrayList<Object>();
//
//    private boolean preserveItemIndexHeight()
//    {
//        mReloadItemTop = 0;
//        mReloadItemIds.clear();
//
//        int childCount = mListView.getChildCount();
//        if(childCount==0) return false;
//        for(int i=0;i<childCount;i++)
//        {
//            View child = mListView.getChildAt(i);
//            int itemTop = child.getTop();
//            if(itemTop<0) continue;
//
//            int firstIndex = mListView.getFirstVisiblePosition();
//            int headerCount = mListView.getHeaderViewsCount();
//            int itemIndex = i + firstIndex - headerCount;
//            if(itemIndex<0 || itemIndex>=mListViewItems.size()) continue;
//
//            VLListView.VLListViewItem<?> item = mListViewItems.get(itemIndex);
//            if(item.mId==null) continue;
//
//            mReloadItemTop = itemTop;
//            mReloadItemIds.add(item.mId);
//            for(int j=itemIndex+1;j<mListViewItems.size();j++)
//            {
//                item = mListViewItems.get(itemIndex);
//                if(item.mId==null) continue;
//                mReloadItemIds.add(item.mId);
//            }
//            return true;
//        }
//        return false;
//    }
//
//    private boolean revertItemIndexHeight()
//    {
//        for(Object reloadItemId : mReloadItemIds)
//        {
//            for(int i=0; i<mListViewItems.size(); i++)
//            {
//                VLListView.VLListViewItem<?> item = mListViewItems.get(i);
//                if(item.mId==null || item.mId.equals(reloadItemId)==false) continue;
//
//                int position = i + mListView.getHeaderViewsCount();
//                mListView.setSelectionFromTop(position, mReloadItemTop);
//                mReloadItemIds.clear();
//                mReloadItemTop = 0;
//                return true;
//            }
//        }
//        mReloadItemIds.clear();
//        mReloadItemTop = 0;
//        return false;
//    }
//
//    public void dataCommit(final int reloadType)
//    {
//        if(reloadType==RELOAD_PRESERVE_FIRST_POS || reloadType==RELOAD_PRESERVE_LAST_POS)
//            preserveItemIndexHeight();
//
//        if(VLUtils.threadInMain())
//        {
//            mListViewItems.clear();
//            mListViewItems.addAll(mListViewItemsTemp);
//            mListViewAdapter.notifyDataSetChanged();
//            if(mResetAdapterFlag)
//            {
//                mListView.setAdapter(mListViewAdapter);
//                mResetAdapterFlag = false;
//            }
//            if(reloadType==RELOAD_PRESERVE_FIRST_POS || reloadType==RELOAD_PRESERVE_LAST_POS)
//                revertItemIndexHeight();
//            else if(reloadType==RELOAD_SCROLL_BOTTOM)
//                scrollToEnd();
//        }
//        else
//        {
//            VLScheduler.instance.schedule(0, VLScheduler.THREAD_MAIN, new VLBlock()
//            {
//                @Override
//                protected void process(boolean canceled)
//                {
//                    mListViewItems.clear();
//                    mListViewItems.addAll(mListViewItemsTemp);
//                    mListViewAdapter.notifyDataSetChanged();
//                    if(mResetAdapterFlag)
//                    {
//                        mListView.setAdapter(mListViewAdapter);
//                        mResetAdapterFlag = false;
//                    }
//                    if(reloadType==RELOAD_PRESERVE_FIRST_POS || reloadType==RELOAD_PRESERVE_LAST_POS)
//                        revertItemIndexHeight();
//                    else if(reloadType==RELOAD_SCROLL_BOTTOM)
//                        scrollToEnd();
//                }
//            });
//        }
//    }
//
//
//    public void notifyDataSetChanged()
//    {
//        if(VLUtils.threadInMain())
//        {
//            mListViewAdapter.notifyDataSetChanged();
//        }
//        else
//        {
//            VLScheduler.instance.schedule(0, VLScheduler.THREAD_MAIN, new VLBlock()
//            {
//                @Override
//                protected void process(boolean canceled)
//                {
//                    mListViewAdapter.notifyDataSetChanged();
//                }
//            });
//        }
//    }
//
//    private <K extends VLListViewType<V>,V> int getViewTypeId(Class<K> cls)
//    {
//        for(int i=mListViewTypeClasses.size()-1; i>=0; i--)
//        {
//            if(mListViewTypeClasses.get(i)==cls)
//            {
//                return i;
//            }
//        }
//        return registerType(cls);
//    }
//
//    private class VLListViewAdapter extends BaseAdapter
//    {
//        @Override
//        public int getCount()
//        {
//            return mListViewItems.size();
//        }
//
//        @Override
//        public Object getItem(int position)
//        {
//            return mListViewItems.get(position);
//        }
//
//        @Override
//        public long getItemId(int position)
//        {
//            return position;
//        }
//
//        @Override
//        public int getViewTypeCount()
//        {
//            int count = mListViewTypes.size();
//            if(count<=0) count = 1;
//            return count;
//        }
//
//        @Override
//        public int getItemViewType(int position)
//        {
//            int type = mListViewItems.get(position).mTypeId;
//            return type;
//        }
//
//        @SuppressWarnings({ "unchecked", "rawtypes" })
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent)
//        {
//            VLListViewItem item = mListViewItems.get(position);
//            VLListViewType type = mListViewTypes.get(item.mTypeId);
//            if(convertView==null) convertView = type.onViewCreate(VLListView.this, position, mLayoutInflater, parent, item.mData);
//            type.onViewUpdate(VLListView.this, position, convertView, item.mData, item.mId);
//            return convertView;
//        }
//    }
//
//    public static class VLRawDelegateType implements VLListViewType<VLListViewType<Object>>
//    {
//        @Override
//        public View onViewCreate(VLListView listView, int position, LayoutInflater inflater, ViewGroup parent, VLListViewType<Object> data)
//        {
//            View view = null;
//            if(data!=null) view = data.onViewCreate(listView, position, inflater, parent, data);
//            return view;
//        }
//
//        @Override
//        public void onViewUpdate(VLListView listView, int position, View view, VLListViewType<Object> data, Object id)
//        {
//            if(data!=null) data.onViewUpdate(listView, position, view, data, id);
//        }
//
//    }
//
//    public static class VLDummyHeightType implements VLListViewType<Integer>
//    {
//        @Override
//        public View onViewCreate(VLListView listView, int position, LayoutInflater inflater, ViewGroup parent, Integer data)
//        {
//            View view = new View(listView.getContext());
//            return view;
//        }
//
//        @Override
//        public void onViewUpdate(VLListView listView, int position, View view, Integer data, Object id)
//        {
//            int height = data;
//            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, height);
//            view.setLayoutParams(params);
//        }
//    }
//
//    public static class VLDummyStringType implements VLListViewType<String>
//    {
//        private int mLayoutHeight = AbsListView.LayoutParams.WRAP_CONTENT;
//        @SuppressLint("InlinedApi")
//        private int mGravity = Gravity.START;
//
//        public void setHeight(int height)
//        {
//            mLayoutHeight = height;
//        }
//
//        public void setGravity(int gravity)
//        {
//            mGravity = gravity;
//        }
//
//        @Override
//        public View onViewCreate(VLListView listView, int position, LayoutInflater inflater, ViewGroup parent, String data)
//        {
//            TextView tv = new TextView(listView.getContext());
//            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, mLayoutHeight);
//            tv.setLayoutParams(params);
//            tv.setGravity(mGravity);
//            return tv;
//        }
//
//        @Override
//        public void onViewUpdate(VLListView listView, int position, View view, String data, Object id)
//        {
//            TextView tv = (TextView)view;
//            String str = data;
//            tv.setText(str);
//        }
//    }
//
//    public static abstract class VLListHeader
//    {
//        private Context mContext;
//        private VLListView mVLListView;
//        private ListViewWrapper mListView;
//        private boolean mContentOccupy;
//        private boolean mContentDisplay;
//        private boolean mInited;
//
//        private Scroller mScroller;
//        private LinearLayout mListHeader;
//        private LinearLayout mContent;
//        private int mContentHeight;
//        private LinearLayout mContainer;
//        private int mState;
//        private boolean mScrolling;
//
//        public static final int STATE_INIT = 0;
//        public static final int STATE_NORMAL = 1;
//        public static final int STATE_READY = 2;
//        public static final int STATE_LOADING = 3;
//
//        public abstract void onCreate(LayoutInflater inflater, ViewGroup root);
//        public abstract void onStateChanged(int from, int to);
//        public void onPushHeight(int height){}
//        public int getContentHeight() { return mContentHeight; }
//
//        public VLListHeader()
//        {
//            mContext = null;
//            mListView = null;
//            mContentOccupy = false;
//            mContentDisplay = true;
//            mInited = false;
//            mScroller = null;
//            mListHeader = null;
//            mContent = null;
//            mContentHeight = 0;
//            mContainer = null;
//            mState = STATE_INIT;
//            mScrolling = false;
//        }
//
//        @SuppressLint("ClickableViewAccessibility")
//        protected void init(Context context, VLListView vllistview)
//        {
//            VLDebug.Assert(mInited==false);
//            mContext = context;
//            mVLListView = vllistview;
//            mListView = vllistview.mListView;
//
//            mScroller = new Scroller(mContext, new DecelerateInterpolator());
//            mListHeader = new LinearLayout(mContext);
//
//            LinearLayout.LayoutParams params = null;
//
//            mContent = new LinearLayout(context);
//            mContent.setOrientation(LinearLayout.VERTICAL);
//            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            mContent.setLayoutParams(params);
//
//            LayoutInflater layoutInflater = LayoutInflater.from(context);
//            onCreate(layoutInflater, mContent);
//
//            mContent.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            mContentHeight = mContent.getMeasuredHeight();
//
//            int containerHeight = 0;
//            if(mContentOccupy) containerHeight = mContentHeight;
//            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, containerHeight);
//            mContainer = new LinearLayout(context);
//            mContainer.setOrientation(LinearLayout.VERTICAL);
//            mContainer.setGravity(Gravity.BOTTOM);
//            mListHeader.addView(mContainer, params);
//
//            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mContentHeight);
//            mContainer.addView(mContent, params);
//
//            mListView.addHeaderView(mListHeader);
//            mListView.setHeaderOnComputeScrollListener(new OnComputeScrollListener()
//            {
//                @Override
//                public void onComputeScroll()
//                {
//                    if(mScroller.computeScrollOffset())
//                    {
//                        if(mScrolling)
//                        {
//                            int containerHeight = mScroller.getCurrY();
//                            setContainerHeight(containerHeight);
//                            mListView.postInvalidate();
//                        }
//                    }
//                    else mScrolling = false;
//                }
//            });
//
//            mListView.setHeaderOnTouchListener(new OnTouchListener()
//            {
//                private int mLastY = -1;
//
//                @Override
//                public boolean onTouch(View v, MotionEvent ev)
//                {
//                    int rawY = (int)ev.getRawY();
//                    if(mLastY==-1) mLastY = rawY;
//                    switch (ev.getAction())
//                    {
//                        case MotionEvent.ACTION_DOWN:
//                            mLastY = rawY;
//                            break;
//                        case MotionEvent.ACTION_MOVE:
//                            int deltaY = (rawY-mLastY)/2;
//                            mLastY = rawY;
//                            int firstVisiblePosition = mListView.getFirstVisiblePosition();
//                            int firstContentPosition = (mVLListView.mListHeader==null ? 0 : 1);
//                            if (firstVisiblePosition<=firstContentPosition)
//                            {
//                                int containerHeight = getContainerHeight();
//                                if(mContentOccupy==false)
//                                {//header隐藏
//                                    VLDebug.Assert(containerHeight>=0);
//                                    containerHeight += deltaY;
//                                    if(containerHeight<0)
//                                    {
//                                        setContainerHeight(0);
//                                        if(mState!=STATE_LOADING)
//                                            setState(STATE_NORMAL);
//                                    }
//                                    else
//                                    {
//                                        onPushHeight(containerHeight);
//                                        setContainerHeight(containerHeight);
//                                        if(mState!=STATE_LOADING)
//                                        {
//                                            if(containerHeight>mContentHeight)
//                                                setState(STATE_READY);
//                                            else
//                                                setState(STATE_NORMAL);
//                                        }
//                                    }
//                                }
//                                else
//                                {//header常显示占位，containerHeight一定>=mContentHeight
//                                    VLDebug.Assert(containerHeight>=mContentHeight);
//                                    containerHeight += deltaY;
//                                    if(containerHeight<mContentHeight)
//                                    {
//                                        setContainerHeight(mContentHeight);
//                                        if(mState!=STATE_LOADING)
//                                            setState(STATE_NORMAL);
//                                    }
//                                    else
//                                    {
//                                        onPushHeight(containerHeight);
//                                        setContainerHeight(containerHeight);
//                                        if(mState!=STATE_LOADING)
//                                        {
//                                            if(containerHeight>mContentHeight)
//                                                setState(STATE_READY);
//                                            else
//                                                setState(STATE_NORMAL);
//                                        }
//                                    }
//                                }
//                            }
//                            else
//                            {
//                                if(mContentOccupy==false)
//                                    setContainerHeight(0);
//                                else
//                                    setContainerHeight(mContentHeight);
//                            }
//                            break;
//                        default:
//                            mLastY = -1;//reset
//                            firstVisiblePosition = mListView.getFirstVisiblePosition();
//                            firstContentPosition = (mVLListView.mListHeader==null ? 0 : 1);
//                            if(firstVisiblePosition<=firstContentPosition)
//                            {
//                                int containerHeight = getContainerHeight();
//                                if(mContentOccupy==false)
//                                {
//                                    VLDebug.Assert(containerHeight>=0);
//                                    if(containerHeight>0)
//                                    {
//                                        if(containerHeight>mContentHeight)
//                                        {
//                                            mScroller.startScroll(0, containerHeight, 0, mContentHeight-containerHeight, 400);
//                                            mScrolling = true;
//                                            mListView.invalidate();
//                                            if(mState!=STATE_LOADING)
//                                                setState(STATE_LOADING);
//                                        }
//                                        else
//                                        {
//                                            mScroller.startScroll(0, containerHeight, 0, 0-containerHeight, 400);
//                                            mScrolling = true;
//                                            mListView.invalidate();
//                                            if(mState!=STATE_LOADING)
//                                                setState(STATE_NORMAL);
//                                        }
//                                    }
//                                }
//                                else
//                                {
//                                    VLDebug.Assert(containerHeight>=mContentHeight);
//                                    if(containerHeight>mContentHeight)
//                                    {
//                                        mScroller.startScroll(0, containerHeight, 0, mContentHeight-containerHeight, 400);
//                                        mScrolling = true;
//                                        mListView.invalidate();
//                                        if(mState!=STATE_LOADING)
//                                            setState(STATE_LOADING);
//                                    }
//                                    else
//                                    {
//                                        if(mState!=STATE_LOADING)
//                                            setState(STATE_NORMAL);
//                                    }
//                                }
//                            }
//                            else
//                            {
//                                if(mContentOccupy==false)
//                                    setContainerHeight(0);
//                                else
//                                    setContainerHeight(mContentHeight);
//                                if(mState!=STATE_LOADING)
//                                    setState(STATE_NORMAL);
//                            }
//                            break;
//                    }
//                    return false;
//                }
//            });
//            setState(STATE_NORMAL);
//            mInited = true;
//        }
//
//        public void setState(int state)
//        {
//            if(mState==state) return;
//            int fromState = mState;
//            mState = state;
//            onStateChanged(fromState, mState);
//        }
//
//        private void setContainerHeight(int containerHeight)
//        {
//            VLDebug.Assert(containerHeight>=0);
//            if(mContentOccupy)
//            {
//                if(containerHeight<mContentHeight)
//                    VLDebug.Assert(containerHeight>=mContentHeight);
//            }
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)mContainer.getLayoutParams();
//            params.height = containerHeight;
//            mContainer.setLayoutParams(params);
//        }
//
//        private int getContainerHeight()
//        {
//            int containerHeight =  mContainer.getHeight();
//            return containerHeight;
//        }
//
//        public void setContentOccupy(boolean contentOccupy)
//        {
//            mContentOccupy = contentOccupy;
//            reset();
//        }
//
//        public void setContentDisplay(boolean contentDisplay)
//        {
//            if(mContentDisplay==contentDisplay) return;
//            mContentDisplay = contentDisplay;
//            if(mContentDisplay)
//            {
//                mContent.setVisibility(View.VISIBLE);
//                mContent.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                mContentHeight = mContent.getMeasuredHeight();
//            }
//            else
//            {
//                mContent.setVisibility(View.GONE);
//                mContentHeight = 0;
//            }
//            reset();
//        }
//
//        public void reset()
//        {
//            if(mInited==false) return;
//            setState(STATE_NORMAL);
//            if(mContentOccupy==false)
//            {
//                int containerHeight = getContainerHeight();
//                if(containerHeight>0)
//                {
//                    mScroller.startScroll(0, containerHeight, 0, 0-containerHeight, 400);
//                    mScrolling = true;
//                    mListView.invalidate();
//                }
//                else setContainerHeight(0);
//            }
//            else
//            {
//                int containerHeight = getContainerHeight();
//                if(containerHeight>mContentHeight)
//                {
//                    mScroller.startScroll(0, containerHeight, 0, mContentHeight-containerHeight, 400);
//                    mScrolling = true;
//                    mListView.invalidate();
//                }
//                else setContainerHeight(mContentHeight);
//            }
//        }
//    }
//
//    public static abstract class VLListFooter
//    {
//        private Context mContext;
//        private VLListView mVLListView;
//        private ListViewWrapper mListView;
//        private boolean mContentOccupy;
//        private boolean mContentDisplay;
//        private boolean mInited;
//
//        private Scroller mScroller;
//        private LinearLayout mListFooter;
//        private LinearLayout mContent;
//        private int mContentHeight;
//        private LinearLayout mContainer;
//        private int mState;
//        private boolean mScrolling;
//
//        public static final int STATE_INIT = 0;
//        public static final int STATE_NORMAL = 1;
//        public static final int STATE_READY = 2;
//        public static final int STATE_LOADING = 3;
//
//        public abstract void onCreate(LayoutInflater inflater, ViewGroup root);
//        public abstract void onStateChanged(int from, int to) ;
//        public void onPushHeight(int height){}
//        public int getContentHeight() { return mContentHeight; }
//
//        public VLListFooter()
//        {
//            mContext = null;
//            mListView = null;
//            mContentOccupy = false;
//            mContentDisplay = true;
//            mInited = false;
//            mScroller = null;
//            mListFooter = null;
//            mContent = null;
//            mContentHeight = 0;
//            mContainer = null;
//            mState = STATE_INIT;
//            mScrolling = false;
//        }
//
//        @SuppressLint("ClickableViewAccessibility")
//        protected void init(Context context, VLListView vllistview)
//        {
//            VLDebug.Assert(mInited==false);
//            mContext = context;
//            mVLListView = vllistview;
//            mListView = vllistview.mListView;
//
//            mScroller = new Scroller(mContext, new DecelerateInterpolator());
//            mListFooter = new LinearLayout(mContext);
//
//            LinearLayout.LayoutParams params = null;
//
//            mContent = new LinearLayout(context);
//            mContent.setOrientation(LinearLayout.VERTICAL);
//            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            mContent.setLayoutParams(params);
//
//            LayoutInflater layoutInflater = LayoutInflater.from(context);
//            onCreate(layoutInflater, mContent);
//
//            mContent.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            mContentHeight = mContent.getMeasuredHeight();
//
//            int containerHeight = 0;
//            if(mContentOccupy) containerHeight = mContentHeight;
//            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, containerHeight);
//            mContainer = new LinearLayout(context);
//            mContainer.setOrientation(LinearLayout.VERTICAL);
//            mContainer.setGravity(Gravity.TOP);
//            mListFooter.addView(mContainer, params);
//
//            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mContentHeight);
//            mContainer.addView(mContent, params);
//
//
//            mListView.addFooterView(mListFooter);
//            mListView.setFooterOnComputeScrollListener(new OnComputeScrollListener()
//            {
//                @Override
//                public void onComputeScroll()
//                {
//                    if (mScroller.computeScrollOffset())
//                    {
//                        if(mScrolling)
//                        {
//                            int containerHeight = mScroller.getCurrY();
//                            setContainerHeight(containerHeight);
//                            mListView.postInvalidate();
//                        }
//                    }
//                    else mScrolling = false;
//                }
//            });
//
//            mListView.setFooterOnTouchListener(new OnTouchListener()
//            {
//                private int mLastY = -1;
//
//                @Override
//                public boolean onTouch(View v, MotionEvent ev)
//                {
//                    int rawY = (int) ev.getRawY();
//                    if (mLastY == -1) mLastY = rawY;
//                    switch (ev.getAction()) {
//                        case MotionEvent.ACTION_DOWN:
//                            mLastY = rawY;
//                            break;
//                        case MotionEvent.ACTION_MOVE:
//                            int deltaY = (rawY - mLastY)/2;
//                            mLastY = rawY;
//                            int lastVisiblePosition = mListView.getLastVisiblePosition();
//                            int lastContentPosition = mListView.getCount()-1-(mVLListView.mListHeader==null ? 0 : 1)-(mVLListView.mListFooter==null ? 0 : 1);
//                            if (lastVisiblePosition>=lastContentPosition)
//                            {
//                                int containerHeight = getContainerHeight();
//                                if (mContentOccupy == false)
//                                {
//                                    VLDebug.Assert(containerHeight >= 0);
//                                    containerHeight += -deltaY;
//                                    if (containerHeight < 0)
//                                    {
//                                        setContainerHeight(0);
//                                        if (mState != STATE_LOADING)
//                                            setState(STATE_NORMAL);
//                                    }
//                                    else
//                                    {
//                                        onPushHeight(containerHeight);
//                                        setContainerHeight(containerHeight);
//                                        if (mState != STATE_LOADING)
//                                        {
//                                            if (containerHeight > mContentHeight)
//                                                setState(STATE_READY);
//                                            else
//                                                setState(STATE_NORMAL);
//                                        }
//                                    }
//                                }
//                                else
//                                {
//                                    VLDebug.Assert(containerHeight >= mContentHeight);
//                                    containerHeight += -deltaY;
//                                    if (containerHeight < mContentHeight)
//                                    {
//                                        setContainerHeight(mContentHeight);
//                                        if (mState != STATE_LOADING)
//                                            setState(STATE_NORMAL);
//                                    }
//                                    else
//                                    {
//                                        onPushHeight(containerHeight);
//                                        setContainerHeight(containerHeight);
//                                        if (mState != STATE_LOADING)
//                                        {
//                                            if (containerHeight > mContentHeight)
//                                                setState(STATE_READY);
//                                            else
//                                                setState(STATE_NORMAL);
//                                        }
//                                    }
//                                }
//                            }
//                            else
//                            {
//                                if (mContentOccupy == false)
//                                    setContainerHeight(0);//?0
//                                else
//                                    setContainerHeight(mContentHeight);
//                            }
//                            break;
//                        default:
//                            mLastY = -1;//reset
//                            lastVisiblePosition = mListView.getLastVisiblePosition();
//                            lastContentPosition = mListView.getCount()-1-(mVLListView.mListHeader==null ? 0 : 1)-(mVLListView.mListFooter==null ? 0 : 1);
//                            if (lastVisiblePosition>=lastContentPosition)
//                            {
//                                int containerHeight = getContainerHeight();
//                                if (mContentOccupy == false)
//                                {
//                                    VLDebug.Assert(containerHeight >= 0);
//                                    if (containerHeight > 0)
//                                    {
//                                        if (containerHeight > mContentHeight)
//                                        {
//                                            mScroller.startScroll(0, containerHeight, 0, mContentHeight - containerHeight, 400);
//                                            mScrolling = true;
//                                            mListView.invalidate();
//                                            if (mState != STATE_LOADING)
//                                                setState(STATE_LOADING);
//                                        }
//                                        else
//                                        {
//                                            mScroller.startScroll(0, containerHeight, 0, 0 - containerHeight, 400);
//                                            mScrolling = true;
//                                            mListView.invalidate();
//                                            if (mState != STATE_LOADING)
//                                                setState(STATE_NORMAL);
//                                        }
//                                    }
//                                }
//                                else
//                                {
//                                    VLDebug.Assert(containerHeight >= mContentHeight);
//                                    if (containerHeight > mContentHeight)
//                                    {
//                                        mScroller.startScroll(0, containerHeight, 0, mContentHeight - containerHeight, 400);
//                                        mScrolling = true;
//                                        mListView.invalidate();
//                                        if (mState != STATE_LOADING)
//                                            setState(STATE_LOADING);
//                                    }
//                                    else
//                                    {
//                                        if (mState != STATE_LOADING)
//                                            setState(STATE_NORMAL);
//                                    }
//                                }
//                            }
//                            else
//                            {
//                                if (mContentOccupy == false)
//                                    setContainerHeight(0);
//                                else
//                                    setContainerHeight(mContentHeight);
//                                if (mState != STATE_LOADING)
//                                    setState(STATE_NORMAL);
//                            }
//                            break;
//                    }
//                    return false;
//                }
//            });
//            setState(STATE_NORMAL);
//            mInited = true;
//        }
//
//        public void setState(int state)
//        {
//            if(mState==state) return;
//            int fromState = mState;
//            mState = state;
//            onStateChanged(fromState, mState);
//        }
//
//        private void setContainerHeight(int containerHeight)
//        {
//            VLDebug.Assert(containerHeight>=0);
//            if(mContentOccupy)
//            {
//                if(containerHeight<mContentHeight)
//                    VLDebug.Assert(containerHeight>=mContentHeight);
//            }
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)mContainer.getLayoutParams();
//            params.height = containerHeight;
//            mContainer.setLayoutParams(params);
//        }
//
//        private int getContainerHeight()
//        {
//            return mContainer.getHeight();
//        }
//
//        public void setContentOccupy(boolean contentOccupy)
//        {
//            mContentOccupy = contentOccupy;
//            reset();
//        }
//
//        public void setContentDisplay(boolean contentDisplay)
//        {
//            if(mContentDisplay==contentDisplay) return;
//            mContentDisplay = contentDisplay;
//            if(mContentDisplay)
//            {
//                mContent.setVisibility(View.VISIBLE);
//                mContent.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                mContentHeight = mContent.getMeasuredHeight();
//            }
//            else
//            {
//                mContent.setVisibility(View.GONE);
//                mContentHeight = 0;
//            }
//            reset();
//        }
//
//        public void reset()
//        {
//            if(mInited==false) return;
//            setState(STATE_NORMAL);
//            if(mContentOccupy==false)
//            {
//                int containerHeight = getContainerHeight();
//                if(containerHeight>0)
//                {
//                    mScroller.startScroll(0, containerHeight, 0, 0-containerHeight, 400);
//                    mScrolling = true;
//                    mListView.invalidate();
//                }
//                else setContainerHeight(0);
//            }
//            else
//            {
//                int containerHeight = getContainerHeight();
//                if(containerHeight>mContentHeight)
//                {
//                    mScroller.startScroll(0, containerHeight, 0, mContentHeight-containerHeight, 400);
//                    mScrolling = true;
//                    mListView.invalidate();
//                }
//                else setContainerHeight(mContentHeight);
//            }
//        }
//    }
//
//    public void scrollToEnd()
//    {
//        if(mListViewItems.size()>0)
//            mListView.setSelection(mListViewItems.size()-1);
//    }
//
//    public void scrollToFirst()
//    {
//        if(mListViewItemsTemp.size() > 0)
//        {
//            mListView.setSelection(0);
//        }
//    }
//
//    public BaseAdapter getAdapter() {
//        return mListViewAdapter;
//    }
//}
//
