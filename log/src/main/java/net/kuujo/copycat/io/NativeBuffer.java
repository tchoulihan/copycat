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

/**
 * Native buffer utility.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public class NativeBuffer extends NativeBlock implements AutoCloseable {
  private static final Allocator allocator = new NativeAllocator();

  /**
   * Allocates a native buffer.
   *
   * @param size The buffer size.
   * @return The buffer instance.
   */
  public static NativeBuffer allocate(long size) {
    return new NativeBuffer(allocator.allocate(size), new ReferenceManager<NativeBlock>() {
      @Override
      public void release(NativeBlock reference) {
        reference.memory().free();
      }
    });
  }

  private NativeBuffer(Memory memory, ReferenceManager<NativeBlock> referenceManager) {
    super(0, memory, referenceManager);
  }

}