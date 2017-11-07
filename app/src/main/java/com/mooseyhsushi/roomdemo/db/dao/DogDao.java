package com.mooseyhsushi.roomdemo.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mooseyhsushi.roomdemo.db.models.Dog;

import java.util.List;
import java.util.Map;

@Dao
public interface DogDao {
    @Insert
    public long insertOne(Dog dog);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public List<Long> insertMany(List<Dog> dogs);

    @Update
    public int update(Dog... dogs);

    @Delete
    public int delete(Dog... dogs);

    @Query("SELECT * FROM dog")
    public List<Dog> getAll();

    @Query("SELECT * FROM dog WHERE id=:dogId")
    public Dog getById(long dogId);

    @Query("SELECT * FROM dog WHERE owner_id=:ownerId")
    public List<Dog> getByOwnerId(long ownerId);

    @Query("SELECT * FROM dog "
            + "INNER JOIN user ON user.id=dog.owner_id "
            + "WHERE user.id=:ownerId AND dog.name=:dogName")
    public List<Dog> getAllDogsByNameForOwner(long ownerId, String dogName);

    @Query("SELECT dog.owner_id AS userId, COUNT(*) AS numPets FROM dog " +
            "GROUP BY dog.owner_id")
    public List<CountUserDog> countDogsForUsers();

    class CountUserDog {
        public long userId;
        public int numPets;
    }
}
