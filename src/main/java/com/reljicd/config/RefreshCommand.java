package com.reljicd.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataman.hawk.config.client.configwatch.ConfigUpdateInfo;
import com.dataman.hawk.config.client.configwatch.NotifyObserver;
import com.dataman.hawk.config.client.configwatch.WatchNotify;

/**
 * @author liuqian on 2017/11/16 10:59
 * @project hawk
 * @desc desc
 */
@Component
public class RefreshCommand {

    @Autowired
    private WatchNotify watchNotify;

    @PostConstruct
    public void start(){
        watchNotify.registerObserver(new NotifyObserver() {

            @Override
            public void updateContext(ConfigUpdateInfo updateInfo) {

                System.out.println(updateInfo);

                watchNotify.updateSpringContext();
            }
        });
    }
}
