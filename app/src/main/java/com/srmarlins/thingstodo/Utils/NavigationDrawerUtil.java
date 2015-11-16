package com.srmarlins.thingstodo.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondarySwitchDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.srmarlins.thingstodo.Models.EventCalendar;
import com.srmarlins.thingstodo.R;

/**
 * Created by jfowler on 11/10/15.
 */
public class NavigationDrawerUtil {

    private static NavigationDrawerUtil mNdUtil;

    private Drawer mNavDrawer;
    private SharedPreferences mPrefs;

    public static NavigationDrawerUtil getInstance(AppCompatActivity context) {
        if (mNdUtil == null) {
            mNdUtil = new NavigationDrawerUtil();
            mNdUtil.mPrefs = context.getPreferences(Context.MODE_PRIVATE);
        }
        return mNdUtil;
    }

    private AccountHeader setupNavDrawerHeader(AppCompatActivity context) {
        return new AccountHeaderBuilder()
                .withActivity(context)
                .withHeaderBackground(R.drawable.nav_drawer_background)
                .withCompactStyle(true)
                .build();
    }

    public void setupNavDrawer(final AppCompatActivity context, Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);
        context.setSupportActionBar(toolbar);

        mNavDrawer = new DrawerBuilder()
                .withActivity(context)
                .withToolbar(toolbar)
                .withAccountHeader(setupNavDrawerHeader(context))
                .withTranslucentStatusBar(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(FontAwesome.Icon.faw_home),
                        new PrimaryDrawerItem().withName("Settings").withIcon(FontAwesome.Icon.faw_gear),
                        new SectionDrawerItem().withDivider(true).withName("Events"),
                        new SecondaryDrawerItem().withName("Accepted").withIcon(FontAwesome.Icon.faw_check),
                        new SecondaryDrawerItem().withName("Declined").withIcon(FontAwesome.Icon.faw_close),
                        new SectionDrawerItem().withDivider(true).withName("Calendars")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return false;
                    }
                })
                .withShowDrawerOnFirstLaunch(true)
                .withSavedInstance(savedInstanceState)
                .build();

        context.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mNavDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    public void addCalendars(EventCalendar[] calendars) {
        for (final EventCalendar calendar : calendars) {
            ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
            int x = 0;
            int y = 0;
            int width = 200;
            int height = 200;

            drawable.getPaint().setColor(calendar.getColor());
            drawable.setBounds(x, y, width, height);
            drawable.setIntrinsicWidth(width);
            drawable.setIntrinsicHeight(height);
            drawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);

            SecondarySwitchDrawerItem item = new SecondarySwitchDrawerItem()
                    .withName(calendar.getName())
                    .withIcon(drawable)
                    .withOnCheckedChangeListener(new OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                            EventManager eventManager = EventManager.getInstance();
                            if (isChecked) {
                                eventManager.addCalendar(calendar);
                            } else {
                                eventManager.removeCalendar(calendar);
                            }
                            writeCalendarSelectedToPrefs(calendar, isChecked);
                        }
                    })
                    .withSelectable(false)
                    .withChecked(wasCalendarSelected(calendar));

            mNavDrawer.addItem(item);
        }
    }

    private boolean wasCalendarSelected(EventCalendar calendar) {
        return mPrefs.getBoolean(calendar.getName(), false);
    }

    private boolean writeCalendarSelectedToPrefs(EventCalendar calendar, boolean selected) {
        SharedPreferences.Editor editor = mPrefs.edit();
        return editor.putBoolean(calendar.getName(), selected).commit();
    }
}
