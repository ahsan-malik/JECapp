package j.e.c.com;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import j.e.c.com.Others.Helper;
import j.e.c.com.chatFragments.ChatFragment;
import j.e.c.com.commonFragments.WelcomeFragment;
import j.e.c.com.teacherPanelFragments.HomeFragment;
import j.e.c.com.teacherPanelFragments.ProfileFragment;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //startService(new Intent(this, FirebaseMessageService.class));

        navView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        onNewIntent(getIntent());

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

    @Override
    protected void onNewIntent(Intent intent) {
        //super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if(extras != null){
            String target = (String) extras.get("target");
            switch (target){
                case "chat":
                    openFragment(new ChatFragment());
                    break;
                case "0":
                case "1":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WelcomeFragment()).addToBackStack(null).commit();
                    break;
            }
        }
    }

    private void openFragment(Fragment fragment){
        if(!fragment.isVisible()) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }
}