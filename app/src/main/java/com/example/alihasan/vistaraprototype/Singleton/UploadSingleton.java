package com.example.alihasan.vistaraprototype.Singleton;

public class UploadSingleton {

    private static int counter;

    private static UploadSingleton instance;

    public static UploadSingleton getInstance() {

        if (instance == null)
            instance = new UploadSingleton();

        return instance;
    }

    public void setTrue()
    {
        counter = 1;
    }

    public void setFalse()
    {
        counter = 0;
    }

    public int getCounter()
    {
        return counter;
    }
}
