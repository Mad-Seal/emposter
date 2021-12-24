package org.seal.wiser.spring;

import org.seal.wiser.re.ReWiser;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Incorporates {@link ReWiser} into ApplicationContext lifecycle.
 * Starts up {@link ReWiser} on context startup and makes sure {@link ReWiser} stopped when context stopping.
 */
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
