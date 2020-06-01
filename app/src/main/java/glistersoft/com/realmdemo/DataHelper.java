package glistersoft.com.realmdemo;

import io.realm.Realm;

public class DataHelper {

    public static void newTask(Realm realm, int taskId, String task){
        realm.beginTransaction();
        TaskDB taskDB = realm.createObject(TaskDB.class, taskId);
        taskDB.setTask(task);
        realm.commitTransaction();
    }

    public static void deleteTask(Realm realm, final long id){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TaskDB taskDBItem = realm.where(TaskDB.class)
                        .equalTo("id", id).findFirst();

                if (taskDBItem != null){
                    taskDBItem.deleteFromRealm();
                }
            }
        });
    }
}
