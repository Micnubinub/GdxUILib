package tbs.uilib.view;

import java.util.ArrayList;

import tbs.uilib.UniversalClickListener;

/**
 * Created by linde on 09-Dec-15.
 */
public abstract class ViewPager extends ViewGroup {
    protected static final ArrayList<View> pages = new ArrayList<View>(4);
    public int currentPage;
    protected float pageScrollOffSetX, pageScrollOffSetY;
    private PageScrollListener pageScrollListener;
    private TextView title;
    private TabStrip tabStrip;
    //Todo ensure all pages are the same size as the parent
    //Todo make adapter for both the pages and the titles* optional

    public ViewPager() {
        title = new TextView((int) w);
        tabStrip = new TabStrip();
    }

    @Override
    public boolean longClick(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        //Todo notify on pageListener
        if (pageScrollListener != null) {

        }
        return super.longClick(touchType, xPos, yPos);
    }

    @Override
    public boolean click(UniversalClickListener.TouchType touchType, int xPos, int yPos) {
        //Todo notify on pageListener
        if (pageScrollListener != null) {

        }
        return super.click(touchType, xPos, yPos);
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        //Todo
    }

    public abstract ArrayList getTitles();

    public View getPage(int position) {
        if (position < 0 || position >= pages.size())
            return null;

        return pages.get(position);
    }

    //Todo views are the title, pager tab strip and pages


    public void setPageScrollListener(PageScrollListener pageScrollListener) {
        this.pageScrollListener = pageScrollListener;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void draw(float relX, float relY, float parentRight, float parentTop) {
        //Todo draw title and tab strip
        drawBackground(relX, relY);

        final View behind = getPage(currentPage - 1);
        final View current = getPage(currentPage);
        final View ahead = getPage(currentPage + 1);

        final float behindLeft = relX + x + 3;

        //Todo calculate offset and draw the views that a re behind and ahead based on that value
        if (behind != null)
            behind.draw(behindLeft, relY + y, parentRight, parentTop);

        if (current != null)
            current.draw(behindLeft + w, relY + y, parentRight, parentTop);

        if (ahead != null)
            ahead.draw(behindLeft + w + w, relY + y, parentRight, parentTop);

    }

    public interface PageScrollListener {
        void onPageScrolled(int currentPage, float pageScrollOffSet);

        void onPageSelected(int selectedPage);
    }

    public static class TabStrip extends ScrollView {
        public static final TextView titleTemplate = new TextView(33);
        public ArrayList<String> titles;
        private int selectedItemTextColor, selectedItemBackgroundColor, itemTextColor, itemBackgroundColor;

        public TabStrip() {
            super(true, true, false);
        }

        public void setSelectedItemBackgroundColor(int selectedItemBackgroundColor) {
            this.selectedItemBackgroundColor = selectedItemBackgroundColor;
        }

        public void setSelectedItemTextColor(int selectedItemTextColor) {
            this.selectedItemTextColor = selectedItemTextColor;
        }

        public void setItemBackgroundColor(int itemBackgroundColor) {
            this.itemBackgroundColor = itemBackgroundColor;
        }

        public void setItemTextColor(int itemTextColor) {
            this.itemTextColor = itemTextColor;
        }

        public void setTitles(ArrayList<String> titles) {
            this.titles = titles;
        }

        @Override
        public void draw(float relX, float relY, float parentRight, float parentTop) {
            //Todo draw title
            //Todo draw tabs
            //Todo draw pages

        }
    }
}
