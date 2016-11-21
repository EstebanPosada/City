package emanager.com.cityeye;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.login.LoginManager;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

public class Probes extends AppCompatActivity {

    private BottomBar mBottomBar;
    private FragNavController fragNavController;

    //indices to fragments
    private final int TAB_HOME = FragNavController.TAB1;
    private final int TAB_SEARCH = FragNavController.TAB2;
    private final int TAB_CAMERA = FragNavController.TAB3;
    private final int TAB_MAPS = FragNavController.TAB4;
    private final int TAB_CONT = FragNavController.TAB5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probes);

        //FragNav
        //list of fragments
        List<Fragment> fragments = new ArrayList<>(5);

        //add fragments to list
        fragments.add(home.newInstance(0));
        fragments.add(Search.newInstance(0));
        fragments.add(camera.newInstance(0));
        fragments.add(maps.newInstance(0));
        fragments.add(contacto.newInstance(0));


        //link fragments to container
        fragNavController = new FragNavController(getSupportFragmentManager(),R.id.activity_probes,fragments);
        //End of FragNav

        //BottomBar menu
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.fragments);
        mBottomBar.mapColorForTab(0, R.color.tab1);
        mBottomBar.mapColorForTab(1, R.color.tab1);
        mBottomBar.mapColorForTab(2, R.color.tab1);
        mBottomBar.mapColorForTab(3, R.color.tab3);
        mBottomBar.mapColorForTab(4, R.color.tab3);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                //switch between tabs
                switch (menuItemId) {
                    case R.id.bottomHome:
                        fragNavController.switchTab(TAB_HOME);
                        break;
                    case R.id.bottomSearch:
                        fragNavController.switchTab(TAB_SEARCH);
                        break;
                    case R.id.bottomCam:
                        fragNavController.switchTab(TAB_CAMERA);
                        break;
                    case R.id.bottomCont:
                        fragNavController.switchTab(TAB_CONT);
                        break;
                    case R.id.bottomMap:
                        fragNavController.switchTab(TAB_MAPS);
                        break;
                }
            }


            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomHome) {
                    fragNavController.clearStack();
                }
            }
        });
        /*BottomBarBadge unreadMessages = mBottomBar.makeBadgeForTabAt(1, "#E91E63", 4);
        unreadMessages.show();
        unreadMessages.setAnimationDuration(200);*/
        //End of BottomBar menu
    }

    @Override
    public void onBackPressed() {
        if (fragNavController.getCurrentStack().size() > 1) {
            fragNavController.pop();
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mBottomBar.onSaveInstanceState(outState);
    }

    public void Logout(){
        LoginManager.getInstance().logOut();
        startActivity(new Intent(Probes.this, MainActivity.class));
    }
}
