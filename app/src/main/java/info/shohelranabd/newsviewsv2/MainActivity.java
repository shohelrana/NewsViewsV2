package info.shohelranabd.newsviewsv2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import info.shohelranabd.newsviewsv2.adapter.NewsAdapter;
import info.shohelranabd.newsviewsv2.common.Common;
import info.shohelranabd.newsviewsv2.interfaces.NewsService;
import info.shohelranabd.newsviewsv2.model.News;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Md. Shohel Rana on 29 December,2018
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    //Preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean isLoggedIn = false;

    @BindView(R.id.swipRefresh)
    SwipeRefreshLayout swipRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //News adapter
    NewsAdapter adapter;
    private NewsService newsService;

    AlertDialog dialog;

    private static final String TAG = "MainActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind view
        ButterKnife.bind(this);

        preferences = getSharedPreferences(Common.PREFS_NAME, MODE_PRIVATE);
        editor = preferences.edit();
        isLoggedIn = preferences.getBoolean(Common.LOG_PREFS_KY, false);

        //init paper for cache
        Paper.init(this);

        //init soptted dialog
        dialog = new SpotsDialog.Builder().setContext(this).build();

        //Init recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //init news service
        newsService = Common.getNewsService();

        //Load news
        loadNews(false);

        //Material drawer
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                /*.withHeaderBackground(R.drawable.header)*/
                .addProfiles(
                        new ProfileDrawerItem().withName("------- ----").withEmail("-----------@---").withIcon(getResources().getDrawable(R.drawable.def_acc_icon))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        PrimaryDrawerItem item_home = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home);
        SecondaryDrawerItem item_about = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_about);
        SecondaryDrawerItem item_log = new SecondaryDrawerItem().withIdentifier(3)
                .withName(isLoggedIn ? R.string.drawer_item_logout : R.string.drawer_item_login);
        SecondaryDrawerItem item_exit = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_exit);

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item_home,
                        item_about,
                        item_log,
                        item_exit
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                //Home
                                break;
                            case 2:
                                //About
                                break;
                            case 3:
                                if (isLoggedIn) {
                                    editor.putBoolean(Common.LOG_PREFS_KY, false).commit();
                                }
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                MainActivity.this.finish();
                                break;
                            case 4:
                                //Exit
                                break;
                            default:
                                break;
                        }
                        Toast.makeText(MainActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }).build();
    }

    // method: fetching news
    private void loadNews(boolean isRefresh) {
        if (!Common.isNetworkAvailable(MainActivity.this)) {
            showErrorDialog("There are no internet connection..");
        } else {
            if (!isRefresh) {
                String cache = Paper.book().read("news_cache");

                if (cache != null && !cache.isEmpty()) {
                    News news = new Gson().fromJson(cache, News.class);
                    adapter = new NewsAdapter(MainActivity.this, news);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                } else {
                    dialog.show();
                    newsService.getNews().enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            adapter = new NewsAdapter(MainActivity.this, response.body());
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);

                            //save to cache
                            Paper.book().write("news_cache", new Gson().toJson(response.body()));

                            Log.d(TAG, response.body().getArticles().get(0).getTitle());

                            //dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {
                            dialog.dismiss();
                            Log.d("MainActivity", t.getMessage());

                        }
                    });
                }
            } else {
                //dialog.show();
                newsService.getNews().enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        adapter = new NewsAdapter(MainActivity.this, response.body());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);

                        //save to cache
                        Paper.book().write("news_cache", new Gson().toJson(response.body()));

                        Log.d(TAG, response.body().toString());

                        swipRefresh.setRefreshing(false);

                        //dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {
                        ///dialog.dismiss();
                        Log.d(TAG, t.getMessage());
                    }
                });
            }
        }
    }

    @Override
    public void onRefresh() {
        // Do refresh
        loadNews(true);
    }

    //Method showing dialog with given message
    private void showErrorDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Message")
                .setMessage(msg)
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Load again
                        swipRefresh.setRefreshing(true);
                        loadNews(true);
                    }
                });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Exit the app
                MainActivity.this.finish();
            }
        });
        builder.setCancelable(false);
        AlertDialog msgAlertDialog = builder.create();
        msgAlertDialog.show();
    }
}
