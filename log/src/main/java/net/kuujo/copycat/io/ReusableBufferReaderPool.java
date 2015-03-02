/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.kuujo.copycat.io;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Buffer reader pool.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public class ReusableBufferReaderPool<T extends Buffer & Referenceable<T>> implements ReferenceManager<ReusableBufferReader<T>> {
  private final T buffer;
  private final Queue<ReusableBufferReader<T>> pool = new ArrayDeque<>(1024);

  public ReusableBufferReaderPool(T buffer) {
    if (buffer == null)
      throw new NullPointerException("buffer cannot be null");
    this.buffer = buffer;
  }

  /**
   * Acquires a new multi buffer reader.
   */
  public ReusableBufferReader<T> acquire() {
    ReusableBufferReader<T> reader = pool.poll();
    if (reader == null) {
      synchronized (pool) {
        reader = pool.poll();
        if (reader == null) {
          reader = new ReusableBufferReader<>(buffer, this);
        }
      }
    }
    reader.reset();
    return reader;
  }

  @Override
  public void release(ReusableBufferReader<T> reference) {
    pool.add(reference);
    reference.buffer().release();
  }

}