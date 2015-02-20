package si.puntar.woodlogger.ui.fragment.addMeasurement;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.InjectView;
import si.puntar.woodlogger.R;
import si.puntar.woodlogger.app.App;
import si.puntar.woodlogger.data.model.Log;
import si.puntar.woodlogger.ui.activity.main.Dagger_MainComponent;
import si.puntar.woodlogger.ui.activity.main.MainComponent;
import si.puntar.woodlogger.ui.activity.main.MainModule;
import si.puntar.woodlogger.ui.fragment.baseFragment.BaseFragment;

/**
 * Created by Puntar on 2/7/15.
 */
public class AddMeasurementFragment extends BaseFragment implements
        AddMeasurementView,
        EditText.OnEditorActionListener,
        View.OnClickListener {

    @Inject AddMeasurementPresenter presenter;

    @InjectView(R.id.btn_change_length) Button btnChangeLength;
    @InjectView(R.id.et_log_diameter) EditText etLogDiameter;
    @InjectView(R.id.btn_save_data) Button btnSaveData;

    private OnAddMeasurementFragmentListener listener;

    @Override
    protected void inject() {
        AddMeasurementComponent addMeasComponent = Dagger_AddMeasurementComponent.builder()
                .appComponent(App.get(getActivity()).getComponent())
                .addMeasurementModule(new AddMeasurementModule(this)).build();

        addMeasComponent.inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_add_measurement;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnChangeLength.setOnClickListener(this);
        etLogDiameter.setOnEditorActionListener(this);
        btnSaveData.setOnClickListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        switch (v.getId()) {
            case R.id.et_log_diameter:
                presenter.verifyData(etLogDiameter.getText().toString());
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_length:

                break;
            case R.id.btn_save_data:
                presenter.verifyData(etLogDiameter.getText().toString());
                break;
        }
    }

    public void setListener(OnAddMeasurementFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void publishLog(Log log) {
        listener.saveMeasurement(log);
    }

    public interface OnAddMeasurementFragmentListener {

        void saveMeasurement(Log log);

    }
}