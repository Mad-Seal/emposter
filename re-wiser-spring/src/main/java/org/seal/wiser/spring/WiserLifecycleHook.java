package org.seal.wiser.spring;

/*-
 * #%L
 * re-wiser-spring
 * %%
 * Copyright (C) 2022 authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
