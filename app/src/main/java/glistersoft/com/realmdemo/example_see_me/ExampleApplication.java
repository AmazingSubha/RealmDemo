package glistersoft.com.realmdemo.example_see_me;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        Realm realm = Realm.getDefaultInstance();

        //Insert method(1)
        /*realm.beginTransaction();
        RealmModelExample user = realm.createObject(RealmModelExample.class);
        user.setName("John");
        user.setAge(28);
        realm.commitTransaction();*/

        //Insert method(2)
       /* RealmModelExample user = new RealmModelExample("John", 28);
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();*/

       //Multiple entries
        /*List<RealmModelExample> users = Arrays.asList(new RealmModelExample("John", 28),new RealmModelExample("James", 22));
        realm.beginTransaction();
        realm.insert(users);
        realm.commitTransaction();*/
//***************************
        //Query for looking at all users
        RealmQuery<RealmModelExample> users = realm.where(RealmModelExample.class);
        //Query conditions
        users.equalTo("name","John");
        users.or().equalTo("name","James");
        users.greaterThan("age",18).limit(10);
        users.greaterThan("age",18).limit(10).findAllAsync();// In background thread
        //Execute the query
        RealmResults<RealmModelExample> results = users.findAll();

        results.size();
        results.get(0).getName();

        //Find First - Finds the first object that fulfills the query conditions.
        RealmModelExample user = realm.where(RealmModelExample.class)
                .greaterThan("age",18).findFirst();
//***************************


    }
}
