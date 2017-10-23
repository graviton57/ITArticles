package com.havryliuk.itarticles.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.havryliuk.itarticles.R;
import com.havryliuk.itarticles.ui.base.BaseActivity;
import com.havryliuk.itarticles.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Igor Havrylyuk on 22.10.2017.
 */

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginMvpPresenter<LoginMvpView> presenter;

    @BindView(R.id.et_email)
    EditText emailEditText;

    @BindView(R.id.et_password)
    EditText passwordEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActivityFragmentComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        presenter.attachView(this);
        initUI();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void initUI() {
        presenter.checkIfLoggedIn();
    }

    @OnClick(R.id.btn_server_login)
    void onServerLoginClick(View v) {
        presenter.onServerLoginClick(emailEditText.getText().toString(),
                passwordEditText.getText().toString());
    }

    @Override
    public void openMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}
