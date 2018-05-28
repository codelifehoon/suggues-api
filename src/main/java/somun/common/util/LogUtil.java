package somun.common.util;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public  class LogUtil<T> {

    public  void printObject(T info)
    {
        log.debug("##### printObject:" + info.toString());
    }

    public   void printObjectList(Iterable<T> infoList)
    {
        for (T info : infoList)
        {
            log.debug("##### printObjectList:" + info.toString());
        }
    }

    public   void printObjectList(List<T> infoList)
    {
        for (T info : infoList)
        {
            log.debug("##### printObjectList:" + info.toString());
        }
    }



}
