/**
 * 
 */
package biz.c24.io.spring.integration.transformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.Message;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.util.Assert;

import biz.c24.io.api.transform.Transform;

/**
 * TODO Consider adding a subclass that uses a pool of transform objects.
 * 
 * @author askogman
 * 
 */
public class IoTransformer extends AbstractTransformer implements
		InitializingBean {

	private final Log logger = LogFactory.getLog(this.getClass());

	private Class<? extends Transform> transformClass;

	private boolean alwaysReturnArray = false;

	public Class<? extends Transform> getTransformClass() {
		return transformClass;
	}

	public void setTransformClass(Class<? extends Transform> transformClass) {

		Assert.notNull(transformClass,
				"The transform class cannot be set to null");

		this.transformClass = transformClass;
	}

	/**
	 * 
	 */
	public IoTransformer() {
	}

	/**
	 * 
	 * @return The transformed payload
	 * 
	 * @see org.springframework.integration.transformer.AbstractTransformer#doTransform
	 *      (org.springframework.integration.Message)
	 */
	@Override
	protected Object doTransform(Message<?> message) throws Exception {

		Transform transform = buildTransform(message);
		
		// TODO Support list or array as input
		Object payload = message.getPayload();

		Object[][] results = transform
				.transform(new Object[][] { new Object[] { payload } });

		Object output = extractOutputPayload(results);

		return output;
	}

	protected Object extractOutputPayload(Object[][] results) {

		if (results.length == 0) {
			// Empty matrix
			return alwaysReturnArray ? new Object[0] : null;
		}

		// Get the first vector from matrix
		Object[] resultVector = results[0];

		if (resultVector.length == 0) {
			// Empty matrix
			return alwaysReturnArray ? new Object[0] : null;
		}

		// Single result, unwrap and use as new payload. Fairly common.
		if (resultVector.length == 1 && !alwaysReturnArray) {
			return resultVector[0];
		}

		// Return a full array of output
		return resultVector;
	}

	/**
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	protected Transform buildTransform(Message<?> message) throws Exception {
		return createTransform();
	}

	protected Transform createTransform() throws Exception {
		try {
			return transformClass.newInstance();
		} catch (Exception e) {
			logger.error("Could not instantiate Transformer of class ["
					+ transformClass.getName() + "]", e);
			throw e;
		}
	}

	@Override
	protected void onInit() throws Exception {
		super.onInit();

		Assert.state(transformClass != null,
				"The transformClass property must not be null");

		try {
			createTransform();
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"The provided transform class threw an exception from its default constructor.",
					e);
		}

	}

	public boolean isAlwaysReturnArray() {
		return alwaysReturnArray;
	}

	public void setAlwaysReturnArray(boolean alwaysReturnArray) {
		this.alwaysReturnArray = alwaysReturnArray;
	}
}
