package myjpa.core.util;

import java.util.List;

public  class LogUtil<T> {

    public  void printObject(T info)
    {
        System.out .println("##### getUserIterableInf:" + info.toString());
    }

    public   void printObjectList(Iterable<T> infoList)
    {
        for (T info : infoList)
        {
            System.out.println("##### getUserIterableInf:" + info.toString());
        }
    }

    public   void printObjectList(List<T> infoList)
    {
        for (T info : infoList)
        {
            System.out.println("##### getUserListInf:" + info.toString());
        }
    }



}
