package emanager.com.cityeye;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity /*implements BaseFragment.FragmentNavigation*/{

    private BottomBar mBottombar;
    private FragNavController mNavController;

    private final int INDEX_HOME = FragNavController.TAB1;
    private final int INDEX_SEARCH = FragNavController.TAB2;
    private final int INDEX_CAMERA = FragNavController.TAB3;
    private final int INDEX_MAPS = FragNavController.TAB4;
    private final int INDEX_CONT = FragNavController.TAB5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //FragNav
        //list of fragments
        List<Fragment> fragments = new ArrayList<>(5);

        //add fragments to list
        fragments.add(home.newInstance(0));
        fragments.add(Search.newInstance(0));
        fragments.add(camera.newInstance(0));
        fragments.add(maps.newInstance(0));
        fragments.add(contacto.newInstance(0));

        mNavController =
                new FragNavController(getSupportFragmentManager(), R.id.container, fragments);
        mBottombar = BottomBar.attach(this, savedInstanceState);
        mBottombar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                //switch between tabs
                switch (menuItemId) {
                    case R.id.bottomHome:
                        mNavController.switchTab(INDEX_HOME);
                        break;
                    case R.id.bottomSearch:
                        mNavController.switchTab(INDEX_SEARCH);
                        break;
                    case R.id.bottomCam:
                        mNavController.switchTab(INDEX_CAMERA);
                        break;
                    case R.id.bottomCont:
                        mNavController.switchTab(INDEX_CONT);
                        break;
                    case R.id.bottomMap:
                        mNavController.switchTab(INDEX_MAPS);
                        break;
                }
            }


            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomHome) {
                    mNavController.clearStack();
                }
            }
        });



    }
}
