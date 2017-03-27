/*
 * Copyright 2007 the original author or authors.
 *
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
 */

package ru.mera.samples.application.service;

import ru.mera.samples.application.dto.AbstractDTO;

import java.util.List;

public interface AbstractService<T extends AbstractDTO> {

  void create(T dtObject);

  T read(Long id);

  List<T> readAll();

  void update(T address);

  void delete(Long id);
}
