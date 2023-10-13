package com.namhaigroup.map;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.namhaigroup.map.system.AppSettings;

public class SettingAlertActivity extends AppCompatActivity {

    CheckBox cb40km, cb50km, cb60km, cb70km, cb80km, cb90km, cb100km, cb110km, cb120km, cbHighPopulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_alert);

        cb40km = findViewById(R.id.cb40km);
        cb50km = findViewById(R.id.cb50km);
        cb60km = findViewById(R.id.cb60km);
        cb70km = findViewById(R.id.cb70km);
        cb80km = findViewById(R.id.cb80km);
        cb90km = findViewById(R.id.cb90km);
        cb100km = findViewById(R.id.cb100km);
        cb110km = findViewById(R.id.cb110km);
        cb120km = findViewById(R.id.cb120km);
        cbHighPopulation = findViewById(R.id.cbHighPopulation);

        if(AppSettings.isAlert40km == true) {
            cb40km.setChecked(true);
        }
        if(AppSettings.isAlert50km == true) {
            cb50km.setChecked(true);
        }
        if(AppSettings.isAlert60km == true) {
            cb60km.setChecked(true);
        }
        if(AppSettings.isAlert70km == true) {
            cb70km.setChecked(true);
        }
        if(AppSettings.isAlert80km == true) {
            cb80km.setChecked(true);
        }
        if(AppSettings.isAlert90km == true) {
            cb90km.setChecked(true);
        }
        if(AppSettings.isAlert100km == true) {
            cb100km.setChecked(true);
        }
        if(AppSettings.isAlert110km == true) {
            cb110km.setChecked(true);
        }
        if(AppSettings.isAlert120km == true) {
            cb120km.setChecked(true);
        }
        if(AppSettings.isHighPopulation == true) {
            cbHighPopulation.setChecked(true);
        }

        cb40km.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettings.isAlert40km = true;
                } else {
                    AppSettings.isAlert40km = false;
                }
            }
        });

        cb50km.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettings.isAlert50km = true;
                } else {
                    AppSettings.isAlert50km = false;
                }
            }
        });

        cb60km.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettings.isAlert60km = true;
                } else {
                    AppSettings.isAlert60km = false;
                }
            }
        });

        cb70km.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettings.isAlert70km = true;
                } else {
                    AppSettings.isAlert70km = false;
                }
            }
        });

        cb80km.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettings.isAlert80km = true;
                } else {
                    AppSettings.isAlert80km = false;
                }
            }
        });

        cb90km.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettings.isAlert90km = true;
                } else {
                    AppSettings.isAlert90km = false;
                }
            }
        });

        cb100km.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettings.isAlert100km = true;
                } else {
                    AppSettings.isAlert100km = false;
                }
            }
        });

        cb110km.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettings.isAlert110km = true;
                } else {
                    AppSettings.isAlert110km = false;
                }
            }
        });

        cb120km.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettings.isAlert120km = true;
                } else {
                    AppSettings.isAlert120km = false;
                }
            }
        });

        cbHighPopulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettings.isHighPopulation = true;
                } else {
                    AppSettings.isHighPopulation = false;
                }
            }
        });
    }
}