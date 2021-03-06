/*
 * Copyright 2012 C24 Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *			http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package biz.c24.io.spring.batch.config;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * {@link NamespaceHandler} implementation that registers
 * {@link BeanDefinitionParser}s for our namespace elements.
 *
 * @author Andrew Elmore
 */
class C24NamespaceHandler extends NamespaceHandlerSupport {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	public void init() {
        registerBeanDefinitionParser("file-source", new FileSourceParser());
        registerBeanDefinitionParser("zip-file-source", new ZipFileSourceParser());
        registerBeanDefinitionParser("file-writer", new FileWriterSourceParser());
        registerBeanDefinitionParser("zip-file-writer", new ZipFileWriterSourceParser());
        registerBeanDefinitionParser("item-reader", new ItemReaderParser());
        registerBeanDefinitionParser("xml-item-reader", new XmlItemReaderParser());
		registerBeanDefinitionParser("batch-item-reader", new BatchItemReaderParser());
		registerBeanDefinitionParser("transform-item-processor", new TransformItemProcessorParser());
		registerBeanDefinitionParser("item-writer", new ItemWriterParser());		
	}
}
