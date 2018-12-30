package info.shohelranabd.newsviewsv2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

/**
 * Created by Md. Shohel Rana on 29 December,2018
 */
public class MainActivity extends AppCompatActivity {

    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                .withName(isLoggedIn ? R.string.drawer_item_login : R.string.drawer_item_logout );
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
                        switch (position){
                            case 1:
                                //Home
                                break;
                            case 2:
                                //About
                                break;
                            case 3:
                                //Login/Logout
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
}
