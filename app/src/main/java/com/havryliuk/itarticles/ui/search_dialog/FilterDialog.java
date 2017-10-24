package com.havryliuk.itarticles.ui.search_dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.havryliuk.itarticles.R;
import com.havryliuk.itarticles.injection.component.ActivityFragmentComponent;
import com.havryliuk.itarticles.ui.base.BaseDialog;
import com.havryliuk.itarticles.utils.events.SearchParamEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Igor Havrylyuk on 24.10.2017.
 */

public class FilterDialog extends BaseDialog
        implements FilterDialogMvpView, DatePickerDialog.OnDateSetListener {


    private static final String SEARCH_DIALOG_TAG = "SEARCH_DIALOG_TAG";
    private static final String DATE_PICKER_DIALOG_RAG = "Datepickerdialog";

    @Inject
    FilterDialogMvpPresenter<FilterDialogMvpView> presenter;

    @BindView(R.id.radio_tag)
    RadioButton checkTag;

    @BindView(R.id.radio_author)
    RadioButton checkAuthor;

    @BindView(R.id.category_spinner)
    Spinner categorySpinner;

    @BindView(R.id.btn_submit)
    Button submitButton;

    @BindView(R.id.pick_date)
    Button pickDateButton;

    @BindView(R.id.date_to_title)
    TextView dateToView;

    @BindView(R.id.date_from_title)
    TextView dateFromView;

    private String category;
    private String dateTo = "";
    private String dateFrom = "";

    public static FilterDialog newInstance() {
        Bundle args = new Bundle();
        FilterDialog fragment = new FilterDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        category = null;
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_filter, container, false);
        ActivityFragmentComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.attachView(this);
        }
        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, SEARCH_DIALOG_TAG);
    }

    @Override
    protected void init(View view) {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchParamEvent event = new SearchParamEvent(checkTag.isChecked(), category);
                if (!dateFrom.isEmpty()) {
                    event.setDateFrom(dateFrom);
                }
                if (!dateTo.isEmpty()) {
                    event.setDateTo(dateTo);
                }
                presenter.onSubmitClick(event);
            }
        });
        checkTag.setChecked(true);
        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.articles_categories, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(cityAdapter);
        categorySpinner.setSelection(0);//set 'all' default category
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (!parent.getItemAtPosition(i).toString().equals(category)){
                    String[] categoryValues =
                            getResources().getStringArray(R.array.articles_categories_values);
                    category = categoryValues[i];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        FilterDialog.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), DATE_PICKER_DIALOG_RAG);
            }
        });
        updateDate();
    }

    @Override
    public void onFilterValueChange(SearchParamEvent event) {
        EventBus.getDefault().postSticky(event);
    }

    @Override
    public void dismissDialog() {
        super.dismissDialog(SEARCH_DIALOG_TAG);
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,
                          int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        dateFrom = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                + "-" + String.valueOf(dayOfMonth);
        dateTo = String.valueOf(yearEnd) + "-" + String.valueOf(monthOfYearEnd)
                + "-" + String.valueOf(dayOfMonthEnd);
        updateDate();
    }

    private void updateDate(){
        dateFromView.setText(getString(R.string.format_date_from, dateFrom));
        dateToView.setText(getString(R.string.format_date_to, dateTo));
    }
}
