package com.bluetea.abbiswood;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bluetea.abbiswood.helper.Commons;
import com.bluetea.abbiswood.helper.Connectivity;
import com.bluetea.abbiswood.helper.ApiClient;
import com.bluetea.abbiswood.vo.CinemaVO;
import com.bluetea.abbiswood.vo.MenuItemVO;
import com.flyco.animation.SlideEnter.SlideBottomEnter;
import com.flyco.animation.SlideEnter.SlideLeftEnter;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.flyco.animation.SlideExit.SlideTopExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Main extends Activity {

//    ResideMenu resideMenu;

    @InjectView(R.id.tv_cinema)
    TextView tvSelectedCinema;


    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Connectivity.BASE_URL).build();
    ApiClient apiClient =
            restAdapter.create(ApiClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);



        newsFragment = NewsFragment.newInstance(null, null);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.id_container_menu, newsFragment)
                .commit();

//        resideMenu = new ResideMenu(this);
//        resideMenu.setBackground(R.drawable.menu_background);
//        resideMenu.attachToActivity(this);

        // create menu items;
//        String titles[] = { "News","Showtime","Voted", "Non-Voted"
//        };
//        int icon[] = { R.drawable.icon_home, R.drawable.icon_list, R.drawable.icon_list};
//
//        for (String title1 : titles) {
//            ResideMenuItem item = new ResideMenuItem(this, R.drawable.icon_home, title1);
//            item.setOnClickListener(this);
//            item.setTag(new MenuItemVO(MenuItemVO.DIRECTION_LEFT,title1));
//            resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
//        }

        FirebaseMessaging.getInstance().subscribeToTopic("news");

        new DrawerBuilder().withActivity(this).build();
        prepareDrawer();
    }

    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();
    private ShowtimeFragment showtimeFragment;
    private VotedListFragment votedFragment;
    private VotedListFragment nonVotedFragment;
    private NewsFragment newsFragment;

    private void changeView(String type){
        Fragment fragment = null;
        switch (type) {
            case "news":
                newsFragment = NewsFragment.newInstance(null, null);
                fragment = newsFragment;
                break;
            case "showtime":
                showtimeFragment = ShowtimeFragment.newInstance(null, null);
                fragment = showtimeFragment;
                break;
            case "voted":
                votedFragment = VotedListFragment.newInstance("voted", null);
                fragment = votedFragment;
                break;
            case "non-voted":
                nonVotedFragment = VotedListFragment.newInstance("non-voted", null);
                fragment = nonVotedFragment;
                break;
        }

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.id_container_menu, fragment)
                .commit();
    }
    Drawer drawer;
    void prepareDrawer(){
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.cog)
                .build();


//create the drawer and remember the `Drawer` result object
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("News"),
                        new PrimaryDrawerItem().withName("Cinema Schedule"),
                        new PrimaryDrawerItem().withName("Voted"),
                        new PrimaryDrawerItem().withName("Non-Voted"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("exit")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        switch (position){
                            case 1:
                                changeView("news");
                                break;
                            case 2:
                                changeView("showtime");
                                showtimeFragment.refreshContent();
                                break;
                            case 3:
                                changeView("voted");
                                break;
                            case 4:
                                changeView("non-voted");
                                break;
                        }

                        return false;
                    }
                })
                .build();
    }



    @OnClick(R.id.btn_drawer)
    public void toggleLeftDrawer(){
        if(drawer.isDrawerOpen())
            drawer.closeDrawer();
        else
            drawer.openDrawer();
    }
//    @OnClick(R.id.btn_cinemas)
//    public void toggleRightDrawer(){
//        loadCinemas();
//        if(!resideMenu.isOpened()){
//            resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//        }else
//            resideMenu.closeMenu();
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onClick(View view) {
//        final MenuItemVO menuItemVO = (MenuItemVO)view.getTag();
//        if(menuItemVO.direction == MenuItemVO.DIRECTION_LEFT) {
//            switch (menuItemVO.name) {
//                case "News":
//                    changeView("news");
//                    resideMenu.closeMenu();
//                    break;
//                case "Showtime":
//                    changeView("showtime");
//                    loadCinemas();
//                    resideMenu.closeMenu();
//                    break;
//                case "Voted":
//                    changeView("voted");
//                    ArrayList<ResideMenuItem> resideMenuItems = new ArrayList<>();
//                    int i = 0;
//                    for(String title: new String[]{"Best Film","Best Actor", "Best Actress"}){
//                        ResideMenuItem item = new ResideMenuItem(Main.this, R.drawable.icon_list, title);
//                        item.setOnClickListener(Main.this);
//                        item.setTag(new MenuItemVO(MenuItemVO.DIRECTION_RIGHT, MenuItemVO.PAGE_VOTED, (long)(i++)));
//                        resideMenuItems.add(item);
//                    }
//                    resideMenu.setMenuItems(resideMenuItems, ResideMenu.DIRECTION_RIGHT);
//                    resideMenu.closeMenu();
//                    break;
//                case "Non-Voted":
//                    changeView("non-voted");
//                    ArrayList<ResideMenuItem> resideMenuItems2 = new ArrayList<>();
//                    int i2 = 0;
//                    for(String title: new String[]{"Box Office","Best Cinema", "Coming Soon"}){
//                        ResideMenuItem item = new ResideMenuItem(Main.this, R.drawable.icon_list, title);
//                        item.setOnClickListener(Main.this);
//                        item.setTag(new MenuItemVO(MenuItemVO.DIRECTION_RIGHT, MenuItemVO.PAGE_NON_VOTED, (long)(i2++)));
//                        resideMenuItems2.add(item);
//                    }
//                    resideMenu.setMenuItems(resideMenuItems2, ResideMenu.DIRECTION_RIGHT);
//                    resideMenu.closeMenu();
//                    break;
//            }
//        }else{
//            if(menuItemVO.page == MenuItemVO.PAGE_HOME) {
//                if(menuItemVO.size > 1) {
//                    final String[] stringItems = new String[menuItemVO.size];
//                    for (int i = 0; i < stringItems.length; i++) {
//                        stringItems[i] = "Cinema " + (i + 1);
//                    }
//                    final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
//                    dialog.title("Choose Cinema")//
//                            .titleTextSize_SP(14.5f)//
//                            .showAnim(new SlideBottomEnter())
//                            .dismissAnim(new SlideTopExit())
//                            .cancelText("cancel")
//                            .show();
//
//                    dialog.setOnOperItemClickL(new OnOperItemClickL() {
//                        @Override
//                        public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            showtimeFragment.refreshContent(menuItemVO.id, position + 1);
//                            dialog.dismiss();
//                        }
//                    });
//                }else{
//                    showtimeFragment.refreshContent(menuItemVO.id, 1);
//                }
//            }
//            else if(menuItemVO.page == MenuItemVO.PAGE_VOTED)
//                votedFragment.refreshContent(menuItemVO.id, true);
//            else if(menuItemVO.page == MenuItemVO.PAGE_NON_VOTED)
//                nonVotedFragment.refreshContent(menuItemVO.id, false);
//        }
//    }

    public void checkGooglePlayService(){
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if(status!=ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        checkGooglePlayService();
    }
}
