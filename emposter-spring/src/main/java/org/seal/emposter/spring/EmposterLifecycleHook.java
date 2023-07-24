package org.seal.emposter.spring;

/*-
 * #%L
 * emposter-spring
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

import org.seal.emposter.smtp.Emposter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Incorporates {@link Emposter} into ApplicationContext lifecycle.
 * Starts up {@link Emposter} on context startup and makes sure {@link Emposter} stopped when context stopping.
 */
public class EmposterLifecycleHook implements InitializingBean, DisposableBean {

    private Emposter emposter;

    public EmposterLifecycleHook(Emposter emposter) {
        this.emposter = emposter;
    }

    public void afterPropertiesSet() {
        emposter.start();
    }

    public void destroy() {
        emposter.stop();
    }

}
