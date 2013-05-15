/*
 * Copyright (c) 2011-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package reactor.groovy

import reactor.core.Promise
import spock.lang.Specification

/**
 * @author Stephane Maldini (smaldini)
 */
class GroovyPromisesSpec extends Specification {

	def "Promise returns value"() {
		when: "a deferred Promise"
		def p = Promise.from("Hello World!")

		then: 'Promise contains value'
		p.get() == "Hello World!"
	}

	def "Promise notifies of Failure"() {
		when: "a deferred failed Promise"
		def p = Promise.from(new IllegalArgumentException("Bad code! Bad!"))

		and: "invoke result"
		p.get()

		then:
		p.state == Promise.State.FAILURE
		thrown(IllegalStateException)
	}

	def "Promises can be mapped"() {
		given: "a synchronous promise"
		def p = Promise.sync()

		when: "add a mapping closure"
		def s = p.map {
			Integer.parseInt it
		}

		and: "setting a value"
		p << '10'

		then:
		s.get() == 10
	}



	/*@Test


	@Test
	public void testPromisesCanBeFiltered() {
		Promise<String> p = Promise.sync();

		Supplier<Integer> s = p
				.map(new Function<String, Integer>() {
			@Override
			public Integer apply(String s) {
				return Integer.parseInt(s);
			}
		})
				.filter(new Function<Integer, Boolean>() {
			@Override
			public Boolean apply(Integer integer) {
				return integer > 10;
			}
		});

		p.set("10");

		assertThat("Value is null because it failed a filter", s.get(), is(nullValue()));
	}

	@Test
	public void testPromiseAsConsumer() {
		Promise<String> p1 = Promise.sync();
		Promise<String> p2 = Promise.sync();

		p1.consume(p2);

		p1.set("Hello World!");

		assertThat("Second Promise is set", p2.get(), is("Hello World!"));
	}

	@Test
	public void testErrorsStopCompositions() throws InterruptedException {
		Promise<String> p = Promise.create();
		final CountDownLatch exceptionHandled = new CountDownLatch(1);

		Deferred<Integer> d = p
				.map(new Function<String, Integer>() {
			@Override
			public Integer apply(String s) {
				return Integer.parseInt(s);
			}
		})
				.when(NumberFormatException.class, new Consumer<NumberFormatException>() {
			@Override
			public void accept(NumberFormatException nfe) {
				exceptionHandled.countDown();
			}
		})
				.filter(new Function<Integer, Boolean>() {
			@Override
			public Boolean apply(Integer integer) {
				System.out.println("This should not appear in the log...");
				return true;
			}
		});

		p.set("Not A Number");

		assertThat("Supplier is not populated", d.await(500, TimeUnit.MILLISECONDS), is(nullValue()));
		assertThat("Exception has been handled", exceptionHandled.getCount(), is(0L));
	}

	@Test
	public void testPromiseComposesAfterSet() {
		Promise<String> p = Promise.sync("10");

		Supplier<Integer> s = p
				.map(new Function<String, Integer>() {
			@Override
			public Integer apply(String s) {
				return Integer.parseInt(s);
			}
		})
				.map(new Function<Integer, Integer>() {
			@Override
			public Integer apply(Integer integer) {
				return integer * 10;
			}
		});

		assertThat("Promise has provided the value to the composition", s.get(), is(100));
	}*/

}