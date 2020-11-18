package j.e.c.com;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import j.e.c.com.Others.Helper;
import j.e.c.com.Others.Prefrence;
import j.e.c.com.chatFragments.ChatFragment;
import j.e.c.com.teacherPanelFragments.HomeFragment;
import j.e.c.com.teacherPanelFragments.ProfileFragment;
import j.e.c.com.teacherPanelFragments.RegistrationFragment;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_person:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegistrationFragment()).commit();
                    openFragment(new ProfileFragment());
                    return true;
                case R.id.navigation_home:
                    openFragment(new HomeFragment());
                    return true;
                case R.id.navigation_chat:
                    openFragment(new ChatFragment());
                    return true;
            }
            return true;
        });
    }

    private void openFragment(Fragment fragment){
        if(!fragment.isVisible()) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }
}