package glistersoft.com.realmdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.SecureRandom;

import glistersoft.com.realmdemo.adapter.RecyclerViewAdapter;
import io.realm.Realm;


public class MainActivity extends AppCompatActivity implements NewTaskDialog.NewTaskListener {

    private Realm realm;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        final DialogFragment addTaskDialog = new NewTaskDialog();
        setUpRecyclerView();

        //using lambda
        floatingActionButton.setOnClickListener(v -> {
            addTaskDialog.show(getSupportFragmentManager(), "New Task");
        });
    }

    @Override
    public void onAddTask(String task) {
        SecureRandom secureRandom = new SecureRandom();
        //From 0 to 99999
        int taskID = secureRandom.nextInt(100000);
        DataHelper.newTask(realm, taskID, task);
    }

    @Override
    public void onCancel(DialogFragment dialogFragment) {
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
    }
    public void setUpRecyclerView(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(realm.where(TaskDB.class).findAll());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback {
        public TouchHelperCallback() {
            super(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            DataHelper.deleteTask(realm, viewHolder.getItemId());
        }
    }
}
