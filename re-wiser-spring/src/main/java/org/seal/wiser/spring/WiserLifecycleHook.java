package org.seal.wiser.spring;

import org.seal.wiser.re.ReWiser;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class WiserLifecycleHook implements InitializingBean, DisposableBean {

    private ReWiser wiser;

    public WiserLifecycleHook(ReWiser wiser) {
        this.wiser = wiser;
    }

    public void afterPropertiesSet() {
        wiser.start();
    }

    public void destroy() {
        wiser.stop();
    }

}
