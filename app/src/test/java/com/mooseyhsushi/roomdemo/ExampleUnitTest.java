package com.mooseyhsushi.roomdemo;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.mooseyhsushi.roomdemo.db.MyDatabase;
import com.mooseyhsushi.roomdemo.db.dao.DogDao;
import com.mooseyhsushi.roomdemo.db.dao.UserDao;
import com.mooseyhsushi.roomdemo.db.models.Dog;
import com.mooseyhsushi.roomdemo.db.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {
    private UserDao mUserDao;
    private DogDao mDogDao;
    private MyDatabase mDb;

    @Before
    public void createDb() {
        Context context = RuntimeEnvironment.application;
        mDb = Room.inMemoryDatabaseBuilder(context, MyDatabase.class)
                .allowMainThreadQueries()
                .build();
        mUserDao = mDb.getUserDao();
        mDogDao = mDb.getDogDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testEmptyDb() throws Exception {
        List<User> queryUsers = mUserDao.getAll();
        assertEquals(0, queryUsers.size());
    }

    @Test
    public void testInsertUser() throws Exception {
        User user1 = createFakeUser("Ash", "Ketchum", "ashketchum1990@gmail.com");
        long userId = mUserDao.insertOne(user1);

        // Query by id
        User queryUser = mUserDao.getById(userId);
        assertEquals("Ash", queryUser.firstName);
        assertEquals("Ketchum", queryUser.lastName);
        assertEquals("ashketchum1990@gmail.com", queryUser.email);
        assertEquals(0, queryUser.age);

        // Query all and test number of rows
        List<User> queryUsers = mUserDao.getAll();
        assertEquals(1, queryUsers.size());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user1 = createFakeUser("Ash", "Ketchum", "ashketchum1990@gmail.com");
        long userId = mUserDao.insertOne(user1);

        // Query by id
        User queryUser1 = mUserDao.getById(userId);
        assertEquals("Ash", queryUser1.firstName);
        assertEquals("Ketchum", queryUser1.lastName);

        // Update row
        User modifiedUser1 = queryUser1;
        modifiedUser1.firstName = "Misty";
        assertEquals(1, mUserDao.update(modifiedUser1));

        User queryUser2 = mUserDao.getById(userId);
        assertEquals("Misty", queryUser2.firstName);
        assertEquals("Ketchum", queryUser2.lastName);

        // Query all and test number of rows
        assertEquals(1, mUserDao.getAll().size());
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user1 = createFakeUser("Ash", "Ketchum", "ashketchum1990@gmail.com");
        long userId = mUserDao.insertOne(user1);

        // Query by id
        User queryUser = mUserDao.getById(userId);
        assertEquals("Ash", queryUser.firstName);

        // Delete row
        assertEquals(1, mUserDao.delete(queryUser));

        queryUser = mUserDao.getById(userId);
        assertNull(queryUser);

        // Query all and test number of rows
        assertEquals(0, mUserDao.getAll().size());
    }

    @Test
    public void testInsertDog() throws Exception {
        User user1 = createFakeUser("Ash", "Ketchum", "ashketchum1990@gmail.com");
        long userId = mUserDao.insertOne(user1);

        Dog dog1 = createFakeDoge(userId, "Pikachu", 3);
        long dogId = mDogDao.insertOne(dog1);

        // Query be row id
        Dog queryDog1 = mDogDao.getById(dogId);
        assertEquals("Pikachu", queryDog1.name);
        assertEquals(3, queryDog1.age);
        assertEquals(userId, queryDog1.ownerId);

        // Query by ownerId
        Dog queryDog2 = mDogDao.getByOwnerId(userId).get(0);
        assertEquals(queryDog1.id, queryDog2.id);
        assertEquals(queryDog1.ownerId, queryDog2.ownerId);

        // Query all and test number of rows
        assertEquals(1, mUserDao.getAll().size());
        assertEquals(1, mDogDao.getAll().size());
    }

    @Test
    public void testJoinUserAndDog() throws Exception {
        User user1 = createFakeUser("Ash", "Ketchum", "ashketchum1990@gmail.com");
        long userId = mUserDao.insertOne(user1);

        Dog dog1 = createFakeDoge(userId, "Pikachu1", 3);
        long dogId1 = mDogDao.insertOne(dog1);

        Dog dog2 = createFakeDoge(userId, "Pikachu2", 4);
        long dogId2 = mDogDao.insertOne(dog2);

        // Query be row id
        List<Dog> queryDogs1 = mDogDao.getAllDogsByNameForOwner(userId, "Pikachu1");
        assertEquals(1, queryDogs1.size());

        List<Dog> queryDogs2 = mDogDao.getAllDogsByNameForOwner(userId, "Pikachu2");
        assertEquals(1, queryDogs2.size());

        List<Dog> queryDogs3 = mDogDao.getAllDogsByNameForOwner(userId, "Pikachu3");
        assertEquals(0, queryDogs3.size());
    }

    @Test
    public void testGroupByUserAndDog() throws Exception {
        User user1 = createFakeUser("Ash", "Ketchum", "ashketchum1990@gmail.com");
        long userId = mUserDao.insertOne(user1);

        Dog dog1 = createFakeDoge(userId, "Pikachu1", 3);
        long dogId1 = mDogDao.insertOne(dog1);

        Dog dog2 = createFakeDoge(userId, "Pikachu2", 4);
        long dogId2 = mDogDao.insertOne(dog2);

        List<DogDao.CountUserDog> countList = mDogDao.countDogsForUsers();

        DogDao.CountUserDog count1 = countList.get(0);
        assertEquals(userId, count1.userId);
        assertEquals(2, count1.numPets);

        assertEquals(1, countList.size());
    }

    /*
     * Private helper methods
     */
    private User createFakeUser(String firstName, String lastName, String email) {
        User user1 = new User();
        user1.firstName = firstName;
        user1.lastName = lastName;
        user1.email = email;
        return user1;
    }

    private Dog createFakeDoge(long ownerId, String name, int age) {
        Dog doge = new Dog();
        doge.ownerId = ownerId;
        doge.name = name;
        doge.age = age;
        return doge;
    }
}