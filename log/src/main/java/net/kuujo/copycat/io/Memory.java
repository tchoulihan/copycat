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
 * Memory allocator.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public interface Memory {

  /**
   * Returns the memory address.
   *
   * @return The memory address.
   */
  long address();

  /**
   * Returns the memory size.
   *
   * @return The memory size.
   */
  long size();

  /**
   * Clears the memory.
   */
  void clear();

  /**
   * Frees the memory.
   */
  void free();

}